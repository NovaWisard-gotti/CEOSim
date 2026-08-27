package com.educalab.ceosim.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidades de estado actual (se actualizan constantemente a medida que el
 * niño juega): UserProfile, Store, Inventory, ProductPrice, Progress.
 *
 * UserProfile, Store y Progress son "singleton rows": siempre existe
 * exactamente una fila con id = SINGLETON_ID.
 */

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = SINGLETON_ID,
    val alias: String,
    val avatarId: Int,
    val soundEnabled: Boolean = true,
    val hapticEnabled: Boolean = true,
    val onboardingCompleted: Boolean = false,
    val createdAt: Long
) {
    companion object {
        const val SINGLETON_ID = 1
    }
}

@Entity(tableName = "store")
data class StoreEntity(
    @PrimaryKey val id: Int = SINGLETON_ID,
    val storeName: String,
    val balance: Int,
    val maxBalanceReached: Int
) {
    companion object {
        const val SINGLETON_ID = 1
    }
}

@Entity(
    tableName = "inventory",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId", unique = true)]
)
data class InventoryEntity(
    @PrimaryKey val productId: String,
    val quantity: Int
)

@Entity(
    tableName = "product_prices",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId", unique = true)]
)
data class ProductPriceEntity(
    @PrimaryKey val productId: String,
    val sellPrice: Int
)

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val id: Int = SINGLETON_ID,
    val totalXp: Int,
    val level: Int,
    val updatedAt: Long
) {
    companion object {
        const val SINGLETON_ID = 1
    }
}
