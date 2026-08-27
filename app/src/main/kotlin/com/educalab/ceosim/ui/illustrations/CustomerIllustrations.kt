package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educalab.ceosim.domain.model.CustomerAvatar

/** Emoji congruente con cada tipo de cliente ficticio del Mostrador. */
private val avatarEmoji: Map<CustomerAvatar, String> = mapOf(
    CustomerAvatar.NINA_TRENZAS to "👧",
    CustomerAvatar.NINO_LENTES to "🤓",
    CustomerAvatar.ABUELA_BUFANDA to "👵",
    CustomerAvatar.ABUELO_SOMBRERO to "👴",
    CustomerAvatar.NINA_GORRA to "👧🧢",
    CustomerAvatar.NINO_CHALECO to "👦🦺",
    CustomerAvatar.ROBOT_AMIGABLE to "🤖",
    CustomerAvatar.GATO_CLIENTE to "🐱",
    CustomerAvatar.NINA_PATINETA to "👧🛹",
    CustomerAvatar.PERRO_CLIENTE to "🐶"
)

/**
 * Avatar de un cliente ficticio del Mostrador, representado con un emoji
 * distinto y reconocible para cada tipo de cliente.
 */
@Composable
fun CustomerIllustration(avatar: CustomerAvatar, modifier: Modifier = Modifier, size: Dp = 64.dp) {
    val emoji = avatarEmoji[avatar] ?: "🙂"
    // Los avatares con dos emojis (persona + accesorio) son más anchos:
    // se escalan un poco más pequeños para que quepan en el mismo tamaño de caja.
    val scale = if (emoji.length > 2) 0.32f else 0.5f
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Text(
            text = emoji,
            fontSize = (size.value * scale).sp,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Visible
        )
    }
}
