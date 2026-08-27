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
import com.educalab.ceosim.domain.model.ProductCategory
import com.educalab.ceosim.ui.theme.InkDark
import com.educalab.ceosim.ui.theme.ShopBlue
import com.educalab.ceosim.ui.theme.ShopBrown
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopGreenDark
import com.educalab.ceosim.ui.theme.ShopOrange
import com.educalab.ceosim.ui.theme.ShopPurple
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.ShopWood
import com.educalab.ceosim.ui.theme.ShopYellow

/**
 * Ilustración de producto según su categoría. Todas comparten un mismo
 * estilo geométrico plano para mantener coherencia visual (sección 32).
 */
@Composable
fun ProductIllustration(category: ProductCategory, modifier: Modifier = Modifier, size: Dp = 56.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        when (category) {
            ProductCategory.BEBIDA -> {
                val cupPath = Path().apply {
                    moveTo(w * 0.30f, h * 0.25f)
                    lineTo(w * 0.70f, h * 0.25f)
                    lineTo(w * 0.62f, h * 0.85f)
                    lineTo(w * 0.38f, h * 0.85f)
                    close()
                }
                drawPath(cupPath, color = ShopBlue)
                drawRect(color = InkDark, topLeft = Offset(w * 0.28f, h * 0.20f), size = Size(w * 0.44f, h * 0.06f))
                drawLine(InkDark, Offset(w * 0.5f, h * 0.05f), Offset(w * 0.5f, h * 0.22f), strokeWidth = w * 0.04f)
            }
            ProductCategory.FRUTA -> {
                drawCircle(color = ShopRed, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.58f))
                drawLine(ShopGreenDark, Offset(w * 0.5f, h * 0.26f), Offset(w * 0.56f, h * 0.14f), strokeWidth = w * 0.04f)
                drawOval(color = ShopGreen, topLeft = Offset(w * 0.54f, h * 0.10f), size = Size(w * 0.20f, w * 0.12f))
            }
            ProductCategory.PAPELERIA -> {
                drawRoundRect(
                    color = ShopYellow,
                    topLeft = Offset(w * 0.24f, h * 0.16f),
                    size = Size(w * 0.52f, h * 0.68f),
                    cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
                )
                repeat(3) { i ->
                    drawLine(
                        InkDark,
                        Offset(w * 0.30f, h * (0.34f + i * 0.14f)),
                        Offset(w * 0.70f, h * (0.34f + i * 0.14f)),
                        strokeWidth = w * 0.015f
                    )
                }
            }
            ProductCategory.JUGUETE -> {
                drawCircle(color = ShopPurple, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f))
                drawArc(
                    color = ShopOrange,
                    startAngle = -20f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(w * 0.18f, h * 0.18f),
                    size = Size(w * 0.64f, h * 0.64f),
                    style = Stroke(width = w * 0.05f)
                )
            }
            ProductCategory.GALLETA -> {
                drawCircle(color = ShopWood, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f))
                val dots = listOf(0.38f to 0.40f, 0.60f to 0.42f, 0.46f to 0.58f, 0.58f to 0.62f, 0.40f to 0.60f)
                dots.forEach { (dx, dy) ->
                    drawCircle(color = InkDark, radius = w * 0.035f, center = Offset(w * dx, h * dy))
                }
            }
            ProductCategory.PLANTA -> {
                drawRoundRect(
                    color = ShopBrown,
                    topLeft = Offset(w * 0.34f, h * 0.62f),
                    size = Size(w * 0.32f, h * 0.26f),
                    cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
                )
                drawOval(color = ShopGreen, topLeft = Offset(w * 0.30f, h * 0.24f), size = Size(w * 0.20f, h * 0.34f))
                drawOval(color = ShopGreenDark, topLeft = Offset(w * 0.50f, h * 0.18f), size = Size(w * 0.20f, h * 0.36f))
            }
            ProductCategory.DEPORTE -> {
                drawCircle(color = ShopOrange, radius = w * 0.30f, center = Offset(w * 0.5f, h * 0.5f))
                drawArc(InkDark, 0f, 360f, false, Offset(w * 0.20f, h * 0.20f), Size(w * 0.60f, h * 0.60f), style = Stroke(width = w * 0.015f))
                drawLine(InkDark, Offset(w * 0.5f, h * 0.20f), Offset(w * 0.5f, h * 0.80f), strokeWidth = w * 0.015f)
                drawLine(InkDark, Offset(w * 0.20f, h * 0.5f), Offset(w * 0.80f, h * 0.5f), strokeWidth = w * 0.015f)
            }
            ProductCategory.LIBRO -> {
                drawRoundRect(
                    color = ShopBlue,
                    topLeft = Offset(w * 0.20f, h * 0.20f),
                    size = Size(w * 0.60f, h * 0.62f),
                    cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
                )
                drawLine(ShopYellow, Offset(w * 0.5f, h * 0.20f), Offset(w * 0.5f, h * 0.82f), strokeWidth = w * 0.03f)
            }
        }
    }
}
