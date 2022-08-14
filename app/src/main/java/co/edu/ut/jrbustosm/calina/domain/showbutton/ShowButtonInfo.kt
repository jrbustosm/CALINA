package co.edu.ut.jrbustosm.calina.domain.showbutton

import android.content.Context
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ShowButtonInfo: ShowUseCase() {

    override operator fun invoke(
        cardUIState: CardUIState,
        appUIState: AppUIState,
        context: Context?,
    ): Boolean =
        cardUIState.url.isNotEmpty()
}