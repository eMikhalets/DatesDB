package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewAction
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateItemAction : ViewAction() {
    object NavigateBack : DateItemAction()
    object NavigateToEditDateItem : DateItemAction()
    data class DeleteDateItem(val date: DateItem) : DateItemAction()
}
