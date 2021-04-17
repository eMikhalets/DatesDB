package com.emikhalets.datesdb.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<I : ViewIntent, A : ViewAction, S : ViewState> :
        ViewModel(), IModel<S, I> {

    abstract fun intentToAction(intent: I): A
    abstract fun handleAction(action: A)

    protected val _state = MutableLiveData<S>()
    override val state: LiveData<S> get() = _state

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    final override fun dispatchIntent(intent: I) {
        handleAction(intentToAction(intent))
    }
}