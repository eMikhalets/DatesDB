package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.ViewAction
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DatesListAction : ViewAction() {
    object CreateTypesTable : DatesListAction()
    object NavigateToAddDateItem : DatesListAction()
    object GetAllDateItems : DatesListAction()
    data class GetDateItem(val date: DateItem) : DatesListAction()
}
