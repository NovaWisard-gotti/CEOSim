package com.educalab.ceosim.data.repository

import androidx.room.withTransaction
import com.educalab.ceosim.data.local.CeoSimDatabase
import com.educalab.ceosim.data.local.SeedData
import com.educalab.ceosim.data.local.entity.CustomerRequestEntity
import com.educalab.ceosim.data.local.entity.InventoryEntity
import com.educalab.ceosim.data.local.entity.ProductPriceEntity
import com.educalab.ceosim.data.local.entity.ProgressEntity
import com.educalab.ceosim.data.local.entity.PurchaseEntity
import com.educalab.ceosim.data.local.entity.SaleEntity
import com.educalab.ceosim.data.local.entity.StoreEntity
import com.educalab.ceosim.data.local.entity.TransactionEntity
import com.educalab.ceosim.data.local.entity.TransactionType
import com.educalab.ceosim.data.local.entity.UserBadgeEntity
import com.educalab.ceosim.data.local.entity.UserProfileEntity
import com.educalab.ceosim.data.local.entity.UserUpgradeEntity
import com.educalab.ceosim.data.local.entity.ChallengeAttemptEntity
import com.educalab.ceosim.domain.engine.FailureReason
import com.educalab.ceosim.domain.engine.MoneyEngine
import com.educalab.ceosim.domain.engine.OperationResult
import com.educalab.ceosim.domain.engine.PriceEngine
import com.educalab.ceosim.domain.engine.ProgressEngine
import com.educalab.ceosim.domain.engine.PurchaseEngine
import com.educalab.ceosim.domain.engine.RewardEngine
import com.educalab.ceosim.domain.engine.SalesEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Repositorio único que orquesta los motores de dominio (puros, sin Android)
 * con la persistencia real en Room. Toda regla de negocio pasa primero por
 * un motor de domain/engine; el repositorio nunca modifica saldo o stock
 * "a mano".
 */
class StoreRepository(private val db: CeoSimDatabase) {

    private val catalogDao = db.catalogDao()
    private val stateDao = db.stateDao()
    private val logDao = db.logDao()

    // ---------------------------------------------------------------
    // Inicialización / datos semilla
    // ---------------------------------------------------------------

    suspend fun ensureSeeded(defaultAlias: String, defaultAvatarId: Int) {
        db.withTransaction {
            if (catalogDao.countProducts() == 0) catalogDao.insertProducts(SeedData.products)
            if (catalogDao.countCustomers() == 0) catalogDao.insertCustomers(SeedData.customers)
            if (catalogDao.countUpgrades() == 0) catalogDao.insertUpgrades(SeedData.upgrades)
            if (catalogDao.countChallenges() == 0) catalogDao.insertChallenges(SeedData.challenges)
            if (catalogDao.countBadges() == 0) catalogDao.insertBadges(SeedData.badges)

            if (stateDao.getStore() == null) {
                stateDao.upsertStore(
                    StoreEntity(storeName = "Mi Pequeña Tienda", balance = MoneyEngine.STARTING_BALANCE, maxBalanceReached = MoneyEngine.STARTING_BALANCE)
                )
            }
            if (stateDao.getProfile() == null) {
                stateDao.upsertProfile(
                    UserProfileEntity(
                        alias = defaultAlias,
                        avatarId = defaultAvatarId,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }
            if (stateDao.getProgress() == null) {
                stateDao.upsertProgress(ProgressEntity(totalXp = 0, level = 1, updatedAt = System.currentTimeMillis()))
            }

            val inventoryDefaults = SeedData.products.map { InventoryEntity(it.id, quantity = 0) }
            stateDao.insertInventoryIfAbsent(inventoryDefaults)

            val priceDefaults = SeedData.products.map { ProductPriceEntity(it.id, sellPrice = it.defaultSellPrice) }
            stateDao.insertPricesIfAbsent(priceDefaults)
        }
    }

    // ---------------------------------------------------------------
    // Observación de estado (para la UI, vía StateFlow en el ViewModel)
    // ---------------------------------------------------------------

    fun observeBalance(): Flow<Int> = stateDao.observeStore().map { it?.balance ?: 0 }
    fun observeProfile() = stateDao.observeProfile()
    fun observeProducts() = catalogDao.observeProducts()
    fun observeInventory() = stateDao.observeInventory()
    fun observePrices() = stateDao.observePrices()
    fun observeCustomers() = catalogDao.observeCustomers()
    fun observeUpgradesCatalog() = catalogDao.observeUpgrades()
    fun observePurchasedUpgradeIds() = logDao.observePurchasedUpgradeIds()
    fun observeChallenges() = catalogDao.observeChallenges()
    fun observeCompletedChallenges() = logDao.observeCompletedChallenges()
    fun observeBadgesCatalog() = catalogDao.observeBadges()
    fun observeUnlockedBadgeIds() = logDao.observeUnlockedBadgeIds()
    fun observeProgress() = stateDao.observeProgress()
    fun observeRecentTransactions(limit: Int = 50) = logDao.observeRecentTransactions(limit)
    fun observeSales() = logDao.observeSales()
    fun observePurchases() = logDao.observePurchases()

    // ---------------------------------------------------------------
    // Módulo 1 — Almacén: comprar productos
    // ---------------------------------------------------------------

    suspend fun buyProduct(productId: String, quantity: Int): OperationResult<PurchaseEngine.PurchaseOutcome> {
        val product = catalogDao.getProduct(productId)
            ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)
        val store = stateDao.getStore() ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)
        val inventoryItem = stateDao.getInventoryItem(productId)
        val currentStock = inventoryItem?.quantity ?: 0

        val result = PurchaseEngine.purchase(
            currentBalance = store.balance,
            currentStock = currentStock,
            unitCost = product.buyCost,
            quantity = quantity
        )

        if (result is OperationResult.Success) {
            val outcome = result.value
            db.withTransaction {
                stateDao.upsertStore(store.copy(balance = outcome.newBalance))
                stateDao.upsertInventory(InventoryEntity(productId, outcome.newStock))
                logDao.insertPurchase(
                    PurchaseEntity(
                        productId = productId,
                        quantity = outcome.quantityBought,
                        unitCost = product.buyCost,
                        totalCost = outcome.totalCost,
                        timestamp = System.currentTimeMillis()
                    )
                )
                logDao.insertTransaction(
                    TransactionEntity(
                        type = TransactionType.COMPRA,
                        amount = -outcome.totalCost,
                        balanceAfter = outcome.newBalance,
                        description = "Compra: ${product.name} x${outcome.quantityBought}",
                        timestamp = System.currentTimeMillis()
                    )
                )
                addXp(ProgressEngine.XP_PER_PURCHASE)
                refreshBadges()
            }
        }
        return result
    }

    // ---------------------------------------------------------------
    // Módulo 3 — Precios
    // ---------------------------------------------------------------

    suspend fun setSellPrice(productId: String, newPrice: Int): OperationResult<Int> {
        val product = catalogDao.getProduct(productId)
            ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)
        val validation = PriceEngine.validateSellPrice(product.buyCost, newPrice)
        if (validation is OperationResult.Success) {
            stateDao.upsertPrice(ProductPriceEntity(productId, newPrice))
        }
        return validation
    }

    // ---------------------------------------------------------------
    // Módulo 4 — Mostrador: vender productos a clientes
    // ---------------------------------------------------------------

    suspend fun sellProduct(productId: String, customerId: String?, quantity: Int = 1): OperationResult<SalesEngine.SaleOutcome> {
        val store = stateDao.getStore() ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)
        val inventoryItem = stateDao.getInventoryItem(productId)
        val currentStock = inventoryItem?.quantity ?: 0
        val price = stateDao.getPrice(productId)?.sellPrice
            ?: catalogDao.getProduct(productId)?.defaultSellPrice
            ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)

        val result = SalesEngine.sell(
            currentBalance = store.balance,
            currentStock = currentStock,
            sellPrice = price,
            quantity = quantity
        )

        if (result is OperationResult.Success) {
            val outcome = result.value
            db.withTransaction {
                val newMax = maxOf(store.maxBalanceReached, outcome.newBalance)
                stateDao.upsertStore(store.copy(balance = outcome.newBalance, maxBalanceReached = newMax))
                stateDao.upsertInventory(InventoryEntity(productId, outcome.newStock))
                logDao.insertSale(
                    SaleEntity(
                        productId = productId,
                        customerId = customerId,
                        quantity = outcome.quantitySold,
                        unitPrice = price,
                        totalEarned = outcome.earned,
                        timestamp = System.currentTimeMillis()
                    )
                )
                if (customerId != null) {
                    logDao.insertCustomerRequest(
                        CustomerRequestEntity(
                            customerId = customerId,
                            productId = productId,
                            quantity = outcome.quantitySold,
                            fulfilled = true,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
                logDao.insertTransaction(
                    TransactionEntity(
                        type = TransactionType.VENTA,
                        amount = outcome.earned,
                        balanceAfter = outcome.newBalance,
                        description = "Venta x${outcome.quantitySold}",
                        timestamp = System.currentTimeMillis()
                    )
                )
                addXp(ProgressEngine.XP_PER_SALE)
                refreshBadges()
            }
        }
        return result
    }

    // ---------------------------------------------------------------
    // Módulo 6 — Mejoras de la tienda
    // ---------------------------------------------------------------

    suspend fun buyUpgrade(upgradeId: String): OperationResult<Int> {
        if (logDao.isUpgradeOwned(upgradeId)) {
            return OperationResult.Failure(FailureReason.ALREADY_UNLOCKED)
        }
        val upgrade = catalogDao.observeUpgrades().first().find { it.id == upgradeId }
            ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)
        val store = stateDao.getStore() ?: return OperationResult.Failure(FailureReason.PRODUCT_NOT_FOUND)

        val spendResult = MoneyEngine.spend(store.balance, upgrade.cost)
        if (spendResult is OperationResult.Success) {
            val newBalance = spendResult.value
            db.withTransaction {
                stateDao.upsertStore(store.copy(balance = newBalance))
                logDao.insertUserUpgrade(UserUpgradeEntity(upgradeId = upgradeId, purchasedAt = System.currentTimeMillis()))
                logDao.insertTransaction(
                    TransactionEntity(
                        type = TransactionType.MEJORA,
                        amount = -upgrade.cost,
                        balanceAfter = newBalance,
                        description = "Mejora: ${upgrade.name}",
                        timestamp = System.currentTimeMillis()
                    )
                )
                addXp(ProgressEngine.XP_PER_UPGRADE)
                refreshBadges()
            }
        }
        return spendResult
    }

    // ---------------------------------------------------------------
    // Módulo 7 — Retos
    // ---------------------------------------------------------------

    suspend fun completeChallenge(challengeId: String, xpReward: Int): Boolean {
        if (logDao.isChallengeCompleted(challengeId)) return false
        db.withTransaction {
            logDao.insertChallengeAttempt(
                ChallengeAttemptEntity(challengeId = challengeId, completed = true, completedAt = System.currentTimeMillis())
            )
            addXp(xpReward)
            refreshBadges()
        }
        return true
    }

    // ---------------------------------------------------------------
    // Perfil
    // ---------------------------------------------------------------

    suspend fun updateProfile(alias: String, avatarId: Int) {
        val current = stateDao.getProfile() ?: return
        stateDao.upsertProfile(current.copy(alias = alias, avatarId = avatarId))
    }

    suspend fun completeOnboarding() {
        val current = stateDao.getProfile() ?: return
        stateDao.upsertProfile(current.copy(onboardingCompleted = true))
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        val current = stateDao.getProfile() ?: return
        stateDao.upsertProfile(current.copy(soundEnabled = enabled))
    }

    suspend fun setHapticEnabled(enabled: Boolean) {
        val current = stateDao.getProfile() ?: return
        stateDao.upsertProfile(current.copy(hapticEnabled = enabled))
    }

    // ---------------------------------------------------------------
    // Internos: progreso e insignias (deben llamarse dentro de una transacción)
    // ---------------------------------------------------------------

    private suspend fun addXp(amount: Int) {
        val progress = stateDao.getProgress() ?: ProgressEntity(totalXp = 0, level = 1, updatedAt = System.currentTimeMillis())
        val newXp = progress.totalXp + amount
        val newLevel = ProgressEngine.levelForXp(newXp)
        stateDao.upsertProgress(progress.copy(totalXp = newXp, level = newLevel, updatedAt = System.currentTimeMillis()))
    }

    private suspend fun refreshBadges() {
        val store = stateDao.getStore() ?: return
        val stats = RewardEngine.ShopStats(
            totalPurchases = logDao.countPurchases(),
            totalSales = logDao.countSales(),
            totalUpgrades = logDao.countUpgradesPurchased(),
            totalChallengesCompleted = logDao.countCompletedChallenges(),
            distinctProductsInStock = stateDao.countDistinctProductsInStock(),
            maxBalanceReached = store.maxBalanceReached
        )
        val alreadyUnlocked = logDao.getUnlockedBadgeIdsOnce().toSet()
        val newUnlocks = RewardEngine.evaluateNewUnlocks(stats, alreadyUnlocked)
        newUnlocks.forEach { badgeId ->
            logDao.insertUserBadge(UserBadgeEntity(badgeId = badgeId, unlockedAt = System.currentTimeMillis()))
        }
    }
}
