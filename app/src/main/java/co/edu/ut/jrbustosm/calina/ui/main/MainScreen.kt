package co.edu.ut.jrbustosm.calina.ui.main

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.edu.ut.jrbustosm.calina.CaptureActivityPortrait
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.encode.DecodeQRCard
import co.edu.ut.jrbustosm.calina.domain.receivedCard.*
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.ui.commons.GetBackgroundImg
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogConfirm
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun GetMainScreen(
    appViewModel: AppViewModel,
    navController: NavHostController,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var showDialogOk by remember { mutableStateOf(false)}
    var textDialogOk by remember { mutableStateOf("")}

    val textErrorBelong = stringResource(R.string.the_read_cards_doesnot)
    val textErrorMalformed = stringResource(R.string.corrupt_card)
    val textErrorAlready = stringResource(R.string.the_card_already_exist)

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

    if(showDialogOk)
        DialogConfirm(
            confirm = { showDialogOk=false },
            cancel = null,
            text = textDialogOk
        )

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
    ) { result ->
        if (result.contents != null) {
            try{
                var cardUIState = DecodeQRCard()(result.contents)
                if(IsCloned()(cardUIState, appViewModel)){
                    cardUIState = cardUIState.copy(
                        imei_owner = appViewModel.appUIState.my_imei
                    )
                }
                if( (IsValidated()(cardUIState, appViewModel) ||
                    IsMyCard()(cardUIState, appViewModel)) &&
                    (IsLocal()(cardUIState, appViewModel) ||
                    IsGlobal()(cardUIState, appViewModel) ||
                    IsGroupCard()(cardUIState, appViewModel))
                ) {
                    if(appViewModel.getByID(cardUIState.imei_maker, cardUIState.imei_card) == null) {
                        appViewModel.insert(cardUIState)
                        cardUIState.execTriggers(EventType.OnReceive, appViewModel)
                    }else{
                        textDialogOk = textErrorAlready
                        showDialogOk = true
                    }
                }else{
                    textDialogOk = textErrorBelong
                    showDialogOk = true
                }
            }catch (e: Exception){
                textDialogOk = textErrorMalformed
                showDialogOk = true
            }
        }
    }

    val scanOptions = ScanOptions()
    scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    scanOptions.setOrientationLocked(true)
    scanOptions.setPrompt(stringResource(R.string.reading_QRcode))
    scanOptions.setBeepEnabled(false)
    scanOptions.setCaptureActivity(CaptureActivityPortrait().javaClass)

    Scaffold(
        topBar = {
            GetMainTopBarUI(appViewModel) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        bottomBar = { GetMainBottomBarUI(
            appViewModel = appViewModel
        )},
        scaffoldState = scaffoldState,
        drawerContent = {
            GetMainDrawerUI(appViewModel){
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerBackgroundColor = MaterialTheme.colors.primary,
        drawerContentColor = MaterialTheme.colors.onPrimary,
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {scanLauncher.launch(scanOptions)}
            ) {
                Icon(
                    painterResource(id = R.drawable.qr),"",
                    modifier = Modifier.size(52.dp)
                )
            }
        },
    ) { padding ->
        Box(Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            if (appViewModel.appUIState.isFetchingCards) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            GetBackgroundImg()
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                GetCashUI(appViewModel)
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(100.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(appViewModel.appUIState.cards) { card ->
                        card.execTriggers(EventType.OnList, appViewModel)
                        GetMiniCardUI(cardUIState = card, onClick = {
                            card.execTriggers(EventType.OnOpenDetail, appViewModel)
                            navController.navigate(
                                route = "cardDetail/${card.imei_maker}/${card.imei_card}"
                            )
                        })
                    }
                }
            }
        }
    }
}
