package com.lucian.airquality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            it.airVerticalAdapter = AirDataVerticalAdapter(this)
            it.lifecycleOwner = this
        }

        // observe query state
        viewModel.queryState.observe(this) {
            // TODO: Handler QueryState.ERROR
            if (it == QueryState.IDLE) {
                queryData()
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
                viewBinding.airVerticalAdapter?.submitList(airBadData)

                // complete
                Log.d(TAG, "queryData() - Success")
                QueryState.SUCCESS
            }
        }
    }
}