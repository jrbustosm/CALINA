package co.edu.ut.jrbustosm.calina.ui.cardDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.cardcreator.*
import co.edu.ut.jrbustosm.calina.domain.showbutton.*
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.*
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

@ExperimentalFoundationApi
private fun factory(
    useCase: ShowUseCase,
): @Composable (
    cardUIState: CardUIState,
    appViewModel: AppViewModel,
    navController: NavController,
    close: ()-> Unit)->Unit
{
    when (useCase) {
        is ShowButtonUse -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogUseCard(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonGift -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogSendCard(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonCloneable -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogCloneCard(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonBuy -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogBuyCard(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonSell -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogSellCard(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonEdit -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogEditInfo(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonValidate -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogValidate(cardUIState,appViewModel,navController,close)
        }
        is ShowButtonEditCard -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogCreateCardUseCase(
                appViewModel = appViewModel,
                useCase = with(cardUIState){
                    if(type == TypeCardCALINA.ACTION) ActionDialogCreate()
                    else if(type == TypeCardCALINA.INFORMATION) InformationDialogCreate()
                    else if(type == TypeCardCALINA.REWARD) RewardDialogCreate()
                    else if(type == TypeCardCALINA.GROUP && isSecondary) GroupSecondaryDialogCreate()
                    else GroupDialogCreate()
                },
                cardUIState = cardUIState,
                close = {
                    close()
                    navController.popBackStack()
                    navController.navigate(
                        "cardDetail/${cardUIState.imei_maker}/${cardUIState.imei_card}"
                    )
                }
            )
        }
        //DESTROY
        else -> return { cardUIState: CardUIState, appViewModel: AppViewModel, navController: NavController, close: () -> Unit ->
            DialogDestroy(cardUIState,appViewModel,navController,close)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun GetCardDetailBottomBarUI(
    navController: NavController,
    cardUIState: CardUIState,
    appViewModel: AppViewModel
){

    val appUIState = appViewModel.appUIState

    BottomAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    ){

        val l = listOf(
            Pair(ShowButtonUse(), Pair(
                stringResource(R.string.use), Icons.Filled.PlayArrow
            )),
            Pair(ShowButtonValidate(), Pair(
                stringResource(R.string.validate), Icons.Filled.CheckCircle
            )),
            Pair(ShowButtonGift(), Pair(
                stringResource(R.string.gift), Icons.Filled.Person
            )),
            Pair(ShowButtonCloneable(), Pair(
                stringResource(R.string.clone), Icons.Filled.Share
            )),
            Pair(ShowButtonBuy(), Pair(
                stringResource(R.string.buy), Icons.Filled.ShoppingCart
            )),
            Pair(ShowButtonSell(), Pair(
                stringResource(R.string.sell), Icons.Filled.ShoppingCart
            )),
            Pair(ShowButtonEdit(), Pair(
                stringResource(R.string.edit), Icons.Filled.Edit
            )),
            Pair(ShowButtonDestroy(), Pair(
                stringResource(R.string.destroy), Icons.Filled.Delete
            )),
            Pair(ShowButtonEditCard(), Pair(
                stringResource(R.string.editcard), Icons.TwoTone.Edit
            ))
        )

        val count = l.filter { it.first(cardUIState, appUIState, LocalContext.current) }.size

        for(it in l){
            val useCase = it.first

            if(useCase(cardUIState, appUIState, LocalContext.current)){

                var showDialog by remember { mutableStateOf(false) }
                var useCaseSelect by remember { mutableStateOf(useCase) }
                if (showDialog) factory(useCase = useCaseSelect)(
                    cardUIState = cardUIState,
                    appViewModel = appViewModel,
                    navController = navController
                ){
                    showDialog = false
                }

                val (text, icon) = it.second
                BottomNavigationItem(
                    icon = { Icon(imageVector = icon, contentDescription = "") },
                    label = { if(count<=5) Text(text) },
                    selected = false,
                    onClick = {
                        useCaseSelect = useCase
                        showDialog = true
                    },
                )
            }
        }
        if(ShowButtonInfo()(cardUIState, appUIState, LocalContext.current)){
            val uriHandler = LocalUriHandler.current
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
                label = { if(count<=5) Text(stringResource(R.string.info)) },
                selected = false,
                onClick = {
                    uriHandler.openUri(cardUIState.url)
                },
            )
        }
    }
}