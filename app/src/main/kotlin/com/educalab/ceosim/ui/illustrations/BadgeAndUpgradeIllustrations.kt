package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.domain.model.UpgradeCategory
import com.educalab.ceosim.ui.theme.CoinGold
import com.educalab.ceosim.ui.theme.CoinGoldDark
import com.educalab.ceosim.ui.theme.InkMedium
import com.educalab.ceosim.ui.theme.ShopBrown
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.ShopWood
import com.educalab.ceosim.ui.theme.ShopYellow
import kotlin.math.cos
import kotlin.math.sin

/** Insignia coleccionable. Gris/apagada cuando está bloqueada, dorada cuando se desbloquea. */
@Composable
fun BadgeMedallion(unlocked: Boolean, modifier: Modifier = Modifier, size: Dp = 72.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w * 0.5f, h * 0.42f)
        val outerColor = if (unlocked) CoinGoldDark else InkMedium.copy(alpha = 0.35f)
        val innerColor = if (unlocked) CoinGold else InkMedium.copy(alpha = 0.20f)

        val ribbonL = Path().apply {
            moveTo(w * 0.38f, h * 0.55f)
            lineTo(w * 0.28f, h * 0.95f)
            lineTo(w * 0.44f, h * 0.80f)
            close()
        }
        val ribbonR = Path().apply {
            moveTo(w * 0.62f, h * 0.55f)
            lineTo(w * 0.72f, h * 0.95f)
            lineTo(w * 0.56f, h * 0.80f)
            close()
        }
        drawPath(ribbonL, color = if (unlocked) ShopRed else InkMedium.copy(alpha = 0.25f))
        drawPath(ribbonR, color = if (unlocked) ShopRed else InkMedium.copy(alpha = 0.25f))

        val outerR = w * 0.30f
        val innerR = w * 0.13f
        val star = Path()
        for (i in 0 until 10) {
            val angle = Math.toRadians((-90 + i * 36).toDouble())
            val r = if (i % 2 == 0) outerR else innerR
            val x = center.x + (r * cos(angle)).toFloat()
            val y = center.y + (r * sin(angle)).toFloat()
            if (i == 0) star.moveTo(x, y) else star.lineTo(x, y)
        }
        star.close()
        drawCircle(color = outerColor, radius = outerR * 1.15f, center = center)
        drawPath(star, color = innerColor)
        drawPath(star, color = outerColor, style = Stroke(width = w * 0.015f))
    }
}

/** Ilustración de una mejora de tienda, según su categoría. */
@Composable
fun UpgradeIllustration(category: UpgradeCategory, modifier: Modifier = Modifier, size: Dp = 56.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        when (category) {
            UpgradeCategory.ESTANTE -> {
                drawRoundRect(ShopWood, Offset(w * 0.15f, h * 0.20f), Size(w * 0.70f, h * 0.10f), CornerRadius(w * 0.02f, w * 0.02f))
                drawRoundRect(ShopWood, Offset(w * 0.15f, h * 0.50f), Size(w * 0.70f, h * 0.10f), CornerRadius(w * 0.02f, w * 0.02f))
                drawRoundRect(ShopWood, Offset(w * 0.15f, h * 0.80f), Size(w * 0.70f, h * 0.10f), CornerRadius(w * 0.02f, w * 0.02f))
                drawRect(ShopBrown, Offset(w * 0.18f, h * 0.20f), Size(w * 0.05f, h * 0.70f))
                drawRect(ShopBrown, Offset(w * 0.77f, h * 0.20f), Size(w * 0.05f, h * 0.70f))
            }
            UpgradeCategory.DECORACION -> {
                drawRoundRect(ShopBrown, Offset(w * 0.36f, h * 0.62f), Size(w * 0.28f, h * 0.24f), CornerRadius(w * 0.03f, w * 0.03f))
                drawOval(ShopGreen, Offset(w * 0.28f, h * 0.20f), Size(w * 0.20f, h * 0.36f))
                drawOval(ShopGreen, Offset(w * 0.48f, h * 0.14f), Size(w * 0.22f, h * 0.40f))
            }
            UpgradeCategory.MOSTRADOR -> {
                drawRoundRect(ShopWood, Offset(w * 0.12f, h * 0.45f), Size(w * 0.76f, h * 0.40f), CornerRadius(w * 0.04f, w * 0.04f))
                drawRect(ShopBrown, Offset(w * 0.12f, h * 0.45f), Size(w * 0.76f, h * 0.08f))
            }
            UpgradeCategory.ILUMINACION -> {
                drawCircle(color = ShopYellow, radius = w * 0.24f, center = Offset(w * 0.5f, h * 0.36f))
                for (i in 0 until 8) {
                    val angle = Math.toRadians((i * 45).toDouble())
                    val startR = w * 0.28f
                    val endR = w * 0.38f
                    val cx = w * 0.5f
                    val cy = h * 0.36f
                    drawLine(
                        ShopYellow,
                        Offset(cx + (startR * cos(angle)).toFloat(), cy + (startR * sin(angle)).toFloat()),
                        Offset(cx + (endR * cos(angle)).toFloat(), cy + (endR * sin(angle)).toFloat()),
                        strokeWidth = w * 0.02f
                    )
                }
                drawRoundRect(ShopBrown, Offset(w * 0.42f, h * 0.66f), Size(w * 0.16f, h * 0.28f), CornerRadius(w * 0.02f, w * 0.02f))
            }
            UpgradeCategory.CARTEL -> {
                drawRoundRect(ShopRed, Offset(w * 0.14f, h * 0.24f), Size(w * 0.72f, h * 0.40f), CornerRadius(w * 0.04f, w * 0.04f))
                drawRect(ShopBrown, Offset(w * 0.46f, h * 0.64f), Size(w * 0.08f, h * 0.28f))
            }
        }
    }
}
