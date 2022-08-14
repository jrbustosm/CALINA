package co.edu.ut.jrbustosm.calina.domain.receivedCard

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

abstract class ReceivedCard {

    abstract operator fun invoke(cardUIState: CardUIState, appViewModel: AppViewModel): Boolean
}