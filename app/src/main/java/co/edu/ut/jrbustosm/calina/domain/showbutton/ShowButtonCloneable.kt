package co.edu.ut.jrbustosm.calina.domain.showbutton

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA

class ShowButtonCloneable : ShowUseCase() {

    override operator fun invoke(
        cardUIState: CardUIState,
        appUIState: AppUIState,
        context: Context?
    ): Boolean =
        cardUIState.isCloneable &&
        appUIState.isTeacher() &&
        cardUIState.state == StateCardCALINA.NORMAL
}