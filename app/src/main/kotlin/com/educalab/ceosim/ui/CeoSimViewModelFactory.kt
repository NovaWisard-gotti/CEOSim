package com.educalab.ceosim.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.educalab.ceosim.data.repository.StoreRepository

class CeoSimViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CeoSimViewModel::class.java)) {
            return CeoSimViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
