package com.educalab.ceosim.domain.model

enum class ChallengeType { REABASTECER, ATENDER_CLIENTE, AJUSTAR_PRECIO, MEJORAR_TIENDA, AHORRAR }

/**
 * Pequeño reto contextual dentro de la simulación (Módulo 7).
 * No es un cuestionario aparte: ocurre dentro de la tienda.
 */
data class Challenge(
    val id: String,
    val title: String,
    val narrative: String,
    val type: ChallengeType,
    val targetProductId: String? = null,
    val targetQuantity: Int = 1,
    val xpReward: Int = ProgressXp.CHALLENGE
)

/** Insignia visual coleccionable (Módulo de recompensas). */
data class Badge(
    val id: String,
    val title: String,
    val description: String
)

object ProgressXp {
    const val PURCHASE = 2
    const val SALE = 5
    const val UPGRADE = 8
    const val CHALLENGE = 10
}
