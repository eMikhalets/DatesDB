package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState
import com.emikhalets.datesdb.model.entities.DateItem

sealed class DatesListState : MviState() {
    object Loading : DatesListState()
    object ResultEmptyDatesList : DatesListState()
    data class ResultDatesList(val data: List<DateItem>) : DatesListState()
    data class Error(val message: String) : DatesListState()
}

sealed class DatesListAction : MviAction() {
    object GetDatesList : DatesListAction()
}

sealed class DatesListIntent : MviIntent() {
    object LoadDatesList : DatesListIntent()
}