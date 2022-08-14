package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.edu.ut.jrbustosm.calina.CaptureActivityPortrait
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.encode.EncodeQRCash
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlin.math.abs

@Composable
fun DialogSendMoney(
    appViewModel: AppViewModel,
    context: Context,
    close: ()->Unit
){

    var amountSend by remember { mutableStateOf(1) }

    var showDialogError by remember { mutableStateOf(false) }
    if(showDialogError) DialogConfirm(
        confirm = {
            showDialogError = false
            close()
        },
        cancel = null,
        text = stringResource(R.string.corrupt_identity)
    )

    var showDialogQRSend by remember { mutableStateOf(false) }
    var textQRSend by remember { mutableStateOf("") }
    if(showDialogQRSend)
        DialogQR(
            text = textQRSend,
            title = stringResource(R.string.QRcode_forreading)
        ) {
            showDialogQRSend = false
            close()
        }

    val scanLauncherSendCash = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            try {
                textQRSend = EncodeQRCash()(
                    amount = amountSend,
                    identity = result.contents,
                    appViewModel = appViewModel,
                    context = context
                )
                if (!appViewModel.appUIState.isTeacher()) {
                    val current = appViewModel.currentGroup!!.copy(
                        cash = appViewModel.currentGroup!!.cash - amountSend
                    )
                    appViewModel.update(current)
                    appViewModel.updateGroupSelect(current)
                }
                showDialogQRSend = true
            }catch (e: Exception){
                showDialogError = true
            }
        }
    }

    val scanOptions = ScanOptions()
    scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    scanOptions.setOrientationLocked(true)
    scanOptions.setPrompt(stringResource(R.string.reading_identityQRcode))
    scanOptions.setBeepEnabled(false)
    scanOptions.setCaptureActivity(CaptureActivityPortrait().javaClass)

    val myColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        focusedLabelColor = MaterialTheme.colors.onPrimary,
        cursorColor = MaterialTheme.colors.onPrimary,
        placeholderColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        textColor = MaterialTheme.colors.onPrimary
    )

    Dialog(onDismissRequest = close) {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.background.copy(alpha = 0.9f))
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(20.dp)
        ) {
            OutlinedTextField(
                value = amountSend.toString(),
                onValueChange = {
                    val t = abs(it.toIntOrNull()?:0)
                    amountSend = if(!appViewModel.appUIState.isTeacher())
                        if(t>appViewModel.currentGroup!!.cash) appViewModel.currentGroup!!.cash
                        else t
                    else
                        t
                },
                singleLine = true,
                label = { Text(text = stringResource(R.string.amount_to_send)) },
                placeholder = { Text(text = stringResource(R.string.amount_to_send)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = myColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scanLauncherSendCash.launch(scanOptions)
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    }
}