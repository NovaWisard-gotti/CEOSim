package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.OperationResult.Failure
import com.educalab.ceosim.domain.engine.OperationResult.Success

/**
 * MoneyEngine
 *
 * Controla el saldo de monedas ficticias de la tienda.
 * Regla absoluta #18 de la especificación: nunca permitir saldo negativo.
 *
 * Es un motor puro: no conoce Room, Compose ni Android. Recibe el saldo
 * actual y devuelve el nuevo saldo, sin efectos secundarios.
 */
object MoneyEngine {

    const val STARTING_BALANCE = 50

    /** ¿El saldo actual alcanza para gastar [amount] monedas? */
    fun canAfford(balance: Int, amount: Int): Boolean {
        if (amount <= 0) return false
        return balance >= amount
    }

    /**
     * Descuenta [amount] monedas del saldo.
     * Falla si el monto no es positivo o si no hay saldo suficiente.
     */
    fun spend(balance: Int, amount: Int): OperationResult<Int> {
        if (amount <= 0) return Failure(FailureReason.INVALID_AMOUNT, "El monto a gastar debe ser mayor que cero")
        if (balance < amount) return Failure(FailureReason.INSUFFICIENT_FUNDS, "No tienes monedas suficientes")
        return Success(balance - amount)
    }

    /**
     * Suma [amount] monedas al saldo (por ejemplo, tras una venta).
     * Falla si el monto no es positivo.
     */
    fun earn(balance: Int, amount: Int): OperationResult<Int> {
        if (amount <= 0) return Failure(FailureReason.INVALID_AMOUNT, "El monto ganado debe ser mayor que cero")
        return Success(balance + amount)
    }
}
