package com.educalab.ceosim.domain.model

enum class UpgradeCategory { ESTANTE, DECORACION, MOSTRADOR, ILUMINACION, CARTEL }

/** Mejora visual/funcional que el niño puede comprar para su tienda (Módulo 6). */
data class StoreUpgrade(
    val id: String,
    val name: String,
    val category: UpgradeCategory,
    val cost: Int,
    val unlockLevel: Int = 1,
    val description: String
)
