package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.ViewIntent
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateEditIntent : ViewIntent() {
    object NavigateBack : DateEditIntent()
    object PressChangeImage : DateEditIntent()
    object PressChangeDate : DateEditIntent()
    object PressChangeType : DateEditIntent()
    data class PressSaveDateItem(val date: DateItem) : DateEditIntent()
}
