package co.edu.ut.jrbustosm.calina.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Winecoofee,           //Top Bar and Lateral Menu  Background
    onPrimary= Gold,                //Top Bar Text Color
    primaryVariant = Winecoofee,    //
    onBackground = SecondaryColor,  //Light Color back
    background = BackgroundColor,   //Dark Color back
    onSecondary = Gold,             //Highlight Info Text Color
    secondary = Infocolor.copy(alpha = 0.5f),          //Highlight Info Color Background
    onSurface = Color.Black,        //Card Text Color
    surface = Color(0.8f,0.8f,0.8f),       //Card Background Color
    onError = Gold,       //Action Button Text Color
    error = Color(0.8f,0.8f,0.8f, alpha = 0.4f),  //Action Button Background Color
)

private val LightColorPalette = lightColors(
    primary = Winecoofee,           //Top Bar and Lateral Menu  Background
    onPrimary= Gold,                //Top Bar Text Color
    primaryVariant = Winecoofee,    //
    onBackground = SecondaryColor,  //Light Color back
    background = BackgroundColor,   //Dark Color back
    onSecondary = Gold,             //Highlight Info Text Color
    secondary = Infocolor.copy(alpha = 0.5f),          //Highlight Info Color Background
    onSurface = Color.Black,        //Card Text Color
    surface = Color(0.8f,0.8f,0.8f),       //Card Background Color
    onError = Gold,       //Action Button Text Color
    error = Color(0.8f,0.8f,0.8f, alpha = 0.4f),  //Action Button Background Color
)

val Colors.actionCard: Color
    @Composable
    get() = if (isLight) ActionCardColor else ActionCardColor

val Colors.informationCard: Color
    @Composable
    get() = if (isLight) InformationCardColor else InformationCardColor

val Colors.groupCard: Color
    @Composable
    get() = if (isLight) GroupCardColor else GroupCardColor

val Colors.rewardCard: Color
    @Composable
    get() = if (isLight) RewardCardColor else RewardCardColor

@Composable
fun CALINATheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}