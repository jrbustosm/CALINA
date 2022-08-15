package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ConditionMoney(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    var n: Int = 0

    init {
        if(params.isNotEmpty()) n = params[0].toIntOrNull()?:0
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean =
        (appViewModel.appUIState.currentGroup?.cash ?: 0) >= n
}