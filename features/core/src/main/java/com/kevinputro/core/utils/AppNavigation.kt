package com.kevinputro.core.utils

import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.kevinputro.core.extension.isNotEmptyOrBlank
import javax.inject.Inject

interface AppNavigation {

  /**
   * Will check existing fragment by using
   * [fragmentTag], if found the fragment will be reused and put the arguments of new [fragment]
   * into it while [Fragment.setArguments] is a [Bundle] object
   */
  fun pushReplaceReuseExistingFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment,
    fragmentTag: String? = null
  )

  /**
   * Will [FragmentManager.popBackStack] all fragments
   * then replace current fragment
   */
  fun pushFragmentClearBackStack(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment,
    fragmentTag: String? = null
  )


  /**
   * Push a fragment to [FragmentManager] using [FragmentTransaction.add] if added into backstack previous
   * fragment will be resumed [Fragment.onResume]
   *
   * @param fragmentManager either [AppCompatActivity.getSupportFragmentManager] or [Fragment.getChildFragmentManager]
   * @param containerId view container id usually [FrameLayout]
   * @param fragment that will going to attach
   * @param fragmentTag will add to [FragmentTransaction.addToBackStack] if not empty
   */
  fun pushAddFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment,
    fragmentTag: String? = null
  )

  /**
   * Push a fragment to [FragmentManager] using [FragmentTransaction.replace] if added into backstack previous
   * fragment will be re-created [Fragment.onCreate]
   *
   * @param fragmentManager either [AppCompatActivity.getSupportFragmentManager] or [Fragment.getChildFragmentManager]
   * @param containerId view container id usually [FrameLayout]
   * @param fragment that will going to attach
   * @param fragmentTag will add to [FragmentTransaction.addToBackStack] if not empty
   */
  fun pushReplaceFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment,
    fragmentTag: String? = null
  )

}

internal class AppNavigationImpl @Inject constructor() : AppNavigation {

  override fun pushReplaceReuseExistingFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    fragment: Fragment,
    fragmentTag: String?
  ) = with(fragmentManager) {
    val existingFragment: Fragment? = fragmentTag?.let(::findFragmentByTag)
    commit {
      if (existingFragment != null) {
        remove(existingFragment)
      }
    }
    pushReplaceFragment(fragmentManager, containerId, fragment, fragmentTag)
  }

  override fun pushFragmentClearBackStack(
    fragmentManager: FragmentManager,
    containerId: Int,
    fragment: Fragment,
    fragmentTag: String?
  ) = with(fragmentManager) {
    for (f in 0 until this.backStackEntryCount) {
      this.popBackStack()
    }
    commit {
      replace(containerId, fragment, fragmentTag)
    }
  }

  override fun pushAddFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    fragment: Fragment,
    fragmentTag: String?
  ) = with(fragmentManager) {
    commit {
      setReorderingAllowed(true)
      fragmentTag.takeIf { it.isNotEmptyOrBlank() }?.let { tag ->
        add(containerId, fragment, tag)
        addToBackStack(tag)
      } ?: add(containerId, fragment)
    }
  }

  override fun pushReplaceFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    fragment: Fragment,
    fragmentTag: String?
  ) = with(fragmentManager) {
    commit {
      setReorderingAllowed(true)
      fragmentTag.takeIf { it.isNotEmptyOrBlank() }?.let { tag ->
        replace(containerId, fragment, tag)
        addToBackStack(tag)
      } ?: replace(containerId, fragment)
    }
  }
}
