package com.kevinputro.core.delegate

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.kevinputro.core.extension.logDebug
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(
  noinline factory: ((LayoutInflater) -> T)
) = FragmentViewBindingDelegate( this, factory)

class FragmentViewBindingDelegate<T : ViewBinding>(
  private val fragment: Fragment,
  private val factory: ((LayoutInflater) -> T)
) : ReadOnlyProperty<Fragment, T> {

  private var binding: T? = null

  init {
    fragment.lifecycle.addObserver(object : LifecycleEventObserver {
      val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
        val viewLifecycleOwner = it ?: return@Observer

        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
          override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) binding = null
          }
        })
      }

      override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
          Lifecycle.Event.ON_CREATE -> fragment.viewLifecycleOwnerLiveData.observeForever(
            viewLifecycleOwnerLiveDataObserver
          )
          Lifecycle.Event.ON_DESTROY -> fragment.viewLifecycleOwnerLiveData.removeObserver(
            viewLifecycleOwnerLiveDataObserver
          )
          else -> logDebug("Unhandled event ${event.name}")
        }
      }
    })
  }

  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    binding?.let { return it }
    val lifecycle = fragment.viewLifecycleOwner.lifecycle

    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
    }

    return factory.invoke(fragment.layoutInflater).also { this.binding = it }
  }
}
