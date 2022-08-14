package co.edu.ut.jrbustosm.calina.domain.receivedCard

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class IsCloned: ReceivedCard() {

    override fun invoke(cardUIState: CardUIState, appViewModel: AppViewModel): Boolean =
        cardUIState.imei_owner == "-1"
}