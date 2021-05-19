package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class DateItemState : MviState() {
    object Deleted : DateItemState()
    data class DateTypeLoaded(val dateType: DateType) : DateItemState()
    data class Error(val message: String) : DateItemState()
}

sealed class DateItemAction : MviAction() {
    data class GetDateType(val id: Long) : DateItemAction()
    data class DeleteDateItem(val dateItem: DateItem) : DateItemAction()
}

sealed class DateItemIntent : MviIntent() {
    data class LoadDateType(val id: Long) : DateItemIntent()
    data class ClickDeleteDateItem(val dateItem: DateItem) : DateItemIntent()
}