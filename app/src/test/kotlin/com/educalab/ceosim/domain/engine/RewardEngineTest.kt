package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.RewardEngine.ShopStats
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RewardEngineTest {

    @Test
    fun `there are at least 12 badges defined`() {
        assertTrue(RewardEngine.ALL_BADGES.size >= 12)
    }

    @Test
    fun `all badge ids are unique`() {
        val ids = RewardEngine.ALL_BADGES.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `first purchase badge unlocks after exactly one purchase`() {
        val stats = ShopStats(totalPurchases = 1)
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, emptySet())
        assertTrue("primera_compra" in unlocked)
    }

    @Test
    fun `first sale badge unlocks after exactly one sale`() {
        val stats = ShopStats(totalSales = 1)
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, emptySet())
        assertTrue("primera_venta" in unlocked)
    }

    @Test
    fun `badges already unlocked are never returned again`() {
        val stats = ShopStats(totalPurchases = 5)
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, alreadyUnlocked = setOf("primera_compra"))
        assertFalse("primera_compra" in unlocked)
    }

    @Test
    fun `buen vendedor unlocks only from 10 sales onward`() {
        val below = RewardEngine.evaluateNewUnlocks(ShopStats(totalSales = 9), emptySet())
        assertFalse("buen_vendedor" in below)

        val atThreshold = RewardEngine.evaluateNewUnlocks(ShopStats(totalSales = 10), emptySet())
        assertTrue("buen_vendedor" in atThreshold)
    }

    @Test
    fun `gran emprendedor requires both sales and upgrades thresholds`() {
        val onlySales = RewardEngine.evaluateNewUnlocks(ShopStats(totalSales = 60, totalUpgrades = 1), emptySet())
        assertFalse("gran_emprendedor" in onlySales)

        val both = RewardEngine.evaluateNewUnlocks(ShopStats(totalSales = 60, totalUpgrades = 6), emptySet())
        assertTrue("gran_emprendedor" in both)
    }

    @Test
    fun `maestro ceosim only unlocks after all other 11 badges`() {
        val elevenOfTwelve = RewardEngine.ALL_BADGES.map { it.id }.filter { it != "maestro_ceosim" }.toSet()
        val stats = ShopStats() // sin nuevas acciones, solo evaluando el maestro
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, alreadyUnlocked = elevenOfTwelve)
        assertTrue("maestro_ceosim" in unlocked)
    }

    @Test
    fun `maestro ceosim does not unlock with fewer than 11 badges`() {
        val fewBadges = setOf("primera_compra", "primera_venta")
        val stats = ShopStats()
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, alreadyUnlocked = fewBadges)
        assertFalse("maestro_ceosim" in unlocked)
    }

    @Test
    fun `evaluateNewUnlocks with empty stats and no history unlocks nothing`() {
        val unlocked = RewardEngine.evaluateNewUnlocks(ShopStats(), emptySet())
        assertTrue(unlocked.isEmpty())
    }

    @Test
    fun `ahorrador unlocks based on max balance ever reached, not current balance`() {
        val stats = ShopStats(maxBalanceReached = 120)
        val unlocked = RewardEngine.evaluateNewUnlocks(stats, emptySet())
        assertTrue("ahorrador" in unlocked)
    }
}
