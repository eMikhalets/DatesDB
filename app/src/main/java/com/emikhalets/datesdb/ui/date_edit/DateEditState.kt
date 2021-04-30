package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateEditState : ViewState() {
    object Loading : DateEditState()
    object Saved : DateEditState()
    data class ResultDateItem(val data: DateItem) : DateEditState()
    data class Error(val message: String) : DateEditState()
}
