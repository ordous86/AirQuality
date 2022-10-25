package com.lucian.airquality

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LifecycleOwner
import com.lucian.airquality.AirRepository.AirData
import com.lucian.airquality.databinding.AirVerticalItemBinding

/**
 * Adapter for air data in vertical list.
 */
class AirDataVerticalAdapter(private val lifecycleOwner: LifecycleOwner): AirDataAdapter(), Filterable {

    // Filter.
    override fun getFilter() = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            TODO("Not yet implemented")
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            TODO("Not yet implemented")
        }
    }

    // Create.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirDataViewHolder {
        AirVerticalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
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