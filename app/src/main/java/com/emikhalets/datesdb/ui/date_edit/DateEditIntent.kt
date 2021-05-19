package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateEditIntent : MviIntent() {
    object NavigateBack : DateEditIntent()
    object PressChangeImage : DateEditIntent()
    object PressChangeDate : DateEditIntent()
    object PressChangeType : DateEditIntent()
    data class PressSaveDateItem(val date: DateItem) : DateEditIntent()
}
