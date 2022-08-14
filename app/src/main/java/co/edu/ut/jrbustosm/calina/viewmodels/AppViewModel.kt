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

    var currentGroup: CardUIState? = null
    var appUIState by mutableStateOf(AppUIState())
        private set

    private var fetchJob: Job? = null

    fun fetchCards(type: TypeCardCALINA? = null, state: StateCardCALINA? = null) {
        fetchJob?.cancel()
        val appViewModel = this
        fetchJob = viewModelScope.launch {
            appUIState = try {
                if(repository.count()==0) {
                    repository.populate()
                    initCurrentSelect()
                }
                var cards = repository.byType(type, state, currentGroup)
                val myImei = repository.getMyIMEI()

                //remove card that expired
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
                    my_imei = myImei!!,
                    currentGroup = currentGroup
                )
            }catch (ioe: IOException){
                val errorMessage = getMessagesFromThrowable(ioe)
                appUIState.copy(errorMessage = errorMessage)
            }
        }
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

    fun initCurrentSelect() {
        var first:CardUIState? = null
        for (c in byType(TypeCardCALINA.GROUP)) {
            if(first==null) first=c
            if (c.isSelect) {
                currentGroup = c
                break
            }
        }
        if(currentGroup==null) currentGroup=first
    }

    fun updateGroupSelect(cardUIState: CardUIState){
        viewModelScope.launch {
            currentGroup?.let { update(it.copy(isSelect = false)) }
            currentGroup = cardUIState
            fetchCards(appUIState.filterCard, appUIState.stateCard)
            currentGroup?.let { update(it.copy(isSelect = true)) }
        }
    }

    fun delete(cardUIState: CardUIState, _update: Boolean=true) {
        viewModelScope.launch {
            if(cardUIState.type == TypeCardCALINA.GROUP && !cardUIState.isSecondary){
                val numberGroups = byType(TypeCardCALINA.GROUP).filter { c->
                    !c.isSecondary && c.imei_maker == currentGroup?.imei_maker
                }.size
                appUIState.cards.forEach { c ->
                    if(c.scope != null || numberGroups==1) repository.delete(c)
                }
                currentGroup = byType(TypeCardCALINA.GROUP).first()
                currentGroup?.let { update(it.copy(isSelect = true)) }
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
        return AppViewModel(repository).apply { initCurrentSelect() } as T
    }
}