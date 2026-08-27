package com.educalab.ceosim.domain.engine

/**
 * Resultado explícito de una operación de un motor de dominio.
 *
 * Se usa en lugar de excepciones para que la UI pueda mostrar mensajes
 * educativos claros (a través de Nico, el personaje guía) en lugar de
 * simplemente fallar.
 */
sealed interface OperationResult<out T> {
    data class Success<T>(val value: T) : OperationResult<T>
    data class Failure(val reason: FailureReason, val detail: String = "") : OperationResult<Nothing>
}

/**
 * Motivos de fallo que puede producir cualquier motor de CEOSim.
 * Cada uno se traduce en la UI a un mensaje corto y amable de Nico.
 */
enum class FailureReason {
    INSUFFICIENT_FUNDS,
    INSUFFICIENT_STOCK,
    INVALID_QUANTITY,
    INVALID_PRICE,
    INVALID_AMOUNT,
    PRODUCT_NOT_FOUND,
    ALREADY_UNLOCKED,
    NOT_ENOUGH_LEVEL
}

inline fun <T> OperationResult<T>.onSuccess(block: (T) -> Unit): OperationResult<T> {
    if (this is OperationResult.Success) block(value)
    return this
}

inline fun <T> OperationResult<T>.onFailure(block: (FailureReason, String) -> Unit): OperationResult<T> {
    if (this is OperationResult.Failure) block(reason, detail)
    return this
}

fun <T> OperationResult<T>.getOrNull(): T? = (this as? OperationResult.Success)?.value

fun <T> OperationResult<T>.isSuccess(): Boolean = this is OperationResult.Success
