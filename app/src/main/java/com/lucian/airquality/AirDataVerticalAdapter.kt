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
class AirDataVerticalAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: AirViewModel): AirDataAdapter(), Filterable {

    // Fields.
    private val defaultDataset = mutableListOf<AirData>()
    private val filteredDataset = mutableListOf<AirData>()
    private val totalDataset = mutableListOf<AirData>()

    // Filter.
    override fun getFilter() = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            // check keywords
            val keywords = constraint?.toString() ?: ""

            // check filter results
            val results = if (keywords.isEmpty()) {
                defaultDataset
            } else {
                filteredDataset.apply {
                    clear()
                    for (data in totalDataset) {
                        if (data.siteName.contains(keywords) || data.county.contains(keywords)) {
                            add(data)
                        }
                    }
                }
            }

            // complete
            return FilterResults().apply { values = results }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.values.let {
                if (it == null) {
                    defaultDataset
                } else {
                    it as List<AirData>
                }
            }.let {
                viewModel.hasFilteredData.value = it.isNotEmpty() && constraint?.toString()?.isNotEmpty() ?: false
                submitList(it)
            }
            notifyDataSetChanged()
        }
    }

    // Create.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirDataViewHolder {
        AirVerticalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            // initialize binding variables
            it.viewModel = viewModel
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

    // Submit data for recycler view.
    fun submitList(defaultData: List<AirData>, totalData: List<AirData>) {
        defaultDataset.clear()
        defaultDataset.addAll(defaultData)
        totalDataset.clear()
        totalDataset.addAll(totalData)
        submitList(defaultData)
    }
}