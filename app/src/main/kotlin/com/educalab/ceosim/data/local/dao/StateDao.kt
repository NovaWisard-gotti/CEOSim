package com.educalab.ceosim.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.educalab.ceosim.data.local.entity.InventoryEntity
import com.educalab.ceosim.data.local.entity.ProductPriceEntity
import com.educalab.ceosim.data.local.entity.ProgressEntity
import com.educalab.ceosim.data.local.entity.StoreEntity
import com.educalab.ceosim.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StateDao {

    // --- UserProfile (singleton) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE id = ${UserProfileEntity.SINGLETON_ID} LIMIT 1")
    fun observeProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = ${UserProfileEntity.SINGLETON_ID} LIMIT 1")
    suspend fun getProfile(): UserProfileEntity?

    // --- Store (singleton) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStore(store: StoreEntity)

    @Query("SELECT * FROM store WHERE id = ${StoreEntity.SINGLETON_ID} LIMIT 1")
    fun observeStore(): Flow<StoreEntity?>

    @Query("SELECT * FROM store WHERE id = ${StoreEntity.SINGLETON_ID} LIMIT 1")
    suspend fun getStore(): StoreEntity?

    // --- Inventory ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertInventory(item: InventoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInventoryIfAbsent(items: List<InventoryEntity>)

    @Query("SELECT * FROM inventory")
    fun observeInventory(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory WHERE productId = :productId LIMIT 1")
    suspend fun getInventoryItem(productId: String): InventoryEntity?

    @Query("SELECT COUNT(*) FROM inventory WHERE quantity > 0")
    suspend fun countDistinctProductsInStock(): Int

    // --- Product prices ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPrice(price: ProductPriceEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPricesIfAbsent(prices: List<ProductPriceEntity>)

    @Query("SELECT * FROM product_prices")
    fun observePrices(): Flow<List<ProductPriceEntity>>

    @Query("SELECT * FROM product_prices WHERE productId = :productId LIMIT 1")
    suspend fun getPrice(productId: String): ProductPriceEntity?

    // --- Progress (singleton) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: ProgressEntity)

    @Query("SELECT * FROM progress WHERE id = ${ProgressEntity.SINGLETON_ID} LIMIT 1")
    fun observeProgress(): Flow<ProgressEntity?>

    @Query("SELECT * FROM progress WHERE id = ${ProgressEntity.SINGLETON_ID} LIMIT 1")
    suspend fun getProgress(): ProgressEntity?
}
