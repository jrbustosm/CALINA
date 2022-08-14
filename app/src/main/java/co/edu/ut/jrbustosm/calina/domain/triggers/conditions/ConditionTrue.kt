package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ConditionTrue(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean = true

}