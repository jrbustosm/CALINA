package co.edu.ut.jrbustosm.calina.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import java.text.DecimalFormat

@Composable
fun GetCashUI(appViewModel: AppViewModel) {

    val symbol = if(appViewModel.appUIState.currentGroup==null) "$"
    else appViewModel.appUIState.currentGroup!!.cash_symbol.toString()

    val f = DecimalFormat("#,##0")
    val cash = if(appViewModel.appUIState.currentGroup==null) "0"
    else f.format(appViewModel.appUIState.currentGroup!!.cash)

    Text(
        text =
            if(appViewModel.appUIState.isTeacher())
                stringResource(R.string.youhave_teacherrol)
            else
                stringResource(R.string.you_have) + " " + symbol + cash + " " + stringResource(R.string.in_cash),
        color = MaterialTheme.colors.onSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(vertical = 10.dp),
        textAlign = TextAlign.Center
    )
}