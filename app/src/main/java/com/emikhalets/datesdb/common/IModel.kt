package com.emikhalets.datesdb.common

import androidx.lifecycle.LiveData

interface IModel<S, I> {
    val state: LiveData<S>
    fun dispatchIntent(intent: I)
}