package com.lucian.airquality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucian.airquality.databinding.ActivityMainBinding

/**
 * Activity to show air quality.
 */
class MainActivity: AppCompatActivity() {
    // Fields.
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

        // start query
        viewModel.queryOnline().also {
            viewBinding.airHorizontalAdapter?.submitList(it)
            viewBinding.airVerticalAdapter?.submitList(it)
        }
    }
}