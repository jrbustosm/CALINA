package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EffectMessage(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params){

    var msg: String = ""

    init {
        if(params.isNotEmpty()) msg = params[0]
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        println(msg)
        return Pair(false, msg)
    }
}