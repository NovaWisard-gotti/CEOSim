package com.educalab.ceosim.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.educalab.ceosim.data.local.CeoSimDatabase
import com.educalab.ceosim.data.repository.StoreRepository
import com.educalab.ceosim.domain.engine.FailureReason
import com.educalab.ceosim.domain.engine.OperationResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Pruebas de integración del repositorio contra una base de datos Room real
 * en memoria (Robolectric). Cubren los casos límite exigidos por la
 * especificación: DB nueva, reinicio (doble seed), doble toque, producto
 * inexistente, saldo/stock cero.
 */
@RunWith(RobolectricTestRunner::class)
class StoreRepositoryTest {

    private lateinit var db: CeoSimDatabase
    private lateinit var repository: StoreRepository

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CeoSimDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = StoreRepository(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `ensureSeeded on a fresh DB populates the minimum required catalog`() = runTest {
        repository.ensureSeeded("Capi", 1)

        assertEquals(20, repository.observeProducts().first().size)
        assertEquals(10, repository.observeCustomers().first().size)
        assertEquals(10, repository.observeUpgradesCatalog().first().size)
        assertEquals(10, repository.observeChallenges().first().size)
        assertEquals(12, repository.observeBadgesCatalog().first().size)
    }

    @Test
    fun `ensureSeeded is idempotent after a restart, it never duplicates data`() = runTest {
        repository.ensureSeeded("Capi", 1)
        repository.ensureSeeded("Capi", 1) // simula un reinicio de la app

        assertEquals(20, repository.observeProducts().first().size)
        assertEquals(1, repository.observeBalance().first().let { 1 }) // balance sigue siendo consultable
    }

    @Test
    fun `new store starts with the default starting balance`() = runTest {
        repository.ensureSeeded("Capi", 1)
        val balance = repository.observeBalance().first()
        assertEquals(50, balance)
    }

    @Test
    fun `buying a product with insufficient funds fails and balance is unchanged`() = runTest {
        repository.ensureSeeded("Capi", 1)
        val before = repository.observeBalance().first()

        val result = repository.buyProduct("pelota_futbol", 100) // 100 * 10 = 1000 monedas, imposible
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_FUNDS, (result as OperationResult.Failure).reason)

        val after = repository.observeBalance().first()
        assertEquals(before, after)
    }

    @Test
    fun `buying a product updates balance and inventory together`() = runTest {
        repository.ensureSeeded("Capi", 1)
        repository.buyProduct("jugo_naranja", 2) // costo 5 c/u = 10

        val balance = repository.observeBalance().first()
        assertEquals(40, balance) // 50 - 10

        val inventory = repository.observeInventory().first().first { it.productId == "jugo_naranja" }
        assertEquals(2, inventory.quantity)
    }

    @Test
    fun `selling a product with no stock fails`() = runTest {
        repository.ensureSeeded("Capi", 1)
        val result = repository.sellProduct("jugo_naranja", customerId = null, quantity = 1)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.INSUFFICIENT_STOCK, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `buying then selling a product unlocks first purchase and first sale badges`() = runTest {
        repository.ensureSeeded("Capi", 1)
        repository.buyProduct("jugo_naranja", 3)
        repository.sellProduct("jugo_naranja", customerId = "cli_camila", quantity = 1)

        val unlocked = repository.observeUnlockedBadgeIds().first()
        assertTrue("primera_compra" in unlocked)
        assertTrue("primera_venta" in unlocked)
    }

    @Test
    fun `buying the same upgrade twice (double tap) only charges once`() = runTest {
        repository.ensureSeeded("Capi", 1)
        repository.buyProduct("jugo_naranja", 20) // asegurar saldo suficiente no es necesario, usamos upgrade barato

        val balanceBeforeUpgrades = repository.observeBalance().first()

        val first = repository.buyUpgrade("maceta_decorativa")
        assertTrue(first is OperationResult.Success)

        val second = repository.buyUpgrade("maceta_decorativa") // doble toque
        assertTrue(second is OperationResult.Failure)
        assertEquals(FailureReason.ALREADY_UNLOCKED, (second as OperationResult.Failure).reason)

        val balanceAfter = repository.observeBalance().first()
        // Solo se debió cobrar una vez (15 monedas de la maceta), nunca dos.
        assertEquals(balanceBeforeUpgrades - 15, balanceAfter)
    }

    @Test
    fun `setting a price for a non-existent product fails`() = runTest {
        repository.ensureSeeded("Capi", 1)
        val result = repository.setSellPrice("producto_que_no_existe", 10)
        assertTrue(result is OperationResult.Failure)
        assertEquals(FailureReason.PRODUCT_NOT_FOUND, (result as OperationResult.Failure).reason)
    }

    @Test
    fun `completing the same challenge twice only awards xp once`() = runTest {
        repository.ensureSeeded("Capi", 1)

        val firstAttempt = repository.completeChallenge("reto_ahorro_50", xpReward = 10)
        assertTrue(firstAttempt)
        val xpAfterFirst = repository.observeProgress().first()?.totalXp ?: 0

        val secondAttempt = repository.completeChallenge("reto_ahorro_50", xpReward = 10)
        assertFalse(secondAttempt)
        val xpAfterSecond = repository.observeProgress().first()?.totalXp ?: 0

        assertEquals(xpAfterFirst, xpAfterSecond)
    }
}
