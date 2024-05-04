package com.kevinputro.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kevinputro.core.components.PageLoading

abstract class BaseFragment<B : ViewBinding> : Fragment() {

  protected abstract val binding: B

  private lateinit var pageLoading: PageLoading

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    pageLoading = PageLoading(requireActivity())
  }

  /**
   * UI blocking progress indicator
   */
  fun showLoading(isShown: Boolean) {
    if (isShown) pageLoading.show()
    else pageLoading.hide()
  }

  fun showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, length).show()
  }
}
