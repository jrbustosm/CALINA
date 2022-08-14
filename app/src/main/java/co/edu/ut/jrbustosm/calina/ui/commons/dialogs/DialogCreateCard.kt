package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.domain.cardcreator.*
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

@ExperimentalFoundationApi
@Composable
fun DialogCreateCard(
    appViewModel: AppViewModel,
    close: ()->Unit
) {

    /*
    --------------- CREATE CARD DIALOG
     */
    var showCreateCard by remember { mutableStateOf(false) }
    var useCase by remember { mutableStateOf< CardDialogCreate?>(null) }

    if(showCreateCard) DialogCreateCardUseCase(
        appViewModel = appViewModel,
        useCase = useCase!!
    ){
        showCreateCard = false
        close()
    }

    DialogButtons(
        close = close,
        select = { t->
            useCase = when(TypeCardCALINA.getType(t)){
                TypeCardCALINA.ACTION -> ActionDialogCreate()
                TypeCardCALINA.GROUP -> GroupSecondaryDialogCreate()
                TypeCardCALINA.INFORMATION -> InformationDialogCreate()
                TypeCardCALINA.REWARD -> RewardDialogCreate()
            }
            showCreateCard = true
        },
        title = stringResource(R.string.select_typecard),
        l = listOf(
            Pair(TypeCardCALINA.GROUP.id, R.string.filterGroup),
            Pair(TypeCardCALINA.ACTION.id, R.string.filterAction),
            Pair(TypeCardCALINA.INFORMATION.id, R.string.filterInformation),
            Pair(TypeCardCALINA.REWARD.id, R.string.filterReward)
        )
    )
}