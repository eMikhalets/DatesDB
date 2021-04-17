package com.emikhalets.datesdb.common

interface IViewRenderer<S> {
    fun render(state: S)
}