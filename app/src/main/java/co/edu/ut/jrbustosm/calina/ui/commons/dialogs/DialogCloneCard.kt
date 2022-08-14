package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.encode.EncodeQRCard
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

@Suppress("UNUSED_PARAMETER")
@Composable
fun DialogCloneCard(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
){

    DialogQR(
        text = EncodeQRCard()(cardUIState.copy(imei_owner = "-1")),
        title = stringResource(R.string.qur_code_forclone),
        close = close
    )

}