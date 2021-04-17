package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.ViewIntent

sealed class DatesListIntent : ViewIntent() {
    object LoadAllDates : DatesListIntent()
}
