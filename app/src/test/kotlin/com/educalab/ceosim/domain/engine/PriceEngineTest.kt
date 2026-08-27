package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PriceEngineTest {

    @Test
    fun `validateSellPrice succeeds for a positive price`() {
        val result = PriceEngine.validateSellPrice(buyCost = 5, proposedPrice = 8)
        assertEquals(OperationResult.Success(8), result)
    }

    @Test
    fun `validateSellPrice fails for zero price`() {
        val result = PriceEngine.validateSellPrice(buyCost = 5, proposedPrice = 0)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INVALID_PRICE, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `validateSellPrice fails for negative price`() {
        val result = PriceEngine.validateSellPrice(buyCost = 5, proposedPrice = -3)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `validateSellPrice fails for invalid buy cost`() {
        val result = PriceEngine.validateSellPrice(buyCost = 0, proposedPrice = 8)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `validateSellPrice allows a price lower than cost, it is not blocked`() {
        // No es contabilidad avanzada: se permite explorar, solo se prohíben
        // precios negativos o cero (regla absoluta #19).
        val result = PriceEngine.validateSellPrice(buyCost = 10, proposedPrice = 3)
        assertEquals(OperationResult.Success(3), result)
    }

    @Test
    fun `marginPerUnit computes profit correctly`() {
        assertEquals(3, PriceEngine.marginPerUnit(buyCost = 5, sellPrice = 8))
    }

    @Test
    fun `marginPerUnit computes loss correctly`() {
        assertEquals(-2, PriceEngine.marginPerUnit(buyCost = 10, sellPrice = 8))
    }

    @Test
    fun `classifyMargin returns GANANCIA when sell price is higher`() {
        assertEquals(MarginResult.GANANCIA, PriceEngine.classifyMargin(5, 8))
    }

    @Test
    fun `classifyMargin returns SIN_GANANCIA when prices are equal`() {
        assertEquals(MarginResult.SIN_GANANCIA, PriceEngine.classifyMargin(5, 5))
    }

    @Test
    fun `classifyMargin returns PERDIDA when sell price is lower`() {
        assertEquals(MarginResult.PERDIDA, PriceEngine.classifyMargin(10, 6))
    }
}
