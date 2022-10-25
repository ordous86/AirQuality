package com.lucian.airquality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.lucian.airquality.AirRepository.AirData
import com.lucian.airquality.databinding.AirHorizontalItemBinding

/**
 * Adapter for air data in horizontal list.
 */
class AirDataHorizontalAdapter(private val lifecycleOwner: LifecycleOwner): AirDataAdapter() {

    // Create.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirDataViewHolder {
        AirHorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            // initialize binding variables
            it.lifecycleOwner = lifecycleOwner

            // initialize view holder
            return object: AirDataViewHolder(it) {
                override fun setData(data: AirData) {
                    it.airData = data
                    it.executePendingBindings()
                }
            }
        }
    }
}