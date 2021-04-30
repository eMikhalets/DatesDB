package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewIntent
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateItemIntent : ViewIntent() {
    object NavigateBack : DateItemIntent()
    data class PressDelete(val date: DateItem) : DateItemIntent()
    data class PressEdit(val date: DateItem) : DateItemIntent()
}
