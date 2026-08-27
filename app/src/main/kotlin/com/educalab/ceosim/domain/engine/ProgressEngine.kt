package com.educalab.ceosim.domain.engine

/**
 * ProgressEngine
 *
 * El progreso de CEOSim se deriva SIEMPRE de acciones reales realizadas por
 * el niño (compras, ventas, mejoras, retos superados) — nunca de un valor
 * arbitrario. Cada acción otorga una cantidad fija y pequeña de experiencia
 * (XP), y el nivel se calcula a partir de la XP acumulada.
 */
object ProgressEngine {

    const val XP_PER_PURCHASE = 2
    const val XP_PER_SALE = 5
    const val XP_PER_UPGRADE = 8
    const val XP_PER_CHALLENGE = 10

    /** Umbrales de XP necesarios para alcanzar cada nivel (índice 0 = nivel 1). */
    private val LEVEL_THRESHOLDS = listOf(0, 20, 50, 100, 180, 300)

    data class LevelInfo(
        val level: Int,
        val currentXp: Int,
        val xpIntoLevel: Int,
        val xpForNextLevel: Int?,
        val progressFraction: Float
    )

    /** Calcula el nivel actual (1..N) a partir de la XP total acumulada. */
    fun levelForXp(totalXp: Int): Int {
        var level = 1
        for (i in LEVEL_THRESHOLDS.indices) {
            if (totalXp >= LEVEL_THRESHOLDS[i]) level = i + 1
        }
        return level.coerceAtMost(LEVEL_THRESHOLDS.size)
    }

    /** Información completa de nivel/progreso, lista para mostrar en la UI. */
    fun computeLevelInfo(totalXp: Int): LevelInfo {
        val safeXp = totalXp.coerceAtLeast(0)
        val level = levelForXp(safeXp)
        val floor = LEVEL_THRESHOLDS.getOrElse(level - 1) { 0 }
        val nextThreshold = LEVEL_THRESHOLDS.getOrNull(level)
        val xpIntoLevel = safeXp - floor
        val fraction = if (nextThreshold == null) {
            1f
        } else {
            val span = (nextThreshold - floor).coerceAtLeast(1)
            (xpIntoLevel.toFloat() / span).coerceIn(0f, 1f)
        }
        return LevelInfo(
            level = level,
            currentXp = safeXp,
            xpIntoLevel = xpIntoLevel,
            xpForNextLevel = nextThreshold,
            progressFraction = fraction
        )
    }

    fun maxLevel(): Int = LEVEL_THRESHOLDS.size
}
