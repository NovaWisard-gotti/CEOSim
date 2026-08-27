package com.educalab.ceosim.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.ceosim.data.local.SeedData
import com.educalab.ceosim.data.local.entity.CustomerEntity
import com.educalab.ceosim.data.local.entity.ProductEntity
import com.educalab.ceosim.data.repository.StoreRepository
import com.educalab.ceosim.domain.engine.OperationResult
import com.educalab.ceosim.domain.engine.ProgressEngine
import com.educalab.ceosim.domain.model.UpgradeCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

// --- Modelos de UI (independientes de Room, listos para Compose) ---

data class ShelfProduct(val product: ProductEntity, val quantity: Int, val sellPrice: Int)

data class BadgeUi(val id: String, val title: String, val description: String, val unlocked: Boolean)

data class UpgradeUi(
    val id: String,
    val name: String,
    val description: String,
    val cost: Int,
    val category: UpgradeCategory,
    val owned: Boolean,
    val unlockLevel: Int
)

data class ChallengeUi(
    val id: String,
    val title: String,
    val narrative: String,
    val xpReward: Int,
    val completed: Boolean,
    val targetProductId: String?,
    val targetQuantity: Int
)

data class HeaderState(
    val alias: String = "",
    val avatarId: Int = 1,
    val onboardingCompleted: Boolean = false,
    val balance: Int = 0,
    val level: Int = 1,
    val xpIntoLevel: Int = 0,
    val xpForNextLevel: Int? = null,
    val progressFraction: Float = 0f
)

/**
 * ViewModel único de CEOSim. Expone varios StateFlow independientes en
 * lugar de un solo estado gigante: cada pantalla observa solo lo que
 * necesita, lo que simplifica la recomposición y las pruebas.
 */
class CeoSimViewModel(private val repository: StoreRepository) : ViewModel() {

    private val defaultAlias = listOf("Capi", "Estrellita", "Zorrito", "Vale", "Tino").random()
    private val defaultAvatar = Random.nextInt(1, 9)

    init {
        viewModelScope.launch {
            repository.ensureSeeded(defaultAlias, defaultAvatar)
        }
    }

    val header: StateFlow<HeaderState> = combine(
        repository.observeProfile(),
        repository.observeBalance(),
        repository.observeProgress()
    ) { profile, balance, progress ->
        val levelInfo = ProgressEngine.computeLevelInfo(progress?.totalXp ?: 0)
        HeaderState(
            alias = profile?.alias ?: defaultAlias,
            avatarId = profile?.avatarId ?: defaultAvatar,
            onboardingCompleted = profile?.onboardingCompleted ?: false,
            balance = balance,
            level = levelInfo.level,
            xpIntoLevel = levelInfo.xpIntoLevel,
            xpForNextLevel = levelInfo.xpForNextLevel,
            progressFraction = levelInfo.progressFraction
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HeaderState())

    val shelves: StateFlow<List<ShelfProduct>> = combine(
        repository.observeProducts(),
        repository.observeInventory(),
        repository.observePrices()
    ) { products, inventory, prices ->
        val inventoryMap = inventory.associateBy { it.productId }
        val priceMap = prices.associateBy { it.productId }
        products.map { product ->
            ShelfProduct(
                product = product,
                quantity = inventoryMap[product.id]?.quantity ?: 0,
                sellPrice = priceMap[product.id]?.sellPrice ?: product.defaultSellPrice
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val badges: StateFlow<List<BadgeUi>> = combine(
        repository.observeBadgesCatalog(),
        repository.observeUnlockedBadgeIds()
    ) { catalog, unlockedIds ->
        val unlockedSet = unlockedIds.toSet()
        catalog.map { BadgeUi(it.id, it.title, it.description, it.id in unlockedSet) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val upgrades: StateFlow<List<UpgradeUi>> = combine(
        repository.observeUpgradesCatalog(),
        repository.observePurchasedUpgradeIds()
    ) { catalog, ownedIds ->
        val ownedSet = ownedIds.toSet()
        catalog.map { UpgradeUi(it.id, it.name, it.description, it.cost, it.category, it.id in ownedSet, it.unlockLevel) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val challenges: StateFlow<List<ChallengeUi>> = combine(
        repository.observeChallenges(),
        repository.observeCompletedChallenges()
    ) { catalog, completedAttempts ->
        val completedIds = completedAttempts.map { it.challengeId }.toSet()
        catalog.map { ChallengeUi(it.id, it.title, it.narrative, it.xpReward, it.id in completedIds, it.targetProductId, it.targetQuantity) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customers: StateFlow<List<CustomerEntity>> = repository.observeCustomers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentTransactions = repository.observeRecentTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _nicoMessage = MutableStateFlow(SeedData.nicoSituations.first { it.id == "bienvenida" }.message)
    val nicoMessage: StateFlow<String> = _nicoMessage

    /**
     * Se vuelve true solo cuando ya sabemos con certeza si el perfil existe
     * (datos semilla listos). Evita que la pantalla de inicio "parpadee"
     * entre onboarding y tienda mientras la base de datos carga.
     */
    val isReady: StateFlow<Boolean> = repository.observeProfile()
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // --- Acciones del jugador ---

    fun buyProduct(productId: String, quantity: Int) {
        viewModelScope.launch {
            val result = repository.buyProduct(productId, quantity)
            say(if (result is OperationResult.Success) "primera_compra" else "saldo_insuficiente")
        }
    }

    fun sellProduct(productId: String, customerId: String?) {
        viewModelScope.launch {
            val result = repository.sellProduct(productId, customerId)
            say(if (result is OperationResult.Success) "primera_venta" else "venta_sin_stock")
        }
    }

    fun setSellPrice(productId: String, price: Int) {
        viewModelScope.launch {
            val result = repository.setSellPrice(productId, price)
            if (result is OperationResult.Success) say("precio_con_ganancia")
        }
    }

    fun buyUpgrade(upgradeId: String) {
        viewModelScope.launch {
            val result = repository.buyUpgrade(upgradeId)
            say(if (result is OperationResult.Success) "mejora_aplicada" else "saldo_insuficiente")
        }
    }

    fun completeChallenge(challengeId: String, xpReward: Int) {
        viewModelScope.launch {
            val done = repository.completeChallenge(challengeId, xpReward)
            if (done) say("reto_completado")
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch { repository.completeOnboarding() }
    }

    fun updateProfile(alias: String, avatarId: Int) {
        viewModelScope.launch { repository.updateProfile(alias, avatarId) }
    }

    private fun say(situationId: String) {
        _nicoMessage.value = SeedData.nicoSituations.find { it.id == situationId }?.message
            ?: _nicoMessage.value
    }
}
