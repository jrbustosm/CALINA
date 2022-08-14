package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import co.edu.ut.jrbustosm.calina.domain.encode.EncodeQRCard
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

@Composable
fun DialogValidate(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
){

    var showDialogQR by remember { mutableStateOf(false) }
    if(showDialogQR) {
        val cardValidate = cardUIState.copy(
            imei_card = GenIMEI()()
        )
        DialogQR(
            text = EncodeQRCard()(cardValidate),
            title = stringResource(R.string.qr_validatecard),
            close = {
                showDialogQR = false
                close()
                cardUIState.execTriggers(EventType.OnValidate, appViewModel)
                appViewModel.delete(cardUIState)
                navController.popBackStack()
                navController.navigate("main")
            }
        )
    }

    DialogConfirm(
        confirm = { showDialogQR = true },
        cancel = close,
        text = stringResource(R.string.when_validate)
    )
}