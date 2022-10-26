package com.lucian.airquality

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Interface for layout binding functions.
 */
interface Bindings

@BindingAdapter("isFocused")
fun bindEditorFocusBehavior(editor: AppCompatEditText, isFocused: Boolean) {
    if (isFocused) {
        editor.hint = editor.context.getString(R.string.air_search_hint)
        editor.requestFocus()
    } else {
        editor.text = null
        editor.hint = editor.context.getString(R.string.air_search_title)
        editor.clearFocus()
    }
}

@BindingAdapter("focusChangeListener")
fun bindEditorFocusListener(editor: AppCompatEditText, listener: View.OnFocusChangeListener) {
    editor.onFocusChangeListener = listener
}

@BindingAdapter("adapter", "layoutManager")
fun bindLayoutManager(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, layoutManager: RecyclerView.LayoutManager) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = layoutManager
}