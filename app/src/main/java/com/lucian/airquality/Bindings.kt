package com.lucian.airquality

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Interface for layout binding functions.
 */
interface Bindings

@BindingAdapter("adapter", "layoutManager")
fun bindLayoutManager(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, layoutManager: LinearLayoutManager) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = layoutManager
}