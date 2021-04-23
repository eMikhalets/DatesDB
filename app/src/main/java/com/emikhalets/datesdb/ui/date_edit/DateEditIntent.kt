package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.ViewIntent

sealed class DateEditIntent : ViewIntent() {
    data class LoadDateItem(val id: Long) : DateEditIntent()
    data class UpdateDateItem(val id: Long) : DateEditIntent()
}
