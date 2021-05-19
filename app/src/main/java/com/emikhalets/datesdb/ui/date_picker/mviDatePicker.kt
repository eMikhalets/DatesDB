package com.emikhalets.datesdb.ui.date_picker

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class DatePickerState : MviState() {
    data class ResultDate(val ts: Long) : DatePickerState()
    data class Error(val message: String) : DatePickerState()
}

sealed class DatePickerAction : MviAction() {
    data class SetDate(val ts: Long) : DatePickerAction()
}


sealed class DatePickerIntent : MviIntent() {
    data class ClickApply(val ts: Long) : DatePickerIntent()
}