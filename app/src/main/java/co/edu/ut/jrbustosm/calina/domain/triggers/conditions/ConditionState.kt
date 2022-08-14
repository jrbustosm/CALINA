package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA

class ConditionState(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    var state: StateCardCALINA? = null

    init {
        if(params.isNotEmpty()) state = StateCardCALINA.getState(params[0][0])
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean =
        cardUIState.state == state
}