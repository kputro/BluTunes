package com.kevinputro.core.delegate.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CompositeAdapter private constructor(
  private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>>
) : ListAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>(DelegateAdapterItemDiffCallback()) {

  override fun getItemViewType(position: Int): Int {
    for (i in 0 until delegates.size()) {
      if (delegates[i].modelClass == getItem(position).javaClass) {
        return delegates.keyAt(i)
      }
    }
    throw NullPointerException("Cannot get viewType for position $position")
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
    delegates[viewType].createViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
    onBindViewHolder(holder, position, mutableListOf())

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int,
    payloads: MutableList<Any>
  ) {
    val delegateAdapter = delegates[getItemViewType(position)]

    if (delegateAdapter != null) {
      val delegatePayloads = payloads.map { it as DelegateAdapterItem.Payloadable }
      delegateAdapter.bindViewHolder(getItem(position), holder, delegatePayloads)
    } else {
      throw NullPointerException("Cannot find adapter for position $position")
    }
  }

  fun getDelegateAdapterItem(position: Int): DelegateAdapterItem  {
    return super.getItem(position)
  }

  override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    delegates[holder.itemViewType].onViewRecycled(holder)
    super.onViewRecycled(holder)
  }

  override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
    delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
    super.onViewDetachedFromWindow(holder)
  }

  override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
    delegates[holder.itemViewType].onViewAttachedToWindow(holder)
    super.onViewAttachedToWindow(holder)
  }

  class Builder {
    private var count: Int = 0
    private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>> =
      SparseArray()

    fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, *>): Builder {
      @Suppress("UNCHECKED_CAST")
      delegates.put(
        count++,
        delegateAdapter as DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>
      )
      return this
    }

    fun build(): CompositeAdapter {
      require(delegates.isNotEmpty()) { "Register at least one adapter" }
      return CompositeAdapter(delegates)
    }
  }
}
