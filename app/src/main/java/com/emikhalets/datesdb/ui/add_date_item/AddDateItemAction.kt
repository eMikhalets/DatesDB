package com.emikhalets.datesdb.ui.add_date_item

import com.emikhalets.datesdb.common.ViewAction
import com.emikhalets.datesdb.model.entities.DateItem

sealed class AddDateItemAction : ViewAction() {
    object NavigateBack : AddDateItemAction()
    object StartGetImageIntent : AddDateItemAction()
    object NavigateToDatePicker : AddDateItemAction()
    object NavigateToTypes : AddDateItemAction()
    data class PressSaveDateItem(val date: DateItem) : AddDateItemAction()
}