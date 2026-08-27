package com.educalab.ceosim.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.educalab.ceosim.data.local.entity.ChallengeAttemptEntity
import com.educalab.ceosim.data.local.entity.CustomerRequestEntity
import com.educalab.ceosim.data.local.entity.PurchaseEntity
import com.educalab.ceosim.data.local.entity.SaleEntity
import com.educalab.ceosim.data.local.entity.TransactionEntity
import com.educalab.ceosim.data.local.entity.UserBadgeEntity
import com.educalab.ceosim.data.local.entity.UserUpgradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    // --- Purchases ---
    @Insert
    suspend fun insertPurchase(purchase: PurchaseEntity): Long

    @Query("SELECT * FROM purchases ORDER BY timestamp DESC")
    fun observePurchases(): Flow<List<PurchaseEntity>>

    @Query("SELECT COUNT(*) FROM purchases")
    suspend fun countPurchases(): Int

    // --- Sales ---
    @Insert
    suspend fun insertSale(sale: SaleEntity): Long

    @Query("SELECT * FROM sales ORDER BY timestamp DESC")
    fun observeSales(): Flow<List<SaleEntity>>

    @Query("SELECT COUNT(*) FROM sales")
    suspend fun countSales(): Int

    // --- Customer requests ---
    @Insert
    suspend fun insertCustomerRequest(request: CustomerRequestEntity): Long

    @Query("SELECT * FROM customer_requests ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestCustomerRequest(): CustomerRequestEntity?

    // --- Upgrades purchased by the user ---
    @Insert
    suspend fun insertUserUpgrade(userUpgrade: UserUpgradeEntity): Long

    @Query("SELECT upgradeId FROM user_upgrades")
    fun observePurchasedUpgradeIds(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM user_upgrades")
    suspend fun countUpgradesPurchased(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM user_upgrades WHERE upgradeId = :upgradeId)")
    suspend fun isUpgradeOwned(upgradeId: String): Boolean

    // --- Challenge attempts ---
    @Insert
    suspend fun insertChallengeAttempt(attempt: ChallengeAttemptEntity): Long

    @Query("SELECT * FROM challenge_attempts WHERE completed = 1")
    fun observeCompletedChallenges(): Flow<List<ChallengeAttemptEntity>>

    @Query("SELECT COUNT(*) FROM challenge_attempts WHERE completed = 1")
    suspend fun countCompletedChallenges(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM challenge_attempts WHERE challengeId = :challengeId AND completed = 1)")
    suspend fun isChallengeCompleted(challengeId: String): Boolean

    // --- User badges ---
    @Insert
    suspend fun insertUserBadge(userBadge: UserBadgeEntity): Long

    @Query("SELECT badgeId FROM user_badges")
    fun observeUnlockedBadgeIds(): Flow<List<String>>

    @Query("SELECT badgeId FROM user_badges")
    suspend fun getUnlockedBadgeIdsOnce(): List<String>

    // --- Transaction ledger (La Caja) ---
    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT :limit")
    fun observeRecentTransactions(limit: Int = 50): Flow<List<TransactionEntity>>
}
