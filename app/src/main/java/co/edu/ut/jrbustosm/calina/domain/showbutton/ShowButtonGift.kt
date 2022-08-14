package co.edu.ut.jrbustosm.calina.domain.showbutton

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

class ShowButtonGift: ShowUseCase() {

    override operator fun invoke(
        cardUIState: CardUIState,
        appUIState: AppUIState,
        context: Context?
    ): Boolean =
        (cardUIState.type != TypeCardCALINA.GROUP ||
        cardUIState.type == TypeCardCALINA.GROUP && cardUIState.isSecondary) &&
        cardUIState.isTransfer &&
        cardUIState.state == StateCardCALINA.NORMAL
}