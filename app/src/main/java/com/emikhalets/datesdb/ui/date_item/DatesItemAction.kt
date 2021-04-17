package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewAction

sealed class DatesItemAction : ViewAction() {
    data class LoadDateItem(val id: Long) : DatesItemAction()
    data class DeleteDateItem(val id: Long) : DatesItemAction()
}
