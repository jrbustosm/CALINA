package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

class EffectRemoveTrigger(
    cardUIState: CardUIState,
    params: List<String>
): Effect(cardUIState, params){

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String> {
        val newCard = cardUIState.copy(
            trigger = ""
        )
        if(cardUIState.type==TypeCardCALINA.GROUP && !cardUIState.isSecondary &&
                cardUIState.imei_card == appViewModel.appUIState.currentGroup!!.imei_card)
            appViewModel.appUIState = appViewModel.appUIState.copy(
                currentGroup = newCard
            )
        appViewModel.update(newCard)
        return Pair(false, "")
    }
}