package com.educalab.ceosim.domain.engine

/**
 * RewardEngine
 *
 * Define las 12 insignias mínimas de CEOSim (sección "RECOMPENSAS") y evalúa,
 * a partir de estadísticas reales de la tienda, cuáles deben desbloquearse.
 * Nunca desbloquea una insignia por una acción que no haya ocurrido.
 */
object RewardEngine {

    /** Estadísticas reales de la tienda, calculadas a partir de datos persistidos. */
    data class ShopStats(
        val totalPurchases: Int = 0,
        val totalSales: Int = 0,
        val totalUpgrades: Int = 0,
        val totalChallengesCompleted: Int = 0,
        val distinctProductsInStock: Int = 0,
        val maxBalanceReached: Int = 0,
        val unlockedBadgeCount: Int = 0
    )

    data class BadgeDefinition(
        val id: String,
        val title: String,
        val description: String,
        val condition: (ShopStats) -> Boolean
    )

    val ALL_BADGES: List<BadgeDefinition> = listOf(
        BadgeDefinition(
            id = "primera_compra",
            title = "Primera Compra",
            description = "Compraste tu primer producto para la tienda.",
            condition = { it.totalPurchases >= 1 }
        ),
        BadgeDefinition(
            id = "primera_venta",
            title = "Primera Venta",
            description = "Vendiste tu primer producto a un cliente.",
            condition = { it.totalSales >= 1 }
        ),
        BadgeDefinition(
            id = "tienda_organizada",
            title = "Tienda Organizada",
            description = "Tienes 5 productos distintos ordenados en tus estantes.",
            condition = { it.distinctProductsInStock >= 5 }
        ),
        BadgeDefinition(
            id = "buen_vendedor",
            title = "Buen Vendedor",
            description = "Completaste 10 ventas en tu tienda.",
            condition = { it.totalSales >= 10 }
        ),
        BadgeDefinition(
            id = "inventario_completo",
            title = "Inventario Completo",
            description = "Tienes 10 productos distintos disponibles a la vez.",
            condition = { it.distinctProductsInStock >= 10 }
        ),
        BadgeDefinition(
            id = "ahorrador",
            title = "Ahorrador",
            description = "Llegaste a juntar 100 monedas en tu caja.",
            condition = { it.maxBalanceReached >= 100 }
        ),
        BadgeDefinition(
            id = "gran_organizador",
            title = "Gran Organizador",
            description = "Tienes 8 productos distintos ordenados en tus estantes.",
            condition = { it.distinctProductsInStock >= 8 }
        ),
        BadgeDefinition(
            id = "primera_mejora",
            title = "Primera Mejora",
            description = "Compraste tu primera mejora para la tienda.",
            condition = { it.totalUpgrades >= 1 }
        ),
        BadgeDefinition(
            id = "tienda_popular",
            title = "Tienda Popular",
            description = "Completaste 25 ventas: ¡tu tienda es conocida!",
            condition = { it.totalSales >= 25 }
        ),
        BadgeDefinition(
            id = "buen_administrador",
            title = "Buen Administrador",
            description = "Superaste 5 pequeños retos de la tienda.",
            condition = { it.totalChallengesCompleted >= 5 }
        ),
        BadgeDefinition(
            id = "gran_emprendedor",
            title = "Gran Emprendedor",
            description = "50 ventas y 5 mejoras: tu tienda crece de verdad.",
            condition = { it.totalSales >= 50 && it.totalUpgrades >= 5 }
        ),
        BadgeDefinition(
            id = "maestro_ceosim",
            title = "Maestro CEOSim",
            description = "Desbloqueaste todas las demás insignias de la tienda.",
            condition = { it.unlockedBadgeCount >= 11 }
        )
    )

    /**
     * Dado el conjunto de ids ya desbloqueados y las estadísticas actuales,
     * devuelve la lista de ids de insignias que deben desbloquearse ahora
     * (nuevas, que aún no estaban en [alreadyUnlocked]).
     */
    fun evaluateNewUnlocks(stats: ShopStats, alreadyUnlocked: Set<String>): List<String> {
        // La insignia "maestro_ceosim" se evalúa con el conteo de insignias
        // YA desbloqueadas (sin contarse a sí misma), así que se procesa aparte.
        val regularBadges = ALL_BADGES.filter { it.id != "maestro_ceosim" }
        val newlyUnlocked = regularBadges
            .filter { it.id !in alreadyUnlocked }
            .filter { it.condition(stats) }
            .map { it.id }

        val statsWithNewCount = stats.copy(
            unlockedBadgeCount = alreadyUnlocked.size + newlyUnlocked.size
        )
        val masterBadge = ALL_BADGES.first { it.id == "maestro_ceosim" }
        val masterUnlocks = if (masterBadge.id !in alreadyUnlocked && masterBadge.condition(statsWithNewCount)) {
            listOf(masterBadge.id)
        } else {
            emptyList()
        }

        return newlyUnlocked + masterUnlocks
    }
}
