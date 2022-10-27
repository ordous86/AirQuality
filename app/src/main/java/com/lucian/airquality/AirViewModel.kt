package com.lucian.airquality

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucian.airquality.AirRepository.AirData

/**
 * ViewModel to connect [MainActivity] and [AirRepository].
 */
class AirViewModel: ViewModel() {
    // Fields.
    private val repository = AirRepository()

    // Fields for live data.
    val hasFilteredData = MutableLiveData<Boolean>().apply { value = false }
    val isFocusEditor = MutableLiveData<Boolean>().apply { value = false }
    val itemClickFlag = MutableLiveData<AirData>()
    val keywords = MutableLiveData<String>().apply { value = "" }
    val queryState = MutableLiveData<QueryState>().apply { value = QueryState.IDLE }

    // Check whether to show a view or not.
    fun isShow(queryState: QueryState, isFocusEditor: Boolean) =
        queryState == QueryState.SUCCESS && !isFocusEditor

    // Check whether to show a view or not.
    fun isShow(queryState: QueryState, isFocusEditor: Boolean, hasFilteredData: Boolean) =
        queryState == QueryState.SUCCESS && !isFocusEditor || hasFilteredData

    // Check whether to show a view or not.
    fun isShow(isFocusEditor: Boolean, hasFilteredData: Boolean) =
        isFocusEditor && !hasFilteredData

    // Called when click item button.
    fun onItemButtonClick(data: AirData) {
        itemClickFlag.value = data
    }

    // Called when click refresh button.
    fun onRefreshButtonClick() {
        queryState.value = QueryState.IDLE
    }

    // Query online data.
    suspend fun queryOnline() = repository.requestOnlineData()

    // Set focus on air search editor.
    fun setFocusEditor(isFocus: Boolean) {
        isFocusEditor.value = isFocus
    }
}