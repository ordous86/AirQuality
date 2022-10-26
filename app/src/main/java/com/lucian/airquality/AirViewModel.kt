package com.lucian.airquality

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel to connect [MainActivity] and [AirRepository].
 */
class AirViewModel: ViewModel() {
    // Fields.
    private val repository = AirRepository()

    // Fields for live data.
    val queryState = MutableLiveData<QueryState>().apply { value = QueryState.IDLE }

    // Query online data.
    suspend fun queryOnline() = repository.requestOnlineData()
}