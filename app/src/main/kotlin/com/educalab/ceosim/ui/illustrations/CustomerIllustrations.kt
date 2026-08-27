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
import com.educalab.ceosim.domain.model.CustomerAvatar
import com.educalab.ceosim.ui.theme.InkDark
import com.educalab.ceosim.ui.theme.ShopBlue
import com.educalab.ceosim.ui.theme.ShopBrown
import com.educalab.ceosim.ui.theme.ShopBrownLight
import com.educalab.ceosim.ui.theme.ShopCream
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopOrange
import com.educalab.ceosim.ui.theme.ShopPurple
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.ShopYellow

/**
 * Avatar de un cliente ficticio del Mostrador. Lógica sencilla y predecible
 * (sección "CLIENTES"): cada avatar es una cabeza con un accesorio distintivo.
 */
@Composable
fun CustomerIllustration(avatar: CustomerAvatar, modifier: Modifier = Modifier, size: Dp = 64.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val skin = ShopBrownLight
        val faceCenter = Offset(w * 0.5f, h * 0.55f)
        val faceRadius = w * 0.30f

        fun drawFace() {
            drawCircle(color = skin, radius = faceRadius, center = faceCenter)
            drawCircle(color = InkDark, radius = w * 0.03f, center = Offset(faceCenter.x - w * 0.10f, faceCenter.y - h * 0.02f))
            drawCircle(color = InkDark, radius = w * 0.03f, center = Offset(faceCenter.x + w * 0.10f, faceCenter.y - h * 0.02f))
            val mouth = Path().apply {
                moveTo(faceCenter.x - w * 0.10f, faceCenter.y + h * 0.10f)
                quadraticBezierTo(faceCenter.x, faceCenter.y + h * 0.16f, faceCenter.x + w * 0.10f, faceCenter.y + h * 0.10f)
            }
            drawPath(mouth, color = InkDark, style = Stroke(width = w * 0.02f))
        }

        when (avatar) {
            CustomerAvatar.NINA_TRENZAS -> {
                drawFace()
                drawCircle(ShopOrange, w * 0.09f, Offset(faceCenter.x - faceRadius, faceCenter.y + h * 0.05f))
                drawCircle(ShopOrange, w * 0.09f, Offset(faceCenter.x + faceRadius, faceCenter.y + h * 0.05f))
                drawArc(ShopBrown, 180f, 180f, false, Offset(faceCenter.x - faceRadius, faceCenter.y - faceRadius), Size(faceRadius * 2, faceRadius * 2), style = Stroke(width = w * 0.10f))
            }
            CustomerAvatar.NINO_LENTES -> {
                drawFace()
                drawCircle(color = InkDark, radius = w * 0.09f, center = Offset(faceCenter.x - w * 0.10f, faceCenter.y - h * 0.02f), style = Stroke(width = w * 0.02f))
                drawCircle(color = InkDark, radius = w * 0.09f, center = Offset(faceCenter.x + w * 0.10f, faceCenter.y - h * 0.02f), style = Stroke(width = w * 0.02f))
                drawLine(InkDark, Offset(faceCenter.x - w * 0.02f, faceCenter.y - h * 0.02f), Offset(faceCenter.x + w * 0.02f, faceCenter.y - h * 0.02f), strokeWidth = w * 0.02f)
            }
            CustomerAvatar.ABUELA_BUFANDA -> {
                drawFace()
                drawRoundRect(ShopPurple, Offset(faceCenter.x - faceRadius * 1.1f, faceCenter.y + faceRadius * 0.5f), Size(faceRadius * 2.2f, h * 0.14f), CornerRadius(w * 0.05f, w * 0.05f))
                drawArc(ShopCream, 180f, 180f, false, Offset(faceCenter.x - faceRadius * 0.9f, faceCenter.y - faceRadius * 1.1f), Size(faceRadius * 1.8f, faceRadius * 1.8f), style = Stroke(width = w * 0.08f))
            }
            CustomerAvatar.ABUELO_SOMBRERO -> {
                drawFace()
                drawRoundRect(ShopBrown, Offset(faceCenter.x - faceRadius, faceCenter.y - faceRadius * 1.5f), Size(faceRadius * 2, h * 0.08f), CornerRadius(w * 0.02f, w * 0.02f))
                drawRoundRect(ShopBrown, Offset(faceCenter.x - faceRadius * 0.6f, faceCenter.y - faceRadius * 1.85f), Size(faceRadius * 1.2f, h * 0.16f), CornerRadius(w * 0.03f, w * 0.03f))
            }
            CustomerAvatar.NINA_GORRA -> {
                drawFace()
                drawArc(ShopRed, 180f, 180f, false, Offset(faceCenter.x - faceRadius, faceCenter.y - faceRadius * 1.15f), Size(faceRadius * 2, faceRadius * 1.6f), style = Stroke(width = w * 0.09f))
                drawOval(ShopRed, Offset(faceCenter.x, faceCenter.y - faceRadius * 1.05f), Size(faceRadius * 0.9f, h * 0.07f))
            }
            CustomerAvatar.NINO_CHALECO -> {
                drawFace()
                drawRoundRect(ShopGreen, Offset(faceCenter.x - faceRadius * 0.9f, faceCenter.y + faceRadius * 0.6f), Size(faceRadius * 1.8f, h * 0.16f), CornerRadius(w * 0.03f, w * 0.03f))
            }
            CustomerAvatar.ROBOT_AMIGABLE -> {
                drawRoundRect(ShopBlue, Offset(faceCenter.x - faceRadius, faceCenter.y - faceRadius), Size(faceRadius * 2, faceRadius * 2), CornerRadius(w * 0.06f, w * 0.06f))
                drawRect(color = ShopYellow, topLeft = Offset(faceCenter.x - w * 0.14f, faceCenter.y - h * 0.05f), size = Size(w * 0.10f, w * 0.10f))
                drawRect(color = ShopYellow, topLeft = Offset(faceCenter.x + w * 0.04f, faceCenter.y - h * 0.05f), size = Size(w * 0.10f, w * 0.10f))
                drawLine(InkDark, Offset(faceCenter.x, faceCenter.y - faceRadius), Offset(faceCenter.x, faceCenter.y - faceRadius * 1.3f), strokeWidth = w * 0.02f)
                drawCircle(ShopRed, w * 0.03f, Offset(faceCenter.x, faceCenter.y - faceRadius * 1.3f))
            }
            CustomerAvatar.GATO_CLIENTE -> {
                drawFace()
                val earL = Path().apply {
                    moveTo(faceCenter.x - faceRadius * 0.8f, faceCenter.y - faceRadius * 0.6f)
                    lineTo(faceCenter.x - faceRadius * 1.3f, faceCenter.y - faceRadius * 1.4f)
                    lineTo(faceCenter.x - faceRadius * 0.2f, faceCenter.y - faceRadius * 0.9f)
                    close()
                }
                val earR = Path().apply {
                    moveTo(faceCenter.x + faceRadius * 0.8f, faceCenter.y - faceRadius * 0.6f)
                    lineTo(faceCenter.x + faceRadius * 1.3f, faceCenter.y - faceRadius * 1.4f)
                    lineTo(faceCenter.x + faceRadius * 0.2f, faceCenter.y - faceRadius * 0.9f)
                    close()
                }
                drawPath(earL, color = skin)
                drawPath(earR, color = skin)
            }
            CustomerAvatar.NINA_PATINETA -> {
                drawFace()
                drawArc(ShopOrange, 180f, 180f, false, Offset(faceCenter.x - faceRadius, faceCenter.y - faceRadius * 1.15f), Size(faceRadius * 2, faceRadius * 1.6f), style = Stroke(width = w * 0.09f))
                drawRoundRect(InkDark, Offset(faceCenter.x - faceRadius, faceCenter.y + faceRadius * 1.3f), Size(faceRadius * 2, h * 0.06f), CornerRadius(w * 0.02f, w * 0.02f))
            }
            CustomerAvatar.PERRO_CLIENTE -> {
                drawFace()
                drawOval(color = ShopBrown, topLeft = Offset(faceCenter.x - faceRadius * 1.2f, faceCenter.y - faceRadius * 0.3f), size = Size(faceRadius * 0.7f, faceRadius * 1.1f))
                drawOval(color = ShopBrown, topLeft = Offset(faceCenter.x + faceRadius * 0.5f, faceCenter.y - faceRadius * 0.3f), size = Size(faceRadius * 0.7f, faceRadius * 1.1f))
            }
        }
    }
}
