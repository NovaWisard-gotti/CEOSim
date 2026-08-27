package com.educalab.ceosim.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.educalab.ceosim.domain.model.ChallengeType
import com.educalab.ceosim.domain.model.CustomerAvatar
import com.educalab.ceosim.domain.model.ProductCategory
import com.educalab.ceosim.domain.model.UpgradeCategory

/**
 * Entidades de catálogo (datos semilla, prácticamente de solo lectura):
 * Product, Customer, StoreUpgrade, Challenge, Badge.
 */

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: ProductCategory,
    val buyCost: Int,
    val defaultSellPrice: Int,
    val unlockLevel: Int
)

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val avatar: CustomerAvatar,
    val greeting: String
)

@Entity(tableName = "store_upgrades")
data class StoreUpgradeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: UpgradeCategory,
    val cost: Int,
    val unlockLevel: Int,
    val description: String
)

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val narrative: String,
    val type: ChallengeType,
    val targetProductId: String?,
    val targetQuantity: Int,
    val xpReward: Int
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String
)
