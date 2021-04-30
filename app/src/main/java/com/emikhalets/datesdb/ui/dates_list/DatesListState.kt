package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DatesListState : ViewState() {
    object Loading : DatesListState()
    data class ResultAllDates(val data: List<DateItem>) : DatesListState()
    data class Error(val message: String) : DatesListState()
}