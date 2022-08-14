package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

@Composable
fun DialogDestroy(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()-> Unit
){
    val textDialogDestroy =
        if(cardUIState.type == TypeCardCALINA.GROUP && !cardUIState.isSecondary)
            stringResource(R.string.destroy_group) + " " +
                    stringResource(R.string.should_i_continue)
        else
            stringResource(R.string.destroy_card) + " " +
                    stringResource(R.string.should_i_continue)

    DialogConfirm(
        confirm = {
            close()
            cardUIState.execTriggers(EventType.OnDestroy, appViewModel)
            appViewModel.delete(cardUIState)
            navController.navigate("main")
        },
        cancel = close,
        text = textDialogDestroy
    )
}