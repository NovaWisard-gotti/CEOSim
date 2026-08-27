package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.OperationResult.Failure
import com.educalab.ceosim.domain.engine.OperationResult.Success

/**
 * InventoryEngine
 *
 * Controla las cantidades de productos en el almacén/estantes de la tienda.
 * Regla absoluta #18: nunca permitir inventario negativo.
 */
object InventoryEngine {

    /** ¿Hay al menos [amount] unidades disponibles? */
    fun hasStock(currentQuantity: Int, amount: Int): Boolean {
        if (amount <= 0) return false
        return currentQuantity >= amount
    }

    /**
     * Añade [amount] unidades al inventario (por ejemplo, tras una compra
     * en el almacén).
     */
    fun addStock(currentQuantity: Int, amount: Int): OperationResult<Int> {
        if (amount <= 0) {
            return Failure(FailureReason.INVALID_QUANTITY, "La cantidad a añadir debe ser mayor que cero")
        }
        return Success(currentQuantity + amount)
    }

    /**
     * Retira [amount] unidades del inventario (por ejemplo, tras una venta
     * en el mostrador). Falla si no hay suficiente stock.
     */
    fun removeStock(currentQuantity: Int, amount: Int): OperationResult<Int> {
        if (amount <= 0) {
            return Failure(FailureReason.INVALID_QUANTITY, "La cantidad a retirar debe ser mayor que cero")
        }
        if (currentQuantity < amount) {
            return Failure(FailureReason.INSUFFICIENT_STOCK, "No hay suficientes productos en la tienda")
        }
        return Success(currentQuantity - amount)
    }

    /** Nivel de stock cualitativo, usado por Nico y por los retos (Módulo 7). */
    fun stockLevel(currentQuantity: Int): StockLevel = when {
        currentQuantity <= 0 -> StockLevel.AGOTADO
        currentQuantity in 1..3 -> StockLevel.BAJO
        currentQuantity in 4..9 -> StockLevel.NORMAL
        else -> StockLevel.ALTO
    }
}

enum class StockLevel { AGOTADO, BAJO, NORMAL, ALTO }
