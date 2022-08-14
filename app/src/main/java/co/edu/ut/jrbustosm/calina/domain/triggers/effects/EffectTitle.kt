package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectTitle(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params){

    var title: String = ""

    init {
        if(params.isNotEmpty()) title = params[0]
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        appViewModel.update(cardUIState.copy(
            title = title
        ))
        return Pair(false, "")
    }
}