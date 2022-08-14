package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectImage(
    cardUIState: CardUIState,
    params: List<String>,
): Effect(cardUIState, params) {

    var image: String = "item1"

    init {
        if(params.isNotEmpty()) image = params[0]
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        appViewModel.update(cardUIState.copy(
            imageResource = image,
            isSymbol = image.startsWith("symbol")
        ))
        return Pair(false, "")
    }
}