package co.edu.ut.jrbustosm.calina.ui.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.getResource
import co.edu.ut.jrbustosm.calina.domain.cardcreator.GroupDialogCreate
import co.edu.ut.jrbustosm.calina.domain.triggers.EventType
import co.edu.ut.jrbustosm.calina.ui.commons.dialogs.DialogCreateCardUseCase
import co.edu.ut.jrbustosm.calina.ui.setAppLocale
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalFoundationApi
@Composable
fun GetMainDrawerUI(
    appViewModel: AppViewModel,
    close: ()->Unit
) {

    var showCreateCard by remember { mutableStateOf(false) }
    if(showCreateCard) DialogCreateCardUseCase(
        appViewModel, useCase = GroupDialogCreate()
    ) { showCreateCard = false }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            verticalAlignment =  Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Row (horizontalArrangement = Arrangement.End){
                Image(
                    painter = painterResource(R.drawable.icon_calina),
                    contentDescription = "App icon",
                    Modifier
                        .height(60.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row (horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = painterResource(R.drawable.calina),
                    contentDescription = "App Name",
                    Modifier
                        .height(30.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        val scrollState = rememberScrollState()
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.select_group),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(5.dp))
            Divider(
                color = MaterialTheme.colors.onPrimary,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(15.dp))

            val colorUnselect = Color(1f, 1f, 1f, 0.01f)
            val colorSelect = MaterialTheme.colors.secondary.copy(alpha = 0.9f)

            for(cardUIState in appViewModel.byType(TypeCardCALINA.GROUP)) {
                if(cardUIState.isSecondary) continue
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (cardUIState.isSelect) colorSelect
                            else colorUnselect
                        )
                        .padding(vertical = 5.dp)
                        .clickable {
                            appViewModel.updateGroupSelect(cardUIState)
                            cardUIState.execTriggers(EventType.OnSelect, appViewModel)
                            close()
                        }
                ){
                    Image(
                        painter = painterResource(
                            id = LocalContext.current.getResource(cardUIState.imageResource)
                        ),
                        contentDescription = "",
                        modifier = Modifier.size(35.dp),
                        colorFilter =
                            if(cardUIState.isSymbol) ColorFilter.tint(MaterialTheme.colors.onPrimary)
                            else null
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = cardUIState.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.height(5.dp))
            Divider(
                color = MaterialTheme.colors.onPrimary,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = stringResource(R.string.imteacher_creategroup),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        close()
                        showCreateCard = true
                    }
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .background(colorUnselect)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(30.dp))
            var showLanguage by remember { mutableStateOf(false) }
            Box{
                Text(
                    text = "${stringResource(R.string.changeLanguage)}: ${appViewModel.appUIState.language}",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clickable {
                            showLanguage = true
                        }
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .background(colorUnselect)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                DropdownMenu(
                    expanded = showLanguage,
                    onDismissRequest = { showLanguage = false },
                    modifier = Modifier
                        .background(MaterialTheme.colors.onBackground),
                ) {
                    val items = listOf("es", "en")
                    items.forEach { text ->
                        DropdownMenuItem(onClick = {
                            coroutineScope.launch {
                                context.setAppLocale(text, appViewModel)
                            }
                            showLanguage = false
                        }) {
                            Text(
                                text = text,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.about_calina),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {

                    }
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .background(colorUnselect)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
        }
    }
}