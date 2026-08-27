package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MoneyEngineTest {

    @Test
    fun `canAfford is true when balance equals amount`() {
        assertTrue(MoneyEngine.canAfford(balance = 10, amount = 10))
    }

    @Test
    fun `canAfford is true when balance is greater than amount`() {
        assertTrue(MoneyEngine.canAfford(balance = 20, amount = 10))
    }

    @Test
    fun `canAfford is false when balance is less than amount`() {
        assertFalse(MoneyEngine.canAfford(balance = 5, amount = 10))
    }

    @Test
    fun `canAfford is false for zero or negative amount`() {
        assertFalse(MoneyEngine.canAfford(balance = 10, amount = 0))
        assertFalse(MoneyEngine.canAfford(balance = 10, amount = -5))
    }

    @Test
    fun `spend reduces balance correctly`() {
        val result = MoneyEngine.spend(balance = 20, amount = 8)
        assertEquals(OperationResult.Success(12), result)
    }

    @Test
    fun `spend fails with insufficient funds when balance is zero`() {
        val result = MoneyEngine.spend(balance = 0, amount = 5)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_FUNDS, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `spend never allows the balance to go negative`() {
        val result = MoneyEngine.spend(balance = 4, amount = 5)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `spend fails for zero amount`() {
        val result = MoneyEngine.spend(balance = 10, amount = 0)
        assertEquals(FailureReason.INVALID_AMOUNT, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `spend fails for negative amount`() {
        val result = MoneyEngine.spend(balance = 10, amount = -3)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `earn increases balance correctly`() {
        val result = MoneyEngine.earn(balance = 10, amount = 7)
        assertEquals(OperationResult.Success(17), result)
    }

    @Test
    fun `earn fails for zero or negative amount`() {
        assertTrue(MoneyEngine.earn(balance = 10, amount = 0) is OperationResult.Failure)
        assertTrue(MoneyEngine.earn(balance = 10, amount = -1) is OperationResult.Failure)
    }

    @Test
    fun `spend exactly draining balance to zero is allowed`() {
        val result = MoneyEngine.spend(balance = 15, amount = 15)
        assertEquals(OperationResult.Success(0), result)
    }
}
