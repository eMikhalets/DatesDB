package com.emikhalets.datesdb.ui.types

import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class TypesState : ViewState() {
    data class ResultAllTypes(val data: List<DateItem>) : TypesState()
    data class Error(val message: String) : TypesState()
}
