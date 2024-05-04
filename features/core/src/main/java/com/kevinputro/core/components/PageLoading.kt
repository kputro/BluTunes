package com.kevinputro.core.components

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.contains
import androidx.core.view.minusAssign
import androidx.core.view.plusAssign
import androidx.fragment.app.Fragment
import com.kevinputro.blutunes.core.databinding.ViewPageLoadingBinding

/**
 * @param context should be [Activity] instance, if created from fragment use [Fragment.requireActivity]
 **/
class PageLoading @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

  private val binding: ViewPageLoadingBinding
  private val viewGroup: ViewGroup

  init {
    if (context !is Activity) throw IllegalArgumentException(
      "Make sure context is Activity, please read the documentation"
    )
    viewGroup = context.window.decorView.findViewById(android.R.id.content) as ViewGroup
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    binding = ViewPageLoadingBinding.inflate(LayoutInflater.from(context), this, true)
    isClickable = true
    isFocusable = true
    isFocusableInTouchMode = true
  }

  fun show() {
    viewGroup.takeIf { !it.contains(this) }?.let { rootView ->
      rootView += this
      requestFocus()
    }
  }

  fun hide() {
    viewGroup.takeIf { it.contains(this) }?.let { rootView ->
      rootView -= this
    }
  }

}
