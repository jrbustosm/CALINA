package co.edu.ut.jrbustosm.calina.domain.receivedCard

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

class IsValidated: ReceivedCard() {

    override fun invoke(cardUIState: CardUIState, appViewModel: AppViewModel): Boolean =
        appViewModel.appUIState.isTeacher() &&
        cardUIState.type == TypeCardCALINA.ACTION &&
        cardUIState.state == StateCardCALINA.USED
}