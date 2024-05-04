package com.kevinputro.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.kevinputro.core.utils.Event
import com.kevinputro.core.utils.EventObserver

fun <T> Fragment.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
  with(viewLifecycleOwner) {
    liveData.observe(this) { it?.let { t -> action(t) } }
  }
}

fun <T> Fragment.observe(liveData: LiveData<Event<T>>, observer: EventObserver<T>) {
  with(viewLifecycleOwner) { liveData.observe(this, observer) }
}
