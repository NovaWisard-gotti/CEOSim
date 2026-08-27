package com.educalab.ceosim

import android.app.Application
import androidx.room.Room
import com.educalab.ceosim.data.local.CeoSimDatabase
import com.educalab.ceosim.data.repository.StoreRepository

class CeoSimApplication : Application() {

    lateinit var database: CeoSimDatabase
        private set

    lateinit var repository: StoreRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, CeoSimDatabase::class.java, CeoSimDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
        repository = StoreRepository(database)
    }
}
