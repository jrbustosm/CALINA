package co.edu.ut.jrbustosm.calina.viewmodels.states

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.domain.triggers.Trigger
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import com.google.gson.Gson
import java.util.*

data class CardUIState(
    val imei_card: String,
    val imei_maker: String,
    val title: String,
    val description: String,
    val imageResource: String,
    val type: TypeCardCALINA,
    val difficulty: DifficultyCardCALINA,
    val isSymbol: Boolean,
    val number: Int,
    val imei_owner: String,
    val trigger: String,
    val state: StateCardCALINA,
    val count: Int,
    val date_create: Date,
    val date_expire: Date?,
    val date_reg: Date?,
    val scope: String?,
    val isTransfer: Boolean,
    val isCloneable: Boolean,
    val isEdit: Boolean,
    val cash: Int,
    val cash_symbol: Char,
    val isSelect: Boolean,
    val isSecondary: Boolean,
    val levels: String,
    val level: String,
    val url: String,
    val lang: String
){
    var triggers: MutableList<Trigger> = mutableListOf()

    init {
        trigger.split('|').forEach {
            triggers += Trigger(
                sentence = it,
                cardUIState = this,
            )
        }
    }

    fun toJson(): String {
        val cardLight = CardUIStateLight(
            imei_card, imei_maker, title, description, imageResource, type, difficulty, isSymbol,
            number, imei_owner, trigger, state, count, date_create, date_expire, date_reg, scope,
            isTransfer, isCloneable, isEdit, cash, cash_symbol, isSelect, isSecondary, levels,
            level, url, lang
        )
        return Gson().toJson(cardLight, CardUIStateLight::class.java)
    }

    fun execTriggers(
        eventType: EventType,
        appViewModel: AppViewModel,
        params: List<Any> = emptyList()
    ): Boolean{
        var flag = false
        val sb = StringBuilder()
        triggers.filter {
            it.event == eventType
        }.forEach {
            val (f, msg) = it(appViewModel, params)
            flag = flag || f
            if(msg.isNotEmpty()) {
                sb.append(msg)
                sb.append("\n")
            }
        }
        if(sb.isNotEmpty())
            appViewModel.appUIState.message = appViewModel.appUIState.message + "\n" + sb.toString()
        if(appViewModel.appUIState.currentGroup!=null)
            appViewModel.updateGroupSelect(appViewModel.appUIState.currentGroup!!)
        return flag
    }
}

enum class StateCardCALINA(val id: Char){
    NORMAL('N'),        //NORMAL State
    USED('U'),          //Action Card in USED State
    BUY('B');           //Card for BUY State

    override fun toString(): String {
        return id.toString()
    }

    companion object{
        fun getState(state: Char): StateCardCALINA = when(state){
            'U' -> USED
            'B' -> BUY
            else -> NORMAL
        }
    }
}
enum class DifficultyCardCALINA(val id: Char) {
    VERY_EASY('C'),  //VERY EASY (C)
    EASY('B'),       //EASY (B)
    NORMAL('A'),     //NORMAL (A)
    HARD('S'),       //HARD (S)
    VERY_HARD('W'),  //VERY HARD
    UNIQUE('X');      //UNIQUE

    override fun toString(): String {
        if(id=='W') return "SS"
        return id.toString()
    }

    @Composable
    fun toText(): String {
        return when (id) {
            'C' -> stringResource(id = R.string.very_easy)
            'B' -> stringResource(id = R.string.easy)
            'A' -> stringResource(id = R.string.normal)
            'S' -> stringResource(id = R.string.hard)
            'W' -> stringResource(id = R.string.very_hard)
            else -> stringResource(id = R.string.unique)
        }
    }

    companion object{
        fun getDifficulty(difficulty: Char): DifficultyCardCALINA = when(difficulty){
            'X' -> UNIQUE
            'W' -> VERY_HARD
            'S' -> HARD
            'A' -> NORMAL
            'B' -> EASY
            else -> VERY_EASY
        }
    }
}

enum class TypeCardCALINA(val id: Char) {
    ACTION('X'),
    REWARD('R'),
    INFORMATION('I'),
    GROUP('G');

    override fun toString(): String {
        return id.toString()
    }

    companion object{
        fun getType(type: Char): TypeCardCALINA = when(type){
            'X' -> ACTION
            'R' -> REWARD
            'G' -> GROUP
            else -> INFORMATION
        }
    }
}