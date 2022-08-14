package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ConditionIMEI(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    var imei: String = ""

    init {
        if(params.isNotEmpty()) imei = params[0]
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean =
        imei == appViewModel.appUIState.my_imei
}