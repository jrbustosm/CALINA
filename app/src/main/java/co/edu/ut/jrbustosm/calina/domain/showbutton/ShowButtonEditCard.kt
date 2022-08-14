package co.edu.ut.jrbustosm.calina.domain.showbutton

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.states.AppUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class ShowButtonEditCard: ShowUseCase() {
    override fun invoke(
        cardUIState: CardUIState,
        appUIState: AppUIState,
        context: Context?,
    ): Boolean = appUIState.isTeacher()
}