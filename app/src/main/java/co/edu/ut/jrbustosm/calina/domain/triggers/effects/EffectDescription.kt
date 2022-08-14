package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectDescription(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params){

    var description: String = ""

    init {
        if(params.isNotEmpty()) description = params[0]
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        appViewModel.update(cardUIState.copy(
            description = description
        ))
        return Pair(false, "")
    }
}