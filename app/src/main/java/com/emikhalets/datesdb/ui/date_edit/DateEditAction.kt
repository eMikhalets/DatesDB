package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.ViewAction

sealed class DateEditAction : ViewAction() {
    data class LoadDateItem(val id: Long) : DateEditAction()
    data class UpdateDateItem(val id: Long) : DateEditAction()
}
