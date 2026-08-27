package com.educalab.ceosim.ui.illustrations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educalab.ceosim.domain.model.ProductCategory

/** Emoji por categoría, usado cuando no conocemos el producto exacto (p. ej. iconos de módulo). */
private val categoryEmoji: Map<ProductCategory, String> = mapOf(
    ProductCategory.BEBIDA to "🧃",
    ProductCategory.FRUTA to "🍎",
    ProductCategory.PAPELERIA to "📓",
    ProductCategory.JUGUETE to "🧸",
    ProductCategory.GALLETA to "🍪",
    ProductCategory.PLANTA to "🪴",
    ProductCategory.DEPORTE to "⚽",
    ProductCategory.LIBRO to "📖"
)

/** Emoji específico por producto, más preciso que el de categoría (p. ej. distingue pelota de trompo). */
private val productEmoji: Map<String, String> = mapOf(
    "jugo_naranja" to "🧃",
    "jugo_manzana" to "🧃",
    "agua_fresca" to "💧",
    "manzana_roja" to "🍎",
    "platano" to "🍌",
    "naranja_fruta" to "🍊",
    "cuaderno_rayado" to "📓",
    "lapiz_grafito" to "✏️",
    "goma_borrar" to "🧼",
    "colores_caja" to "🖍️",
    "pelota_futbol" to "⚽",
    "trompo_madera" to "🪀",
    "carrito_juguete" to "🚗",
    "galleta_avena" to "🍪",
    "galleta_chocolate" to "🍫",
    "planta_maceta" to "🪴",
    "cactus_mini" to "🌵",
    "cuerda_saltar" to "🪢",
    "gorra_deportiva" to "🧢",
    "libro_cuentos" to "📖"
)

/**
 * Ilustración de producto: un emoji congruente con el producto real.
 * Si se conoce el [productId] se usa un emoji específico; si no, se cae
 * al emoji genérico de la [category] (por ejemplo, para iconos de módulo).
 */
@Composable
fun ProductIllustration(
    category: ProductCategory,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    productId: String? = null
) {
    val emoji = productId?.let { productEmoji[it] } ?: categoryEmoji[category] ?: "🛍️"
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Text(text = emoji, fontSize = (size.value * 0.55f).sp)
    }
}
