package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.asDate
import co.edu.ut.jrbustosm.calina.data.asLocalDate
import co.edu.ut.jrbustosm.calina.domain.cardcreator.CardDialogCreate
import co.edu.ut.jrbustosm.calina.domain.cardcreator.DialogFragment
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DialogAdvanceOptions(
        close: ()->Unit,
        changeDateExpired: (dateExpired: Date?)->Unit,
        dateExpired: Date?,
        changeTrigger: (trigger: String)->Unit,
        trigger: String,
        changeURL: (url: String)->Unit,
        url: String,
        myColors: TextFieldColors,
        switchColors: SwitchColors,
        useCase: CardDialogCreate
    ){

    var localTrigger by remember { mutableStateOf(trigger) }
    var localDateExpired by remember { mutableStateOf<Date?>(dateExpired) }
    var localURL by remember { mutableStateOf(url)}

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val period = Period.of(0, 0, 7)
    var expired by remember { mutableStateOf(
        dateExpired?.asLocalDate() ?: LocalDate.now().plus(period))
    }

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
            if(!useCase(DialogFragment.GROUP)) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = stringResource(R.string.dateexpirednever),
                        color = if (localDateExpired == null) MaterialTheme.colors.onPrimary
                        else MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Switch(
                        checked = localDateExpired == null,
                        onCheckedChange = {
                            localDateExpired = if (it) null else expired.asDate()
                            changeDateExpired(localDateExpired)
                        },
                        colors = switchColors
                    )
                    val mDatePickerDialog = DatePickerDialog(
                        LocalContext.current,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            expired = LocalDate.of(year, month+1, dayOfMonth)
                            localDateExpired = expired.asDate()
                            changeDateExpired(localDateExpired)
                        },
                        expired.year, expired.monthValue-1, expired.dayOfMonth
                    )
                    if (localDateExpired != null) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = expired.format(formatter),
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .clickable {
                                    mDatePickerDialog.show()
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
            OutlinedTextField(
                value = localURL,
                onValueChange = {
                    localURL = it
                    changeURL(localURL)
                },
                singleLine = true,
                label = { Text(text = stringResource(R.string.url)) },
                placeholder = { Text(text = stringResource(R.string.url_extrainfo)) },
                colors = myColors,
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = localTrigger,
                onValueChange = {
                    localTrigger = it
                    changeTrigger(localTrigger)
                },
                singleLine = false,
                label = { Text(text = stringResource(R.string.trigger)) },
                placeholder = { Text(text = stringResource(R.string.triggerofcard)) },
                colors = myColors,
                modifier = Modifier.height(200.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = close) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}