package com.lucian.airquality

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucian.airquality.AirRepository.AirData
import com.lucian.airquality.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Activity to show air quality.
 */
class MainActivity: AppCompatActivity() {
    // Fields.
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler() - An exception occurs: ", throwable.fillInStackTrace())
        viewModel.queryState.value = QueryState.ERROR
    }
    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: AirViewModel by lazy {
        ViewModelProvider(this, AirViewModelFactory())[AirViewModel::class.java]
    }

    // Create.
    override fun onCreate(savedInstanceState: Bundle?) {
        // call super
        super.onCreate(savedInstanceState)

        // initialize binding
        viewBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.airHorizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            it.airVerticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.airHorizontalAdapter = AirDataHorizontalAdapter(this)
            it.airVerticalAdapter = AirDataVerticalAdapter(this, viewModel)
            it.focusChangeListener = OnFocusChangeListener { _, hasFocus -> viewModel.isFocusEditor.value = hasFocus }
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        // observe query state
        viewModel.queryState.observe(this) {
            // TODO: Handler QueryState.ERROR
            if (it == QueryState.IDLE) {
                queryData()
            }
        }

        // observe data change
        viewModel.keywords.observe(this) {
            viewBinding.airVerticalAdapter?.filter?.filter(it)
        }

        // observe data click event
        viewModel.itemClickFlag.observe(this) {
            val message = getString(R.string.item_button_click_message,
                it.siteId,
                it.siteName,
                it.county,
                it.pm25,
                it.status)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        // observe editor focus state
        viewModel.isFocusEditor.observe(this) { isFocus ->
            getSystemService(Context.INPUT_METHOD_SERVICE).apply {
                this as InputMethodManager
                if (isFocus) {
                    showSoftInput(viewBinding.airSearchEditor, 0)
                } else {
                    hideSoftInputFromWindow(viewBinding.airSearchEditor.windowToken, 0)
                }
            }
        }
    }

    // Query data.
    private fun queryData() {
        viewModel.viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            // check query state
            viewModel.queryState.value = QueryState.RUNNING

            // start query
            val totalData = viewModel.queryOnline().records

            // check query result
            viewModel.queryState.value = if (totalData.isEmpty()) {
                Log.e(TAG, "queryData() - Empty response")
                QueryState.ERROR
            } else {
                // check result data
                val airGoodData = mutableListOf<AirData>()
                val airBadData = mutableListOf<AirData>()
                for (data in totalData) {
                    val pm25 = data.pm25.let {
                        if (it.isEmpty()) {
                            0
                        } else {
                            it.toInt()
                        }
                    }
                    if (pm25 > PM_25_THRESHOLD) {
                        airBadData.add(data)
                    } else {
                        airGoodData.add(data)
                    }
                }

                // submit data to adapter
                viewBinding.airHorizontalAdapter?.submitList(airGoodData)
                viewBinding.airVerticalAdapter?.submitList(airBadData, totalData)

                // complete
                Log.d(TAG, "queryData() - Success")
                QueryState.SUCCESS
            }
        }
    }
}