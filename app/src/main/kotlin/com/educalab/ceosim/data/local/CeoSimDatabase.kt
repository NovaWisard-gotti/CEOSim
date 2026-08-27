package com.educalab.ceosim.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.educalab.ceosim.data.local.dao.CatalogDao
import com.educalab.ceosim.data.local.dao.LogDao
import com.educalab.ceosim.data.local.dao.StateDao
import com.educalab.ceosim.data.local.entity.BadgeEntity
import com.educalab.ceosim.data.local.entity.ChallengeAttemptEntity
import com.educalab.ceosim.data.local.entity.ChallengeEntity
import com.educalab.ceosim.data.local.entity.CustomerEntity
import com.educalab.ceosim.data.local.entity.CustomerRequestEntity
import com.educalab.ceosim.data.local.entity.InventoryEntity
import com.educalab.ceosim.data.local.entity.ProductEntity
import com.educalab.ceosim.data.local.entity.ProductPriceEntity
import com.educalab.ceosim.data.local.entity.ProgressEntity
import com.educalab.ceosim.data.local.entity.PurchaseEntity
import com.educalab.ceosim.data.local.entity.SaleEntity
import com.educalab.ceosim.data.local.entity.StoreEntity
import com.educalab.ceosim.data.local.entity.StoreUpgradeEntity
import com.educalab.ceosim.data.local.entity.TransactionEntity
import com.educalab.ceosim.data.local.entity.UserBadgeEntity
import com.educalab.ceosim.data.local.entity.UserProfileEntity
import com.educalab.ceosim.data.local.entity.UserUpgradeEntity

/**
 * Base de datos Room de CEOSim. 100% local, sin ningún backend.
 * 17 entidades, tal como exige la especificación (sección "BASE DE DATOS").
 */
@Database(
    entities = [
        // Catálogo (semilla)
        ProductEntity::class,
        CustomerEntity::class,
        StoreUpgradeEntity::class,
        ChallengeEntity::class,
        BadgeEntity::class,
        // Estado actual
        UserProfileEntity::class,
        StoreEntity::class,
        InventoryEntity::class,
        ProductPriceEntity::class,
        ProgressEntity::class,
        // Historial
        PurchaseEntity::class,
        SaleEntity::class,
        CustomerRequestEntity::class,
        UserUpgradeEntity::class,
        ChallengeAttemptEntity::class,
        UserBadgeEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CeoSimDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
    abstract fun stateDao(): StateDao
    abstract fun logDao(): LogDao

    companion object {
        const val DATABASE_NAME = "ceosim.db"
    }
}
