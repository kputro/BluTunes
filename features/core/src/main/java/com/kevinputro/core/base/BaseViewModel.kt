package com.kevinputro.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevinputro.core.extension.asLiveData
import com.kevinputro.core.utils.ErrorModel
import com.kevinputro.core.utils.Event
import com.kevinputro.core.utils.ViewModelUtils
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel(
  private val utils: ViewModelUtils
) : ViewModel() {

  protected val dispatcher get() = utils.dispatcherProvider
  protected val errorParser get() = utils.errorParser

  protected val _loading = MutableLiveData<Event<Boolean>>()
  val loading = _loading.asLiveData()

  protected val _errorLiveData = MutableLiveData<Event<ErrorModel>>()
  val errorLiveData = _errorLiveData.asLiveData()

  protected val exceptionHandler = CoroutineExceptionHandler() { _, throwable ->
    onException(throwable)
  }

  protected fun onException(throwable: Throwable) {
    //log for debugging
    throwable.printStackTrace()

    _loading.postValue(Event(false))

    errorParser.parseError(throwable).let {
      _errorLiveData.postValue(Event(it))
    }
  }
}
