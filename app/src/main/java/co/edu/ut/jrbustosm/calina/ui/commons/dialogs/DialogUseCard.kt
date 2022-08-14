package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA

@Composable
fun DialogUseCard(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
){

    val myColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        focusedLabelColor = MaterialTheme.colors.onPrimary,
        cursorColor = MaterialTheme.colors.onPrimary,
        placeholderColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        textColor = MaterialTheme.colors.onPrimary
    )

    var name by remember { mutableStateOf("") }

    var showDialogName by remember { mutableStateOf(false) }
    if(showDialogName)
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
                    value = name,
                    onValueChange = {
                        if (it.length <= 30) name = it
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.name) + " (${name.length}/30)") },
                    placeholder = { Text(text = stringResource(R.string.name_usingcard)) },
                    colors = myColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val newCard = cardUIState.copy(
                            state = StateCardCALINA.USED,
                            imei_card = GenIMEI()(),
                            level = name
                        )
                        appViewModel.delete(cardUIState)
                        appViewModel.insert(newCard)
                        close()
                        navController.popBackStack()
                        if(cardUIState.execTriggers(EventType.OnUse, appViewModel)){
                            navController.navigate("main")
                        }else{
                            navController.navigate(
                                "cardDetail/${newCard.imei_maker}/${newCard.imei_card}"
                            )
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        }

    DialogConfirm(
        confirm = {
            if(cardUIState.cash > 0){
                if (!appViewModel.appUIState.isTeacher()) {
                    //Card of money
                    cardUIState.execTriggers(EventType.OnUse, appViewModel)
                    val current = appViewModel.currentGroup!!.copy(
                        cash = appViewModel.currentGroup!!.cash + cardUIState.cash
                    )
                    appViewModel.delete(cardUIState)
                    cardUIState.execTriggers(EventType.OnDestroy, appViewModel)
                    appViewModel.update(current)
                    appViewModel.updateGroupSelect(current)
                }
                close()
                navController.popBackStack()
                navController.navigate("main")
            }else {
                showDialogName = true
            }
        },
        cancel = close,
        text = stringResource(R.string.doyouwant_use)
    )
}