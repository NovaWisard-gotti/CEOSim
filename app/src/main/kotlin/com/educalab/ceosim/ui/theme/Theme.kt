package com.educalab.ceosim.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = ShopOrange,
    onPrimary = Color.White,
    primaryContainer = ShopOrangeLight,
    onPrimaryContainer = InkDark,
    secondary = ShopGreen,
    onSecondary = Color.White,
    secondaryContainer = ShopGreenLight,
    onSecondaryContainer = InkDark,
    tertiary = ShopBlue,
    background = ShopCream,
    onBackground = InkDark,
    surface = SurfaceCard,
    onSurface = InkDark,
    surfaceVariant = SurfaceShelf,
    onSurfaceVariant = InkMedium,
    error = ShopRed,
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = ShopOrange,
    onPrimary = InkDark,
    primaryContainer = ShopOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = ShopGreen,
    onSecondary = InkDark,
    background = Color(0xFF221A12),
    onBackground = Color(0xFFF3E7D8),
    surface = Color(0xFF2E2419),
    onSurface = Color(0xFFF3E7D8),
    surfaceVariant = Color(0xFF3A2E20),
    onSurfaceVariant = Color(0xFFD8C6AE)
)

/**
 * CEOSim usa siempre la paleta cálida de "Mi Pequeña Tienda". Se respeta el
 * modo oscuro del sistema para comodidad visual, pero la identidad de marca
 * (naranjas, verdes, madera) se mantiene en ambos modos.
 */
@Composable
fun CeoSimTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = CeoSimTypography,
        content = content
    )
}
