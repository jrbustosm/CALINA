package co.edu.ut.jrbustosm.calina.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

@Composable
fun GetMainTopBarUI(appViewModel: AppViewModel, onClickMenu:()->Unit) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        title = { GetTitle(appViewModel) },
        navigationIcon = { GetNavButton(onClickMenu) },
        actions = {
            GetFilterButton{showMenu = !showMenu }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .background(MaterialTheme.colors.onBackground)
                    .width(200.dp),
            ) {
                val items = listOf(
                    Pair(stringResource(R.string.filterAll), Pair(null, null)),
                    Pair(stringResource(R.string.filterAction), Pair(TypeCardCALINA.ACTION, null)),
                    Pair(stringResource(R.string.filterInformation), Pair(TypeCardCALINA.INFORMATION, null)),
                    Pair(stringResource(R.string.filterGroup), Pair(TypeCardCALINA.GROUP, null)),
                    Pair(stringResource(R.string.filterReward), Pair(TypeCardCALINA.REWARD, null)),
                    Pair(stringResource(R.string.buy), Pair(null, StateCardCALINA.BUY)),
                    Pair(stringResource(R.string.used), Pair(null, StateCardCALINA.USED))
                )
                items.forEach {
                    val (text, p) = it
                    val (type, state) = p
                    DropdownMenuItem(onClick = {
                        appViewModel.appUIState.filterCard = type
                        appViewModel.appUIState.stateCard = state
                        appViewModel.fetchCards(type, state)
                        showMenu=false
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.alpha(
                                if(
                                    appViewModel.appUIState.filterCard==type &&
                                    appViewModel.appUIState.stateCard==state) 1f
                                else 0f
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = text,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun GetFilterButton(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = null
        )
    }
}

@Composable
private fun GetNavButton(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null
        )
    }
}

@Composable
private fun GetTitle(appViewModel: AppViewModel) {
    val title = appViewModel.appUIState.currentGroup?.title ?: stringResource(id = R.string.app_name)
    Text(text = title)
}
