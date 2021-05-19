package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DateEditAction : MviAction() {
    object NavigateBack : DateEditAction()
    object StartGetImageIntent : DateEditAction()
    object NavigateToDatePicker : DateEditAction()
    object NavigateToTypes : DateEditAction()
    data class PressSaveDateItem(val date: DateItem) : DateEditAction()
}