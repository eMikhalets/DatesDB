package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateItemState : ViewState() {
    object Loading : DateItemState()
    object Deleted : DateItemState()
    data class ResultDateItem(val data: DateItem) : DateItemState()
    data class Error(val message: String) : DateItemState()
}
