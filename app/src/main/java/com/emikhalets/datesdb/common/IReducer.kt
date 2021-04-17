package com.emikhalets.datesdb.common

interface IReducer<S, T : Any> {
    fun reduce(result: Result<T>, state: S): S
}