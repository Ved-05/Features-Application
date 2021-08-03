package com.esper.devices.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esper.devices.domain.Repository

class FeatureViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FeatureViewModel::class.java)){
            return FeatureViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}