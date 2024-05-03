package com.kevinputro.core.utils

import javax.inject.Inject

data class ViewModelUtils @Inject constructor(
  val dispatcherProvider: DispatcherProvider,
  val errorParser: ErrorParser,
)
