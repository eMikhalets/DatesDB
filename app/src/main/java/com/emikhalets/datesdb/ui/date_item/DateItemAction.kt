package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewAction

sealed class DateItemAction : ViewAction() {
    data class LoadDateItem(val id: Long) : DateItemAction()
    data class DeleteDateItem(val id: Long) : DateItemAction()
}
