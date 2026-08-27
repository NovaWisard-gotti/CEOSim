package com.educalab.ceosim.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.illustrations.CoinIcon
import com.educalab.ceosim.ui.illustrations.NicoCharacter
import com.educalab.ceosim.ui.illustrations.NicoExpression
import com.educalab.ceosim.ui.theme.CoinGoldDark
import com.educalab.ceosim.ui.theme.ShopGreenLight
import com.educalab.ceosim.ui.theme.ShopOrangeLight

/** Chip que muestra el saldo de monedas ficticias, siempre visible en la tienda. */
@Composable
fun BalanceChip(balance: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ShopOrangeLight)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        CoinIcon(size = 20.dp)
        Text(text = "$balance", style = MaterialTheme.typography.titleMedium)
    }
}

/** Burbuja de diálogo de Nico. Mensajes cortos, nunca diálogos extensos. */
@Composable
fun NicoBubble(
    message: String,
    modifier: Modifier = Modifier,
    expression: NicoExpression = NicoExpression.NEUTRAL
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        NicoCharacter(expression = expression, size = 56.dp)
        Card(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/** Barra de progreso de nivel, animada, con etiqueta de nivel. */
@Composable
fun LevelProgressBar(level: Int, fraction: Float, modifier: Modifier = Modifier) {
    val animatedFraction by animateFloatAsState(targetValue = fraction, animationSpec = tween(500), label = "levelProgress")
    Column(modifier = modifier) {
        Text(text = "Nivel $level", style = MaterialTheme.typography.labelLarge)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(ShopGreenLight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedFraction.coerceIn(0f, 1f))
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(CoinGoldDark)
            )
        }
    }
}

/** Etiqueta de estado de un módulo/producto (bloqueado, disponible, etc). No depende solo del color. */
@Composable
fun StatusTag(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.18f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium, color = color)
    }
}
