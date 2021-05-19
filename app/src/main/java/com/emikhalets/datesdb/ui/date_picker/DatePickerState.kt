package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.mvi.MviState

sealed class DatePickerState : MviState() {
    data class ResultDate(val ts: Long) : DatePickerState()
    data class Error(val message: String) : DatePickerState()
}
