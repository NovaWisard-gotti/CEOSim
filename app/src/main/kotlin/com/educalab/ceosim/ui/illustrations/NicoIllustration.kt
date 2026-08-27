package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.theme.InkDark
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopGreenDark
import com.educalab.ceosim.ui.theme.ShopCream
import com.educalab.ceosim.ui.theme.ShopOrange
import com.educalab.ceosim.ui.theme.ShopOrangeDark

/** Estados de ánimo de Nico, usados para reforzar el feedback (sección 10). */
enum class NicoExpression { NEUTRAL, FELIZ, EMOCIONADO, PENSANDO }

/**
 * Nico: el zorrito tendero que guía al niño por la tienda.
 * Personaje 100% vectorial, dibujado con Canvas, sin depender de imágenes
 * externas ni de conexión a internet.
 */
@Composable
fun NicoCharacter(
    modifier: Modifier = Modifier,
    expression: NicoExpression = NicoExpression.NEUTRAL,
    size: Dp = 96.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Orejas
        val earPath = Path().apply {
            moveTo(w * 0.22f, h * 0.20f)
            lineTo(w * 0.12f, h * 0.02f)
            lineTo(w * 0.34f, h * 0.14f)
            close()
        }
        val earPath2 = Path().apply {
            moveTo(w * 0.78f, h * 0.20f)
            lineTo(w * 0.88f, h * 0.02f)
            lineTo(w * 0.66f, h * 0.14f)
            close()
        }
        drawPath(earPath, color = ShopOrangeDark)
        drawPath(earPath2, color = ShopOrangeDark)

        // Cabeza
        drawOval(color = ShopOrange, topLeft = Offset(w * 0.10f, h * 0.10f), size = Size(w * 0.80f, h * 0.62f))

        // Hocico
        drawOval(color = ShopCream, topLeft = Offset(w * 0.30f, h * 0.36f), size = Size(w * 0.40f, h * 0.32f))

        // Nariz
        drawOval(color = InkDark, topLeft = Offset(w * 0.45f, h * 0.44f), size = Size(w * 0.10f, h * 0.08f))

        // Ojos (varían un poco según la expresión)
        val eyeY = h * 0.30f
        val eyeRadius = if (expression == NicoExpression.EMOCIONADO) w * 0.055f else w * 0.045f
        drawCircle(color = InkDark, radius = eyeRadius, center = Offset(w * 0.36f, eyeY))
        drawCircle(color = InkDark, radius = eyeRadius, center = Offset(w * 0.64f, eyeY))

        // Boca según expresión
        val mouthPath = Path()
        when (expression) {
            NicoExpression.FELIZ, NicoExpression.EMOCIONADO -> {
                mouthPath.moveTo(w * 0.40f, h * 0.55f)
                mouthPath.quadraticBezierTo(w * 0.50f, h * 0.63f, w * 0.60f, h * 0.55f)
            }
            NicoExpression.PENSANDO -> {
                mouthPath.moveTo(w * 0.42f, h * 0.57f)
                mouthPath.lineTo(w * 0.58f, h * 0.57f)
            }
            NicoExpression.NEUTRAL -> {
                mouthPath.moveTo(w * 0.42f, h * 0.56f)
                mouthPath.quadraticBezierTo(w * 0.50f, h * 0.59f, w * 0.58f, h * 0.56f)
            }
        }
        drawPath(mouthPath, color = InkDark, style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.02f))

        // Cuerpo
        drawRoundRect(
            color = ShopOrange,
            topLeft = Offset(w * 0.18f, h * 0.60f),
            size = Size(w * 0.64f, h * 0.36f),
            cornerRadius = CornerRadius(w * 0.18f, w * 0.18f)
        )

        // Delantal de tendero
        drawRoundRect(
            color = ShopGreen,
            topLeft = Offset(w * 0.28f, h * 0.66f),
            size = Size(w * 0.44f, h * 0.30f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f),
            style = Fill
        )
        drawRoundRect(
            color = ShopGreenDark,
            topLeft = Offset(w * 0.28f, h * 0.66f),
            size = Size(w * 0.44f, h * 0.30f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.015f)
        )
        // Bolsillo del delantal
        drawRoundRect(
            color = ShopGreenDark,
            topLeft = Offset(w * 0.40f, h * 0.82f),
            size = Size(w * 0.20f, h * 0.10f),
            cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
        )
    }
}
