package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.CaptureActivityPortrait
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.encode.EncodeQRCard
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun DialogSendCard(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
){
    val scanOptions = ScanOptions()
    scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    scanOptions.setOrientationLocked(true)
    scanOptions.setBeepEnabled(false)
    scanOptions.setCaptureActivity(CaptureActivityPortrait().javaClass)

    var textQRSend by remember { mutableStateOf("") }
    var textQRSendMsg by remember { mutableStateOf("") }
    var showDialogOk by remember { mutableStateOf(false)}
    var textDialogOk by remember { mutableStateOf("")}
    val textErrorMalformed = stringResource(R.string.corrupt_identity)

    if(showDialogOk)
        DialogConfirm(
            confirm = {
                showDialogOk=false
                close()
            },
            cancel = null,
            text = textDialogOk
        )

    var showDialogQRSend by remember { mutableStateOf(false)}
    if(showDialogQRSend)
        DialogQR(
            text = textQRSend,
            title = textQRSendMsg
        ) {
            showDialogQRSend = false
            close()
            cardUIState.execTriggers(EventType.OnGift, appViewModel)
            appViewModel.delete(cardUIState)
            navController.popBackStack()
            navController.navigate("main")
        }

    val msgQR = stringResource(R.string.qur_code_forgift)
    val scanLauncherSendCash = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            try {
                val imeiOwner = result.contents
                textQRSend = EncodeQRCard()(cardUIState.copy(imei_owner = imeiOwner))
                textQRSendMsg = msgQR
                showDialogQRSend = true
            }catch (e: Exception){
                textDialogOk = textErrorMalformed
                showDialogOk = true
            }
        }
    }

    val textGiftIdentity = stringResource(R.string.reading_identityrecipient)
    DialogConfirm(
        confirm = {
            scanOptions.setPrompt(textGiftIdentity)
            scanLauncherSendCash.launch(scanOptions)
        },
        cancel = close,
        text = stringResource(R.string.when_givingacard)
    )
}