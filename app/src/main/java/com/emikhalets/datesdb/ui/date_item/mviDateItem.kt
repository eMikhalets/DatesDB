package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class DateItemState : MviState() {
    object Deleted : DateItemState()
    data class Error(val message: String) : DateItemState()
}

sealed class DateItemAction : MviAction() {
    data class DeleteDateItem(val dateItem: DateItem) : DateItemAction()
}

sealed class DateItemIntent : MviIntent() {
    data class ClickDeleteDateItem(val dateItem: DateItem) : DateItemIntent()
}