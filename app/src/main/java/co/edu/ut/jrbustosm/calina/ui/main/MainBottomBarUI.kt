package co.edu.ut.jrbustosm.calina.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogQR
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogCreateCard
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogSendMoney
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel

@ExperimentalFoundationApi
@Composable
fun GetMainBottomBarUI(appViewModel: AppViewModel){

    val context = LocalContext.current


    /*
    ------------ DIALOG CREATE CARD
     */
    var showCreateTypeCard by remember { mutableStateOf(false)}
    if(showCreateTypeCard)
        DialogCreateCard(
            appViewModel = appViewModel
        ){ showCreateTypeCard = false }

    /*
    -------------- SHOW QR IDENTITY
     */
    var showQRIdentity by remember { mutableStateOf(false)}
    if(showQRIdentity){
        DialogQR(
            text = appViewModel.appUIState.my_imei,
            title = stringResource(R.string.identity_QRcode)
        ) { showQRIdentity = false}
    }

    /*
    ---------------- SHOW SEND MONEY DIALOG
     */

    var showAmountSend by remember { mutableStateOf(false) }
    if(showAmountSend)
        DialogSendMoney(
            appViewModel = appViewModel,
            context = context
        ){
            showAmountSend = false
        }


    /*
    -------------- CONTROLS AREA
     */
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        cutoutShape = CircleShape
    ) {
        val screens = if(appViewModel.appUIState.isTeacher()) listOf(
            Pair(stringResource(R.string.make_card), Pair(Icons.Filled.AddCircle) {showCreateTypeCard=true}),
            Pair(stringResource(R.string.send_money), Pair(Icons.Filled.Send){
                if(appViewModel.appUIState.isTeacher() ||
                    !appViewModel.appUIState.isTeacher() && appViewModel.appUIState.currentGroup!!.cash>0)
                    showAmountSend=true
            })
        ) else listOf(
            Pair(stringResource(R.string.identity_QR_code), Pair(Icons.Filled.Person){showQRIdentity=true}),
            Pair(stringResource(R.string.send_money), Pair(Icons.Filled.Send){
                if(appViewModel.appUIState.isTeacher() ||
                    !appViewModel.appUIState.isTeacher() && appViewModel.appUIState.currentGroup!!.cash>0)
                    showAmountSend=true})
        )
        screens.forEach { screen ->
            val (text, T) = screen
            val (icon, onClick) = T
            BottomNavigationItem(
                icon = { Icon(imageVector = icon, contentDescription = "") },
                label = { Text(text) },
                selected = false,
                onClick = onClick,
            )
        }
    }
}