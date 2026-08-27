package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.OperationResult.Failure
import com.educalab.ceosim.domain.engine.OperationResult.Success

/**
 * PurchaseEngine (Módulo 1 — Almacén)
 *
 * Orquesta una compra: verifica monedas disponibles, calcula el costo total,
 * y devuelve el nuevo saldo y el nuevo stock. No persiste nada: eso lo hace
 * el repositorio a partir del resultado de este motor.
 */
object PurchaseEngine {

    data class PurchaseOutcome(
        val newBalance: Int,
        val newStock: Int,
        val totalCost: Int,
        val quantityBought: Int
    )

    /**
     * Intenta comprar [quantity] unidades de un producto cuyo costo unitario
     * es [unitCost], partiendo del saldo [currentBalance] y del stock
     * [currentStock] ya existente en el inventario.
     */
    fun purchase(
        currentBalance: Int,
        currentStock: Int,
        unitCost: Int,
        quantity: Int
    ): OperationResult<PurchaseOutcome> {
        if (quantity <= 0) {
            return Failure(FailureReason.INVALID_QUANTITY, "Elige al menos 1 unidad para comprar")
        }
        if (unitCost <= 0) {
            return Failure(FailureReason.INVALID_PRICE, "El costo del producto no es válido")
        }

        val totalCost = unitCost * quantity

        val newBalance = when (val moneyResult = MoneyEngine.spend(currentBalance, totalCost)) {
            is Success -> moneyResult.value
            is Failure -> return moneyResult
        }

        val newStock = when (val stockResult = InventoryEngine.addStock(currentStock, quantity)) {
            is Success -> stockResult.value
            is Failure -> return stockResult
        }

        return Success(
            PurchaseOutcome(
                newBalance = newBalance,
                newStock = newStock,
                totalCost = totalCost,
                quantityBought = quantity
            )
        )
    }
}
