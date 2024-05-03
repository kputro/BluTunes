package com.kevinputro.core.delegate.adapter

interface DelegateAdapterItem {
  fun id(): Any

  fun content(): Any

  fun equals(other: DelegateAdapterItem): Boolean

  fun payload(other: Any): Payloadable = Payloadable.None

  /**
   * Simple marker interface for payloads
   */
  interface Payloadable {
    object None: Payloadable
  }
}
