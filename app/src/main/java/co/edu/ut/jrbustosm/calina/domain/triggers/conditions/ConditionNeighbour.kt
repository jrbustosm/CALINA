package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ConditionNeighbour(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    var neighbours = mutableListOf<Int>()

    init {
        params.forEach { neighbours += it.toIntOrNull() ?: -1 }
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean{
        for(n in neighbours) if(!existNeighbour(n, appViewModel)) return false
        return true
    }

    private fun existNeighbour(n:Int, appViewModel: AppViewModel): Boolean =
        appViewModel.appUIState.cards.firstOrNull(){it.number==n} != null

}