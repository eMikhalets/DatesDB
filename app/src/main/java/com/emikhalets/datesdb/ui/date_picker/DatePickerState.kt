package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.common.ViewState

sealed class DatePickerState : ViewState() {
    data class ResultDate(val ts: Long) : DatePickerState()
    data class Error(val message: String) : DatePickerState()
}
