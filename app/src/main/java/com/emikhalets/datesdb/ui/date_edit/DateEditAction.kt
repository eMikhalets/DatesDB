package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.ViewAction

sealed class DateEditAction : ViewAction() {
    object DateItem : DateEditAction()
}
