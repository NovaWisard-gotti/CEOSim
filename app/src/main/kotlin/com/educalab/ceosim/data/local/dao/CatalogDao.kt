package com.educalab.ceosim.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.ceosim.data.local.entity.BadgeEntity
import com.educalab.ceosim.data.local.entity.ChallengeEntity
import com.educalab.ceosim.data.local.entity.CustomerEntity
import com.educalab.ceosim.data.local.entity.ProductEntity
import com.educalab.ceosim.data.local.entity.StoreUpgradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    // --- Products ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products ORDER BY unlockLevel ASC, name ASC")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getProduct(productId: String): ProductEntity?

    @Query("SELECT COUNT(*) FROM products")
    suspend fun countProducts(): Int

    // --- Customers ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomers(customers: List<CustomerEntity>)

    @Query("SELECT * FROM customers")
    fun observeCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers WHERE id = :customerId LIMIT 1")
    suspend fun getCustomer(customerId: String): CustomerEntity?

    @Query("SELECT COUNT(*) FROM customers")
    suspend fun countCustomers(): Int

    // --- Store upgrades ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpgrades(upgrades: List<StoreUpgradeEntity>)

    @Query("SELECT * FROM store_upgrades ORDER BY unlockLevel ASC, cost ASC")
    fun observeUpgrades(): Flow<List<StoreUpgradeEntity>>

    @Query("SELECT COUNT(*) FROM store_upgrades")
    suspend fun countUpgrades(): Int

    // --- Challenges ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChallenges(challenges: List<ChallengeEntity>)

    @Query("SELECT * FROM challenges")
    fun observeChallenges(): Flow<List<ChallengeEntity>>

    @Query("SELECT COUNT(*) FROM challenges")
    suspend fun countChallenges(): Int

    // --- Badges ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Query("SELECT * FROM badges")
    fun observeBadges(): Flow<List<BadgeEntity>>

    @Query("SELECT COUNT(*) FROM badges")
    suspend fun countBadges(): Int
}
