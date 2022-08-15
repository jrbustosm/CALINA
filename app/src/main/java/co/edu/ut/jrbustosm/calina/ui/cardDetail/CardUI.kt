package co.edu.ut.jrbustosm.calina.ui.cardDetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.getResource
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.text.DecimalFormat

@Composable
fun GetCardUI(cardUIState: CardUIState, appViewModel: AppViewModel, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(
                vertical = 10.dp,
                horizontal = 20.dp
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp)
            .fillMaxWidth(),
    ){
        GetCardInfo(cardUIState)
        Spacer(modifier = Modifier.height(5.dp))
        GetCardDraw(cardUIState, appViewModel)
        Spacer(modifier = Modifier.height(5.dp))
        GetCardDescription(cardUIState)
    }
}

@Composable
private fun GetCardDescription(cardUIState: CardUIState) {
    Card (
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
    ){
        Image(
            painter = when(cardUIState.type) {
                    TypeCardCALINA.ACTION-> painterResource(id = R.drawable.marbel)
                    TypeCardCALINA.REWARD -> painterResource(id = R.drawable.azul)
                    TypeCardCALINA.INFORMATION -> painterResource(id = R.drawable.verde)
                    else -> painterResource(id = R.drawable.morado)
                },
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.FillWidth
        )
        var textDescription = cardUIState.description
        var textColor = MaterialTheme.colors.onSurface
        if(cardUIState.state == StateCardCALINA.BUY &&
            cardUIState.type == TypeCardCALINA.INFORMATION) {
            textDescription = stringResource(R.string.you_cantthisinfo)
            textColor = MaterialTheme.colors.primary
        }


        Text(
            text = textDescription,
            color = textColor,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(CutCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CutCornerShape(4.dp)
                )
                .padding(10.dp)
                .verticalScroll(rememberScrollState(0)),
        )
    }
}

@Composable
private fun GetCardDraw(cardUIState: CardUIState, appViewModel: AppViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(5.dp, MaterialTheme.colors.surface)
            .fillMaxWidth()
            .height(170.dp)
            .padding(5.dp),
    ){
        Image(
            painter = painterResource(
                id = LocalContext.current.getResource(cardUIState.imageResource)
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.Black),
            colorFilter = if(cardUIState.isSymbol) {
                ColorFilter.tint(MaterialTheme.colors.onError)
            } else {
                null
            }
        )
        if(cardUIState.state == StateCardCALINA.BUY ||
            cardUIState.state == StateCardCALINA.USED) {
            val text =
                if (cardUIState.state == StateCardCALINA.BUY) {
                    val currentGroup = appViewModel.appUIState.currentGroup!!
                    "${stringResource(R.string.buyfor)} ${currentGroup.cash_symbol}${cardUIState.cash}"
                }else
                    "${stringResource(R.string.used_by)} ${cardUIState.level}"
            Text(
                text = text,
                color = MaterialTheme.colors.secondary.copy(alpha = 1f),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .rotate(-10f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onPrimary.copy(alpha = 0.8f))
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GetCardInfo(cardUIState: CardUIState) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column (
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val f = DecimalFormat("#0000")
                GetTextInfo(f.format(cardUIState.number), 1f)
                Spacer(modifier = Modifier.width(5.dp))
                GetTextInfo(
                    text = "${cardUIState.type}-${cardUIState.difficulty}",
                    weight = 1f
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                GetTextInfo(text = cardUIState.title, 1f)
            }
        }
    }
}


@Composable
private fun RowScope.GetTextInfo(text: String, weight: Float) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onSurface,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .weight(weight = weight)
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.7f),
                shape = CutCornerShape(4.dp)
            )
            .padding(vertical = 5.dp)
    )
}
