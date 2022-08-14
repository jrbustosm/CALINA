package co.edu.ut.jrbustosm.calina.domain.receivedCard

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

class IsGroupCard: ReceivedCard() {

    override fun invoke(cardUIState: CardUIState, appViewModel: AppViewModel): Boolean =
        cardUIState.type == TypeCardCALINA.GROUP && !cardUIState.isSecondary

}