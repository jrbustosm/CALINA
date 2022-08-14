package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogButtons(
    select: (Char)->Unit,
    title: String,
    l: List<Pair<Char, Int>>,
    close: ()->Unit,

    ) {
    Dialog(onDismissRequest = {  close() } ) {
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
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(thickness = 2.dp, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.height(20.dp))
            for(p in l){
                Button(onClick = {
                    select(p.first)
                },modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = p.second),
                        style = MaterialTheme.typography.h6
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
