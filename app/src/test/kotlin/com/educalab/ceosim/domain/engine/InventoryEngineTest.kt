package com.educalab.ceosim.domain.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InventoryEngineTest {

    @Test
    fun `hasStock true when quantity equals amount`() {
        assertTrue(InventoryEngine.hasStock(currentQuantity = 5, amount = 5))
    }

    @Test
    fun `hasStock false when quantity is less than amount`() {
        assertFalse(InventoryEngine.hasStock(currentQuantity = 2, amount = 5))
    }

    @Test
    fun `hasStock false for zero amount requested`() {
        assertFalse(InventoryEngine.hasStock(currentQuantity = 10, amount = 0))
    }

    @Test
    fun `addStock increases quantity`() {
        val result = InventoryEngine.addStock(currentQuantity = 3, amount = 4)
        assertEquals(OperationResult.Success(7), result)
    }

    @Test
    fun `addStock fails for zero or negative amount`() {
        assertTrue(InventoryEngine.addStock(0, 0) is OperationResult.Failure)
        assertTrue(InventoryEngine.addStock(0, -2) is OperationResult.Failure)
    }

    @Test
    fun `removeStock decreases quantity`() {
        val result = InventoryEngine.removeStock(currentQuantity = 10, amount = 3)
        assertEquals(OperationResult.Success(7), result)
    }

    @Test
    fun `removeStock fails when there is not enough stock`() {
        val result = InventoryEngine.removeStock(currentQuantity = 2, amount = 5)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_STOCK, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `removeStock never allows inventory to go negative`() {
        val result = InventoryEngine.removeStock(currentQuantity = 0, amount = 1)
        assertTrue(result is OperationResult.Failure)
    }

    @Test
    fun `removeStock exactly draining stock to zero is allowed`() {
        val result = InventoryEngine.removeStock(currentQuantity = 4, amount = 4)
        assertEquals(OperationResult.Success(0), result)
    }

    @Test
    fun `removeStock fails for zero or negative amount`() {
        assertTrue(InventoryEngine.removeStock(5, 0) is OperationResult.Failure)
        assertTrue(InventoryEngine.removeStock(5, -1) is OperationResult.Failure)
    }

    @Test
    fun `stockLevel classifies correctly across boundaries`() {
        assertEquals(StockLevel.AGOTADO, InventoryEngine.stockLevel(0))
        assertEquals(StockLevel.BAJO, InventoryEngine.stockLevel(1))
        assertEquals(StockLevel.BAJO, InventoryEngine.stockLevel(3))
        assertEquals(StockLevel.NORMAL, InventoryEngine.stockLevel(4))
        assertEquals(StockLevel.NORMAL, InventoryEngine.stockLevel(9))
        assertEquals(StockLevel.ALTO, InventoryEngine.stockLevel(10))
    }
}
