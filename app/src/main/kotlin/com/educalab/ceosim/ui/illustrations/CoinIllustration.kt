package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.theme.CoinGold
import com.educalab.ceosim.ui.theme.CoinGoldDark

/**
 * Moneda ficticia de CEOSim. Se usa en todo el juego (saldo, precios, costos)
 * en lugar de cualquier símbolo de dinero real.
 */
@Composable
fun CoinIcon(modifier: Modifier = Modifier, size: Dp = 24.dp) {
    Canvas(modifier = modifier.size(size)) {
        val radius = this.size.minDimension / 2f
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        drawCircle(color = CoinGoldDark, radius = radius, center = center)
        drawCircle(color = CoinGold, radius = radius * 0.86f, center = center)
        drawCircle(
            color = CoinGoldDark,
            radius = radius * 0.86f,
            center = center,
            style = Stroke(width = radius * 0.12f)
        )
        drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = CoinGoldDark.toArgbCompat()
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = radius * 1.05f
                isFakeBoldText = true
                isAntiAlias = true
            }
            val textY = center.y - (paint.descent() + paint.ascent()) / 2f
            drawText("m", center.x, textY, paint)
        }
    }
}

internal fun Color.toArgbCompat(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}
