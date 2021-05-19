package com.emikhalets.datesdb.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class MviViewModel<I : MviIntent, A : MviAction, S : MviState> : ViewModel() {

    abstract fun intentToAction(intent: I): A
    abstract fun handleAction(action: A)

    protected val stateProtected = MutableLiveData<S>()
    val state: LiveData<S> get() = stateProtected

    fun dispatchIntent(intent: I) {
        handleAction(intentToAction(intent))
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }
}