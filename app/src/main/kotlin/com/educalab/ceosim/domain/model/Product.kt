package com.educalab.ceosim.domain.model

/** Categorías visuales de producto, usadas para elegir la ilustración correcta. */
enum class ProductCategory {
    BEBIDA, FRUTA, PAPELERIA, JUGUETE, GALLETA, PLANTA, DEPORTE, LIBRO
}

/**
 * Catálogo de un producto disponible en el Almacén (Módulo 1).
 * No representa el stock actual (eso vive en [InventoryItem]); es la
 * definición: cómo se llama, cuánto cuesta comprarlo y a qué nivel se
 * desbloquea en el almacén.
 */
data class Product(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val buyCost: Int,
    val defaultSellPrice: Int,
    val unlockLevel: Int = 1
)

/** Cantidad actual de un producto en los estantes/inventario de la tienda. */
data class InventoryItem(
    val productId: String,
    val quantity: Int,
    val sellPrice: Int
)
