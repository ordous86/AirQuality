package com.lucian.airquality

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lucian.airquality.AirRepository.AirData

/**
 * Adapter for air data.
 */
abstract class AirDataAdapter: ListAdapter<AirData, AirDataViewHolder>(DiffCallback) {
    // Callback for calculating the diff between 2 list items.
    companion object DiffCallback : DiffUtil.ItemCallback<AirData>() {
        override fun areItemsTheSame(oldItem: AirData, newItem: AirData) = (oldItem === newItem)
        override fun areContentsTheSame(oldItem: AirData, newItem: AirData) =
            (oldItem.siteId == newItem.siteId &&
             oldItem.siteName == newItem.siteName &&
             oldItem.county == newItem.county &&
             oldItem.pm25 == newItem.pm25 &&
             oldItem.status == newItem.status)
    }

    // Bind.
    override fun onBindViewHolder(holder: AirDataViewHolder, position: Int) {
        getItem(position)?.also {
            holder.setData(it)
        }
    }

    // Create.
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirDataViewHolder
}