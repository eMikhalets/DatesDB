package com.emikhalets.datesdb.ui.types

import com.emikhalets.datesdb.mvi.MviState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class TypesState : MviState() {
    data class ResultAllTypes(val data: List<DateItem>) : TypesState()
    data class Error(val message: String) : TypesState()
}
