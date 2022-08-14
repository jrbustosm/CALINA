package co.edu.ut.jrbustosm.calina.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.getResource
import co.edu.ut.jrbustosm.calina.ui.theme.actionCard
import co.edu.ut.jrbustosm.calina.ui.theme.groupCard
import co.edu.ut.jrbustosm.calina.ui.theme.informationCard
import co.edu.ut.jrbustosm.calina.ui.theme.rewardCard
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.text.DecimalFormat

@Composable
fun GetMiniCardUI(
    cardUIState: CardUIState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() },
        backgroundColor = when(cardUIState.type){
            TypeCardCALINA.ACTION -> MaterialTheme.colors.actionCard
            TypeCardCALINA.GROUP -> MaterialTheme.colors.groupCard
            TypeCardCALINA.REWARD -> MaterialTheme.colors.rewardCard
            else -> MaterialTheme.colors.informationCard
        }
    ) {
        Column(
            modifier = modifier
                .padding(5.dp)
        ){
            val f = DecimalFormat("#0000")
            Text(
                text = "${f.format(cardUIState.number)} ${cardUIState.type}-${cardUIState.difficulty}",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CutCornerShape(2.dp)
                    )
                    .padding(vertical = 1.dp)
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ){
                Image(
                    painter = painterResource(
                        id = LocalContext.current.getResource(cardUIState.imageResource))
                    ,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .background(Color(1f, 1f, 1f, 0.6f))
                        .fillMaxSize()
                        .border(1.dp, Color.Black)
                )
                if(cardUIState.state == StateCardCALINA.BUY ||
                    cardUIState.state == StateCardCALINA.USED) {
                    val textId = if (cardUIState.state == StateCardCALINA.BUY)
                        R.string.buy else R.string.used
                    Text(
                        text = stringResource(id = textId),
                        color = MaterialTheme.colors.secondary.copy(alpha = 1f),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .rotate(-45f)
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}