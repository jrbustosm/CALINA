package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

@Composable
fun DialogEditInfo(
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()->Unit
) {

    val myColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        focusedLabelColor = MaterialTheme.colors.onPrimary,
        cursorColor = MaterialTheme.colors.onPrimary,
        placeholderColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        textColor = MaterialTheme.colors.onPrimary
    )

    var description by remember { mutableStateOf(cardUIState.description) }
    Dialog(onDismissRequest = close) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .background(MaterialTheme.colors.background.copy(alpha = 0.9f))
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(5.dp)
            )
            .verticalScroll(scrollState)
            .padding(20.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = {
                    if (it.length <= 280) description = it
                },
                singleLine = false,
                label = { Text(text = stringResource(R.string.description) + " (${description.length}/280)") },
                placeholder = { Text(text = stringResource(R.string.descriptionofcard)) },
                colors = myColors,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = close) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                OutlinedButton(
                    enabled = description != "",
                    onClick = {
                        close()
                        val updateCard = cardUIState.copy(
                            description = description
                        )
                        appViewModel.update(updateCard)
                        navController.popBackStack()
                        if(updateCard.execTriggers(EventType.OnEdit, appViewModel, listOf(cardUIState))){
                            navController.navigate("main")
                        }else {
                            navController.navigate(
                                "cardDetail/${cardUIState.imei_maker}/${cardUIState.imei_card}"
                            )
                        }
                    }
                ){
                    Text(text = stringResource(R.string.edit))
                }
            }
        }
    }

}