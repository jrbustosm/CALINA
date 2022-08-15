package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectMoney(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params) {

    var n: Int = 0

    init {
        if(params.isNotEmpty()) n = params[0].toIntOrNull()?:0
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        val newCurrentGroup = appViewModel.appUIState.currentGroup!!.copy(
            cash = appViewModel.appUIState.currentGroup!!.cash + n
        )
        appViewModel.appUIState = appViewModel.appUIState.copy(
            currentGroup = newCurrentGroup
        )
        appViewModel.update(appViewModel.appUIState.currentGroup!!)
        appViewModel.updateGroupSelect(appViewModel.appUIState.currentGroup!!)
        return Pair(false, "")
    }
}