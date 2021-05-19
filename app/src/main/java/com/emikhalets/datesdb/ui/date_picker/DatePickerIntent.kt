package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.mvi.MviIntent

sealed class DatePickerIntent : MviIntent() {
    object NavigateBack : DatePickerIntent()
    data class ApplyDate(val timestamp: Long) : DatePickerIntent()
}