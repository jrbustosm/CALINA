package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA

@Composable
fun DialogBuyCard(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()-> Unit
){

    var showDialogNoMoney by remember { mutableStateOf(false) }
    DialogConfirm(
        confirm = {
            if(appViewModel.currentGroup!!.cash >= cardUIState.cash){
                val current = appViewModel.currentGroup!!.copy(
                    cash = appViewModel.currentGroup!!.cash - cardUIState.cash
                )
                cardUIState.execTriggers(EventType.OnBuy, appViewModel)
                appViewModel.update(cardUIState.copy(
                    state = StateCardCALINA.NORMAL,
                    cash = 0
                ))
                appViewModel.update(current)
                appViewModel.updateGroupSelect(current)
                close()
                navController.popBackStack()
                navController.navigate(
                    "cardDetail/${cardUIState.imei_maker}/${cardUIState.imei_card}"
                )
            }else showDialogNoMoney = true
        },
        cancel = close,
        text = "${stringResource(R.string.you_wantspend)} " +
                "${appViewModel.currentGroup!!.cash_symbol} ${cardUIState.cash}" +
                " ${stringResource(R.string.to_buy_card)}." +
                stringResource(R.string.you_have_money) +
                " ${appViewModel.currentGroup!!.cash_symbol} ${appViewModel.currentGroup!!.cash}"
    )

    if(showDialogNoMoney)
        DialogConfirm(
            confirm = {
                showDialogNoMoney = false
                close()
            },
            cancel = null,
            text = stringResource(R.string.you_dontenoughmoney)
        )
}