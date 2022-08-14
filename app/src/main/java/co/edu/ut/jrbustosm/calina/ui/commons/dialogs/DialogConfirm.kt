package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import co.edu.ut.jrbustosm.calina.R

@Composable
fun DialogConfirm(confirm: ()-> Unit, cancel: (()-> Unit)?, text: String) {
    if (cancel != null) {
        AlertDialog(
            onDismissRequest = cancel,
            confirmButton = {
                TextButton(onClick = confirm)
                { Text(text = stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = cancel)
                { Text(text = stringResource(R.string.cancel)) }
            },
            title = { Text(text = stringResource(R.string.please_confirm)) },
            text = { Text(text = text) }
        )
    }else{
        AlertDialog(
            onDismissRequest = confirm,
            confirmButton = {
                TextButton(onClick = confirm)
                { Text(text = stringResource(R.string.ok)) }
            },
            title = { Text(text = stringResource(R.string.please_confirm)) },
            text = { Text(text = text) }
        )
    }
}