package com.kevinputro.core.model

import com.kevinputro.core.delegate.adapter.DelegateAdapterItem
import java.util.UUID

data class WelcomeCard(
  val id: String = UUID.randomUUID().toString()
) : DelegateAdapterItem {
  override fun id() = id

  override fun content() = "WelcomeCard"

  override fun equals(other: DelegateAdapterItem): Boolean = other is WelcomeCard
}
