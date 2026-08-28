package com.educalab.ceosim.ui.theme

import androidx.compose.material3.MaterialTheme
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

/**
 * CEOSim usa siempre la paleta cálida de "Mi Pequeña Tienda", sin importar
 * si el celular está en modo oscuro o claro: la identidad de marca (naranjas,
 * verdes, madera) debe verse igual en todos los dispositivos.
 */
@Composable
fun CeoSimTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = CeoSimTypography,
        content = content
    )
}
