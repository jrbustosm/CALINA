package co.edu.ut.jrbustosm.calina.domain.triggers

import co.edu.ut.jrbustosm.calina.domain.triggers.conditions.Condition
import co.edu.ut.jrbustosm.calina.domain.triggers.conditions.ConditionType
import co.edu.ut.jrbustosm.calina.domain.triggers.effects.Effect
import co.edu.ut.jrbustosm.calina.domain.triggers.effects.EffectType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class Trigger(
    val sentence: String,
    val cardUIState: CardUIState,
) {

    var event: EventType? = null
    private val conditions = mutableListOf<Condition>()
    private val effects = mutableListOf<Effect>()

    init {
        val e = Regex("[${EventType.getAllValues()}]")
        val c = Regex("[${ConditionType.getAllValues()}](:[^|_#:]+)*")
        val a = Regex("[${EffectType.getAllValues()}](:[^|_#:]+)*")
        val re = Regex("^(?<event>${e.pattern})_" +
                "(?<conditions>${c.pattern}(#${c.pattern})*)_" +
                "(?<effects>${a.pattern}(#${a.pattern})*)$")
        val match = re.find(sentence)
        if (match != null) {
            event = EventType.getEventType(match.groups["event"]!!.value[0])!!
            match.groups["conditions"]?.value?.split('#')?.forEach{ sequence->
                val type = ConditionType.getConditionType(sequence[0])
                if (sequence.substring(startIndex = 1).matches(type.re))
                    conditions += Condition.factory(
                        conditionType = type,
                        cardUIState = cardUIState,
                        params = if(sequence.length<=2) emptyList()
                                else sequence.substring(startIndex = 2).split(':')
                    )
            }
            match.groups["effects"]?.value?.split('#')?.forEach{ sequence->
                val type = EffectType.getEffectType(sequence[0])
                if (sequence.substring(startIndex = 1).matches(type.re))
                    effects += Effect.factory(
                        effectType = type,
                        cardUIState = cardUIState,
                        params = if(sequence.length<=2) emptyList()
                        else sequence.substring(startIndex = 2).split(':'),
                    )
            }
        }
    }

    /*
    If return true, it involves going back to the main screen
     */
    operator fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        var flag = false
        val sb = StringBuilder()
        if(!appViewModel.appUIState.isTeacher())
            if(conditions.map { it(appViewModel, params) }.reduce { a, b -> a && b }){
                effects.forEach{
                    val (f, msg) = it(appViewModel, params)
                    flag = flag || f
                    if(msg.isNotEmpty()){
                        sb.append(msg)
                        sb.append("\n")
                    }
                }
            }
        return Pair(flag, sb.toString())
    }

}

