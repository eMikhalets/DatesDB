package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.ViewIntent

sealed class DatesItemIntent : ViewIntent() {
    data class LoadDateItem(val id: Long) : DatesItemIntent()
    data class DeleteDateItem(val id: Long) : DatesItemIntent()
}
