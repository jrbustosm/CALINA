package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectReset(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params){

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        appViewModel.update(cardUIState.copy(
            count = 0
        ))
        return Pair(false, "")
    }
}