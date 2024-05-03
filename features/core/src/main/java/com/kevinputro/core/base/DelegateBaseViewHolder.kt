package com.kevinputro.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem

abstract class DelegateBaseViewHolder<T : DelegateAdapterItem>(view: View) :
  RecyclerView.ViewHolder(view) {
  abstract fun bind(data: T)
}
