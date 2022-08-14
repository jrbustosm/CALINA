package co.edu.ut.jrbustosm.calina.ui.cardDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.asLocalDate
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.ui.commons.GetBackTopBarUI
import co.edu.ut.jrbustosm.calina.ui.commons.GetBackgroundImg
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogConfirm
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExperimentalFoundationApi
@Composable
fun GetCardDetailScreen(
    cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController
){
    cardUIState.execTriggers(EventType.OnOpenDetail, appViewModel)

    var showDialogMsg by remember { mutableStateOf(appViewModel.appUIState.message.isNotEmpty())}
    if(showDialogMsg){
        DialogConfirm(
            confirm = {
                appViewModel.appUIState.message = ""
                showDialogMsg = false
            },
            cancel = null,
            text = appViewModel.appUIState.message
        )
    }

    Scaffold(
        topBar = { GetBackTopBarUI(navController, appViewModel.currentGroup?.title ?: "") },
        bottomBar = { GetCardDetailBottomBarUI(navController, cardUIState, appViewModel)}
    ) { padding ->
        Box(Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            GetBackgroundImg()
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                GetCardUI(cardUIState = cardUIState, appViewModel = appViewModel)
            }
            var showSnackBar by remember { mutableStateOf(true) }
            if(showSnackBar) {
                Snackbar(
                    backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.8f),
                    contentColor = MaterialTheme.colors.onPrimary,
                ) {
                    if (cardUIState.date_expire == null)
                        Text(text = "Card never expired")
                    else{
                        val localDate = cardUIState.date_expire.asLocalDate()
                        val now = LocalDate.now()
                        val dayDiff = ChronoUnit.DAYS.between(now, localDate)
                        Text(text = "$dayDiff ${stringResource(R.string.daysforexpired)}")
                    }
                }
                rememberCoroutineScope().launch {
                    delay(3000).apply {
                        showSnackBar = false
                    }
                }
            }
        }
    }
}
