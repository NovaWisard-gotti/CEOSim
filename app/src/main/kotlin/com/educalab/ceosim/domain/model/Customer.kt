package com.educalab.ceosim.domain.model

/** Variantes visuales de cliente ficticio (Módulo 4 — Mostrador). */
enum class CustomerAvatar {
    NINA_GORRA, NINO_LENTES, ABUELA_BUFANDA, ABUELO_SOMBRERO,
    NINA_TRENZAS, NINO_CHALECO, ROBOT_AMIGABLE, GATO_CLIENTE,
    NINA_PATINETA, PERRO_CLIENTE
}

/** Definición de un cliente ficticio que puede visitar la tienda. */
data class Customer(
    val id: String,
    val name: String,
    val avatar: CustomerAvatar,
    val greeting: String
)

/** Estado de una visita de cliente concreta pidiendo un producto. */
data class CustomerRequest(
    val customer: Customer,
    val requestedProductId: String,
    val quantity: Int = 1
)
