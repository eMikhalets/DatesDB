package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewIntent

sealed class DateItemIntent : ViewIntent() {
    data class LoadDateItem(val id: Long) : DateItemIntent()
    data class DeleteDateItem(val id: Long) : DateItemIntent()
}
