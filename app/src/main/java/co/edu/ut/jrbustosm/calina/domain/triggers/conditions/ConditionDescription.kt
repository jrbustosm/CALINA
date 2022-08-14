package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ConditionDescription(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    private var find: String

    init {
        find = if(params.isNotEmpty()) params[0]
        else ""
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean{
        if(find.isEmpty() || params.isEmpty()) return false
        //val oldCard = params[0] as CardUIState
        val re = """\b${find.uppercase()}\b""".toRegex()
        return re.containsMatchIn(cardUIState.description.uppercase())
    }

}