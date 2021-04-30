package com.emikhalets.datesdb.ui.add_date_item

import com.emikhalets.datesdb.common.ViewIntent
import com.emikhalets.datesdb.model.entities.DateItem

sealed class AddDateItemIntent : ViewIntent() {
    object NavigateBack : AddDateItemIntent()
    object PressChangeImage : AddDateItemIntent()
    object PressChangeDate : AddDateItemIntent()
    object PressChangeType : AddDateItemIntent()
    data class PressSaveDateItem(val date: DateItem) : AddDateItemIntent()
}
