package com.educalab.ceosim.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidades de historial (append-only, crecen con el uso de la app):
 * Purchase, Sale, CustomerRequest, UserUpgrade, ChallengeAttempt, UserBadge,
 * Transaction.
 */

@Entity(
    tableName = "purchases",
    foreignKeys = [
        ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = ["productId"])
    ],
    indices = [Index("productId")]
)
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val quantity: Int,
    val unitCost: Int,
    val totalCost: Int,
    val timestamp: Long
)

@Entity(
    tableName = "sales",
    foreignKeys = [
        ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = ["productId"]),
        ForeignKey(entity = CustomerEntity::class, parentColumns = ["id"], childColumns = ["customerId"])
    ],
    indices = [Index("productId"), Index("customerId")]
)
data class SaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val customerId: String?,
    val quantity: Int,
    val unitPrice: Int,
    val totalEarned: Int,
    val timestamp: Long
)

@Entity(
    tableName = "customer_requests",
    foreignKeys = [
        ForeignKey(entity = CustomerEntity::class, parentColumns = ["id"], childColumns = ["customerId"]),
        ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = ["productId"])
    ],
    indices = [Index("customerId"), Index("productId")]
)
data class CustomerRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: String,
    val productId: String,
    val quantity: Int,
    val fulfilled: Boolean,
    val timestamp: Long
)

@Entity(
    tableName = "user_upgrades",
    foreignKeys = [
        ForeignKey(entity = StoreUpgradeEntity::class, parentColumns = ["id"], childColumns = ["upgradeId"])
    ],
    indices = [Index("upgradeId", unique = true)]
)
data class UserUpgradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val upgradeId: String,
    val purchasedAt: Long
)

@Entity(
    tableName = "challenge_attempts",
    foreignKeys = [
        ForeignKey(entity = ChallengeEntity::class, parentColumns = ["id"], childColumns = ["challengeId"])
    ],
    indices = [Index("challengeId")]
)
data class ChallengeAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val challengeId: String,
    val completed: Boolean,
    val completedAt: Long?
)

@Entity(
    tableName = "user_badges",
    foreignKeys = [
        ForeignKey(entity = BadgeEntity::class, parentColumns = ["id"], childColumns = ["badgeId"])
    ],
    indices = [Index("badgeId", unique = true)]
)
data class UserBadgeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val badgeId: String,
    val unlockedAt: Long
)

enum class TransactionType { COMPRA, VENTA, MEJORA }

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: TransactionType,
    val amount: Int, // positivo = ingreso, negativo = gasto
    val balanceAfter: Int,
    val description: String,
    val timestamp: Long
)
