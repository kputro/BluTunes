package com.kevinputro.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kevinputro.blutunes.core.databinding.LayoutItemWelcomeCardBinding
import com.kevinputro.core.base.DelegateBaseViewHolder
import com.kevinputro.core.delegate.adapter.DelegateAdapter
import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import com.kevinputro.core.model.WelcomeCard

class WelcomeCardAdapter :
  DelegateAdapter<WelcomeCard, WelcomeCardAdapter.WelcomeCardViewHolder>(WelcomeCard::class.java) {

  override fun createViewHolder(parent: ViewGroup) = WelcomeCardViewHolder(
    LayoutItemWelcomeCardBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
  )

  override fun bindViewHolder(
    model: WelcomeCard,
    viewHolder: WelcomeCardViewHolder,
    payloads: List<DelegateAdapterItem.Payloadable>
  ) {
    viewHolder.bind(model)
  }

  inner class WelcomeCardViewHolder(
    binding: LayoutItemWelcomeCardBinding
  ) : DelegateBaseViewHolder<WelcomeCard>(binding.root) {
    override fun bind(data: WelcomeCard) {
      // not used at the moment..
    }
  }
}
