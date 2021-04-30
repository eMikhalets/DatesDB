package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.common.ViewIntent

sealed class DatePickerIntent : ViewIntent() {
    object NavigateBack : DatePickerIntent()
    data class ApplyDate(val timestamp: Long) : DatePickerIntent()
}