package com.lucian.airquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * Factory to build [AirViewModel].
 */
@Suppress("UNCHECKED_CAST")
class AirViewModelFactory: ViewModelProvider.Factory {
    // Create view model.
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        // create
        modelClass.isAssignableFrom(AirViewModel::class.java) -> AirViewModel() as T

        // otherwise
        else -> throw IllegalArgumentException("Unknown ViewModel")
    }
}