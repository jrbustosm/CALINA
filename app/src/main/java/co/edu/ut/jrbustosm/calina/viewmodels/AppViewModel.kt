package co.edu.ut.jrbustosm.calina.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.ut.jrbustosm.calina.data.asLocalDate
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.repositories.AppRepository
import co.edu.ut.jrbustosm.calina.viewmodels.states.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class AppViewModel(
    private val repository: AppRepository
): ViewModel() {

    var appUIState by mutableStateOf(AppUIState())

    private var fetchJob: Job? = null

    fun fetchCards(
        type: TypeCardCALINA? = null,
        state: StateCardCALINA? = null,
        after: (()->Unit)? = null
    ) {
        fetchJob?.cancel()
        val appViewModel = this
        fetchJob = viewModelScope.launch {
            appUIState = try {
                if(repository.count()==0)
                    repository.populate()

                val myImei = repository.getMyIMEI()
                val language = repository.getLanguage()
                val currentGroup = getCurrentSelect()

                //remove card that expired
                var cards = repository.byType(type, state, currentGroup)
                val now = LocalDate.now()
                var flag = false
                for(c in cards){
                    if(c.date_expire != null){
                        val localDate = c.date_expire.asLocalDate()
                        val dayDiff = ChronoUnit.DAYS.between(now, localDate)
                        if(dayDiff<0){
                            c.execTriggers(EventType.OnExpired, appViewModel)
                            delete(c, false)
                            flag = true
                        }
                    }
                }
                if(flag) cards = repository.byType(type, state, currentGroup)

                appUIState.copy(
                    cards = cards,
                    my_imei = myImei,
                    language = language,
                    currentGroup = currentGroup
                )

            }catch (ioe: IOException){
                val errorMessage = getMessagesFromThrowable(ioe)
                appUIState.copy(
                    errorMessage = errorMessage
                )
            }
            if(after!=null) after()
        }
    }

    suspend fun setLanguage(language: String){
        repository.setLanguage(language)
        appUIState = appUIState.copy(
            language = language,
            currentGroup = getCurrentSelect()
        )
    }

    fun getByID(imei_maker: String, imei_card: String) = runBlocking {
        repository.getByID(imei_maker, imei_card)
    }

    fun byType(type: TypeCardCALINA? = null):List<CardUIState> = runBlocking {
        repository.byType(type)
    }

    fun update(cardUIState: CardUIState) {
        viewModelScope.launch {
            repository.update(cardUIState)
        }
    }

    private fun getCurrentSelect(): CardUIState {
        var currentGroup: CardUIState? = null
        var first:CardUIState? = null
        for (c in byType(TypeCardCALINA.GROUP)) {
            if(first==null) first=c
            if (c.isSelect) {
                currentGroup = c
                break
            }
        }
        return currentGroup ?: first!!
    }

    fun updateGroupSelect(cardUIState: CardUIState? = null){
        viewModelScope.launch {
            if(cardUIState!=null) {
                appUIState.currentGroup?.let { update(it.copy(isSelect = false)) }
                appUIState = appUIState.copy(
                    currentGroup = cardUIState
                )
                appUIState.currentGroup?.let { update(it.copy(isSelect = true)) }
            }
            fetchCards(appUIState.filterCard, appUIState.stateCard)
        }
    }

    fun delete(cardUIState: CardUIState, _update: Boolean=true) {
        viewModelScope.launch {
            if(cardUIState.type == TypeCardCALINA.GROUP && !cardUIState.isSecondary){
                val numberGroups = byType(TypeCardCALINA.GROUP).filter { c->
                    !c.isSecondary && c.imei_maker == appUIState.currentGroup?.imei_maker
                }.size
                appUIState.cards.forEach { c ->
                    if(c.scope != null || numberGroups==1) repository.delete(c)
                }
                appUIState = appUIState.copy(
                    currentGroup = byType(TypeCardCALINA.GROUP).first()
                )
                appUIState.currentGroup?.let { update(it.copy(isSelect = true)) }
            }else {
                repository.delete(cardUIState)
            }
            if(_update) fetchCards(appUIState.filterCard, appUIState.stateCard)
        }
    }

    fun insert(cardUIState: CardUIState) {
        viewModelScope.launch {
            repository.insert(cardUIState)
            if(cardUIState.type == TypeCardCALINA.GROUP && !cardUIState.isSecondary)
                updateGroupSelect(cardUIState)
            else
                fetchCards(appUIState.filterCard, appUIState.stateCard)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(private val repository: AppRepository) :
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(repository).apply { fetchCards() } as T
    }
}