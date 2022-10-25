package com.lucian.airquality

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.lucian.airquality.AirRepository.AirData

/**
 * View holder for [AirDataAdapter] list item.
 */
abstract class AirDataViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
    // Set data to list item.
    abstract fun setData(data: AirData)
}