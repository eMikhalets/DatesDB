package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.common.ViewAction

sealed class DatePickerAction : ViewAction() {
    object NavigateBack : DatePickerAction()
    data class SetDate(val ts: Long) : DatePickerAction()
}
