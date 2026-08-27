package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PurchaseEngineTest {

    @Test
    fun `purchase succeeds and computes total cost correctly`() {
        val result = PurchaseEngine.purchase(currentBalance = 50, currentStock = 2, unitCost = 5, quantity = 3)
        assertTrue(result is OperationResult.Success)
        val outcome = (result as OperationResult.Success).value
        assertEquals(35, outcome.newBalance) // 50 - 15
        assertEquals(5, outcome.newStock) // 2 + 3
        assertEquals(15, outcome.totalCost)
        assertEquals(3, outcome.quantityBought)
    }

    @Test
    fun `purchase fails when balance is insufficient`() {
        val result = PurchaseEngine.purchase(currentBalance = 10, currentStock = 0, unitCost = 5, quantity = 3)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_FUNDS, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `purchase with balance of exactly zero always fails`() {
        val result = PurchaseEngine.purchase(currentBalance = 0, currentStock = 0, unitCost = 5, quantity = 1)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `purchase fails for zero quantity`() {
        val result = PurchaseEngine.purchase(currentBalance = 50, currentStock = 0, unitCost = 5, quantity = 0)
        assertEquals(FailureReason.INVALID_QUANTITY, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `purchase fails for negative quantity`() {
        val result = PurchaseEngine.purchase(currentBalance = 50, currentStock = 0, unitCost = 5, quantity = -2)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `purchase fails for invalid unit cost`() {
        val result = PurchaseEngine.purchase(currentBalance = 50, currentStock = 0, unitCost = 0, quantity = 2)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `purchase spending the exact balance leaves zero coins`() {
        val result = PurchaseEngine.purchase(currentBalance = 15, currentStock = 0, unitCost = 5, quantity = 3)
        val outcome = (result as OperationResult.Success).value
        assertEquals(0, outcome.newBalance)
    }
}
