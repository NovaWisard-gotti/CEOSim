package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SalesEngineTest {

    @Test
    fun `sell succeeds and computes earnings correctly`() {
        val result = SalesEngine.sell(currentBalance = 20, currentStock = 5, sellPrice = 8, quantity = 2)
        assertTrue(result is OperationResult.Success)
        val outcome = (result as OperationResult.Success).value
        assertEquals(36, outcome.newBalance) // 20 + 16
        assertEquals(3, outcome.newStock) // 5 - 2
        assertEquals(16, outcome.earned)
    }

    @Test
    fun `sell defaults to quantity 1 when a single customer buys`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 5, sellPrice = 8)
        val outcome = (result as OperationResult.Success).value
        assertEquals(1, outcome.quantitySold)
        assertEquals(4, outcome.newStock)
    }

    @Test
    fun `sell fails when there is no stock available`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 0, sellPrice = 8, quantity = 1)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_STOCK, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `selling twice in a row correctly depletes stock without going negative`() {
        val firstSale = SalesEngine.sell(currentBalance = 0, currentStock = 1, sellPrice = 5, quantity = 1)
        val stockAfterFirst = (firstSale as OperationResult.Success).value.newStock
        assertEquals(0, stockAfterFirst)

        // Doble venta ("doble toque"): el segundo intento debe fallar, nunca
        // dejar el inventario en negativo.
        val secondSale = SalesEngine.sell(currentBalance = 5, currentStock = stockAfterFirst, sellPrice = 5, quantity = 1)
        assertTrue(secondSale is OperationResult.Failure)
    }

    @Test
    fun `sell fails for zero quantity`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 5, sellPrice = 8, quantity = 0)
        assertEquals(FailureReason.INVALID_QUANTITY, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `sell fails for negative quantity`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 5, sellPrice = 8, quantity = -1)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `sell fails for invalid sell price`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 5, sellPrice = 0, quantity = 1)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `sell exactly depleting stock to zero is allowed`() {
        val result = SalesEngine.sell(currentBalance = 0, currentStock = 3, sellPrice = 5, quantity = 3)
        val outcome = (result as OperationResult.Success).value
        assertEquals(0, outcome.newStock)
    }
}
