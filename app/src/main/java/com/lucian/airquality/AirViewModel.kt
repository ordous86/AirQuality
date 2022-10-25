package com.lucian.airquality

import androidx.lifecycle.ViewModel

/**
 * ViewModel to connect [MainActivity] and [AirRepository].
 */
class AirViewModel: ViewModel() {
    // Fields.
    private val repository = AirRepository()

    // Query online data.
    fun queryOnline() = repository.requestOnlineData()
}