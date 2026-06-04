package ni.edu.uam.inventarioacademico.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF90CAF9),
    onPrimary = Color.White,

    secondary = Color(0xFF64B5F6),
    onSecondary = Color.White,

    tertiary = Color(0xFFE3F2FD),

    background = Color(0xFFF5FAFF),
    onBackground = Color(0xFF1E3A5F),

    surface = Color.White,
    onSurface = Color(0xFF1E3A5F)
)

private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFF64B5F6),
    secondary = Color(0xFF90CAF9),

    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B)
)

@Composable
fun InventarioAcademicoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme)
            DarkColorScheme
        else
            LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}