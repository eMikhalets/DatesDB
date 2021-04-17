package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.ViewAction

sealed class DatesListAction : ViewAction() {
    object AllDates : DatesListAction()
}
