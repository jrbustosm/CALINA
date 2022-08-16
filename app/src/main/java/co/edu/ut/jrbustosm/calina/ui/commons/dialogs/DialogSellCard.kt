package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

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
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.encode.EncodeQRCard
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import kotlin.math.abs

@Suppress("UNUSED_PARAMETER")
@Composable
fun DialogSellCard(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
){

    var amountSell by remember { mutableStateOf(1) }
    var showDialogQR  by remember { mutableStateOf(false) }

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
                value = amountSell.toString(),
                onValueChange = {
                    val t = abs(it.toIntOrNull()?:0)
                    amountSell = t
                },
                singleLine = true,
                label = { Text(text = stringResource(R.string.amount_to_sell)) },
                placeholder = { Text(text = stringResource(R.string.amount_to_sell)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = myColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showDialogQR = true
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    }

    if(showDialogQR)
        DialogQR(
            text = EncodeQRCard()(cardUIState.copy(
                imei_owner = "-1",
                cash = amountSell,
                state = StateCardCALINA.BUY
            )),
            title = stringResource(R.string.qr_code_forsell),
            close = {
                showDialogQR = false
                close()
            }
        )
}