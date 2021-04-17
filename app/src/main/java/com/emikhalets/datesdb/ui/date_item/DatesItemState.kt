package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DatesItemState : ViewState() {
    object Loading : DatesItemState()
    object Deleted : DatesItemState()
    data class ResultDateItem(val data: DateItem) : DatesItemState()
    data class Error(val message: String) : DatesItemState()
}
