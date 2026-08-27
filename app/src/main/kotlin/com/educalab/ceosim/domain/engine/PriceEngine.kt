package com.educalab.ceosim.domain.engine

import com.educalab.ceosim.domain.engine.OperationResult.Failure
import com.educalab.ceosim.domain.engine.OperationResult.Success

/**
 * PriceEngine
 *
 * Controla los precios de venta que el niño asigna a cada producto.
 * Módulo 3 de la especificación: costo de compra + precio de venta.
 *
 * No implementa contabilidad avanzada: solo valida que los precios sean
 * positivos y clasifica el margen de forma educativa y sencilla.
 */
object PriceEngine {

    /**
     * Valida un precio de venta propuesto para un producto con costo [buyCost].
     * Solo se prohíben precios negativos o cero (regla absoluta #19).
     * No se obliga a que el precio de venta sea mayor al costo: eso es una
     * decisión educativa que el niño puede explorar y de la que Nico explica
     * las consecuencias mediante [classifyMargin].
     */
    fun validateSellPrice(buyCost: Int, proposedPrice: Int): OperationResult<Int> {
        if (buyCost <= 0) return Failure(FailureReason.INVALID_PRICE, "El costo de compra debe ser mayor que cero")
        if (proposedPrice <= 0) return Failure(FailureReason.INVALID_PRICE, "El precio de venta debe ser mayor que cero")
        return Success(proposedPrice)
    }

    /** Ganancia (puede ser negativa) por unidad vendida. */
    fun marginPerUnit(buyCost: Int, sellPrice: Int): Int = sellPrice - buyCost

    /** Clasificación educativa y sencilla del margen. */
    fun classifyMargin(buyCost: Int, sellPrice: Int): MarginResult {
        val margin = marginPerUnit(buyCost, sellPrice)
        return when {
            margin > 0 -> MarginResult.GANANCIA
            margin == 0 -> MarginResult.SIN_GANANCIA
            else -> MarginResult.PERDIDA
        }
    }
}

enum class MarginResult { GANANCIA, SIN_GANANCIA, PERDIDA }
