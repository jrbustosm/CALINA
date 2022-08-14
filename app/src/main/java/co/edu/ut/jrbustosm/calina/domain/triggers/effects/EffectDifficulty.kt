package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA

class EffectDifficulty(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params) {

    var difficulty: DifficultyCardCALINA = DifficultyCardCALINA.EASY

    init {
        if(params.isNotEmpty()) difficulty = DifficultyCardCALINA.getDifficulty(params[0][0])
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        appViewModel.update(cardUIState.copy(
            difficulty = difficulty
        ))
        return Pair(false, "")
    }
}