package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.ViewIntent
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DatesListIntent : ViewIntent() {
    object CreateTypesTable : DatesListIntent()
    object LoadAllDates : DatesListIntent()
    object PressAddDate : DatesListIntent()
    data class SelectDate(val date: DateItem) : DatesListIntent()
}