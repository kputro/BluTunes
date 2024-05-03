package com.kevinputro.musicplayer

import android.os.Bundle

sealed class Pages(val id: Int, var args: Bundle? = null) {
  object Main : Pages(0)

  object Search : Pages(1)

  object Detail : Pages(2)
}
