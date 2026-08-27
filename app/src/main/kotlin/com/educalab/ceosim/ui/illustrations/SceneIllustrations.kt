package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.theme.CoinGold
import com.educalab.ceosim.ui.theme.ShopBlue
import com.educalab.ceosim.ui.theme.ShopBrown
import com.educalab.ceosim.ui.theme.ShopCream
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopGreenDark
import com.educalab.ceosim.ui.theme.ShopOrange
import com.educalab.ceosim.ui.theme.ShopOrangeDark
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.ShopWood
import com.educalab.ceosim.ui.theme.ShopYellow

/**
 * Ilustración principal de portada: la fachada de "Mi Pequeña Tienda".
 * Es el elemento visual fuerte que ancla la identidad de toda la app.
 */
@Composable
fun StoreFrontIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxWidth().height(220.dp)) {
        val w = size.width
        val h = size.height

        // Cielo
        drawRect(color = ShopBlue.copy(alpha = 0.18f), topLeft = Offset.Zero, size = Size(w, h * 0.55f))

        // Suelo
        drawRect(color = ShopWood.copy(alpha = 0.35f), topLeft = Offset(0f, h * 0.85f), size = Size(w, h * 0.15f))

        // Cuerpo de la tienda
        drawRoundRect(
            color = ShopCream,
            topLeft = Offset(w * 0.10f, h * 0.42f),
            size = Size(w * 0.80f, h * 0.46f),
            cornerRadius = CornerRadius(12f, 12f)
        )

        // Toldo a rayas
        val stripes = 7
        val stripeWidth = (w * 0.86f) / stripes
        for (i in 0 until stripes) {
            drawRect(
                color = if (i % 2 == 0) ShopRed else ShopCream,
                topLeft = Offset(w * 0.07f + i * stripeWidth, h * 0.30f),
                size = Size(stripeWidth, h * 0.14f)
            )
        }
        val awningEdge = Path().apply {
            moveTo(w * 0.07f, h * 0.44f)
            for (i in 0..stripes) {
                val x = w * 0.07f + i * stripeWidth
                lineTo(x, h * 0.44f)
                lineTo(x + stripeWidth / 2, h * 0.49f)
            }
        }
        drawPath(awningEdge, color = ShopOrangeDark)

        // Puerta
        drawRoundRect(
            color = ShopBrown,
            topLeft = Offset(w * 0.44f, h * 0.62f),
            size = Size(w * 0.14f, h * 0.26f),
            cornerRadius = CornerRadius(8f, 8f)
        )

        // Ventanas
        drawRoundRect(color = ShopBlue.copy(alpha = 0.55f), topLeft = Offset(w * 0.18f, h * 0.55f), size = Size(w * 0.16f, h * 0.16f), cornerRadius = CornerRadius(6f, 6f))
        drawRoundRect(color = ShopBlue.copy(alpha = 0.55f), topLeft = Offset(w * 0.66f, h * 0.55f), size = Size(w * 0.16f, h * 0.16f), cornerRadius = CornerRadius(6f, 6f))

        // Cartel con el nombre
        drawRoundRect(color = ShopGreen, topLeft = Offset(w * 0.30f, h * 0.18f), size = Size(w * 0.40f, h * 0.12f), cornerRadius = CornerRadius(10f, 10f))
        drawRoundRect(color = ShopGreenDark, topLeft = Offset(w * 0.30f, h * 0.18f), size = Size(w * 0.40f, h * 0.12f), cornerRadius = CornerRadius(10f, 10f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f))

        // Monedas decorativas flotando
        drawCircle(color = CoinGold, radius = w * 0.025f, center = Offset(w * 0.86f, h * 0.20f))
        drawCircle(color = CoinGold, radius = w * 0.02f, center = Offset(w * 0.10f, h * 0.16f))
        drawCircle(color = ShopYellow, radius = w * 0.015f, center = Offset(w * 0.92f, h * 0.30f))
    }
}

/** Fondo decorativo de madera para pantallas de estante/almacén. */
@Composable
fun ShelfBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxWidth().height(24.dp)) {
        drawRect(color = ShopWood, topLeft = Offset.Zero, size = size)
        drawRect(color = ShopBrown, topLeft = Offset(0f, size.height - 6f), size = Size(size.width, 6f))
    }
}
