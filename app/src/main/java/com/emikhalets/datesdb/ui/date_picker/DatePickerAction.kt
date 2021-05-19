package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.mvi.MviAction

sealed class DatePickerAction : MviAction() {
    object NavigateBack : DatePickerAction()
    data class SetDate(val ts: Long) : DatePickerAction()
}
