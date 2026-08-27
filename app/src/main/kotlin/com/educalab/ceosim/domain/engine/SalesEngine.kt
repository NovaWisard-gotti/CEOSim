package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.OperationResult.Failure
import com.educalab.ceosim.domain.engine.OperationResult.Success

/**
 * SalesEngine (Módulo 4 — Mostrador)
 *
 * Orquesta una venta a un cliente ficticio: verifica que haya stock,
 * descuenta el inventario y aumenta el saldo de monedas.
 */
object SalesEngine {

    data class SaleOutcome(
        val newBalance: Int,
        val newStock: Int,
        val earned: Int,
        val quantitySold: Int
    )

    /**
     * Vende [quantity] unidades (por defecto 1, como pide un cliente) de un
     * producto cuyo precio de venta es [sellPrice].
     */
    fun sell(
        currentBalance: Int,
        currentStock: Int,
        sellPrice: Int,
        quantity: Int = 1
    ): OperationResult<SaleOutcome> {
        if (quantity <= 0) {
            return Failure(FailureReason.INVALID_QUANTITY, "La cantidad a vender debe ser mayor que cero")
        }
        if (sellPrice <= 0) {
            return Failure(FailureReason.INVALID_PRICE, "El precio de venta no es válido")
        }

        val newStock = when (val stockResult = InventoryEngine.removeStock(currentStock, quantity)) {
            is Success -> stockResult.value
            is Failure -> return stockResult
        }

        val earned = sellPrice * quantity
        val newBalance = when (val moneyResult = MoneyEngine.earn(currentBalance, earned)) {
            is Success -> moneyResult.value
            is Failure -> return moneyResult
        }

        return Success(
            SaleOutcome(
                newBalance = newBalance,
                newStock = newStock,
                earned = earned,
                quantitySold = quantity
            )
        )
    }
}
