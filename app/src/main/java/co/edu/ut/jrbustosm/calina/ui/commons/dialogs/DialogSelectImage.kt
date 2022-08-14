package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.edu.ut.jrbustosm.calina.data.getResource
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun DialogSelectImage(imageSel: String, close: ()->Unit, select: (image: String)->Unit){
    Dialog(onDismissRequest = close) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.background.copy(alpha = 0.9f))
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(20.dp)
        ) {
            val stateLazy = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LazyVerticalGrid(
                //cells = GridCells.Adaptive(60.dp),
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(5.dp),
                state = stateLazy
            ) {
                repeat(35){
                    item() {
                        Image(
                            painter = painterResource(
                                id = LocalContext.current.getResource("symbol${it}")
                            ),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(60.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.secondary.copy(
                                        alpha = if ("symbol${it}" == imageSel) 1f else 0f
                                    )
                                )
                                .clickable {
                                    select("symbol${it}")
                                    close()
                                }
                        )
                    }
                    if ("symbol${it}" == imageSel){
                        coroutineScope.launch {
                            stateLazy.scrollToItem(it/4)
                        }
                    }
                }
                repeat(404){
                    item {
                        Image(
                            painter = painterResource(
                                id = LocalContext.current.getResource("item${it+1}")
                            ),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(60.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.secondary.copy(
                                        alpha = if ("item${it + 1}" == imageSel) 1f else 0f
                                    )
                                )
                                .clickable {
                                    select("item${it + 1}")
                                    close()
                                }
                        )
                    }
                    if ("item${it + 1}" == imageSel){
                        coroutineScope.launch {
                            stateLazy.scrollToItem((it+34)/4)
                        }
                    }
                }
            }
        }
    }
}