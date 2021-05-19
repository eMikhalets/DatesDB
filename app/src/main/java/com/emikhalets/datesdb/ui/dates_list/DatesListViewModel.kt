package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import com.emikhalets.datesdb.mvi.MviViewModel

class DatesListViewModel(
    private val repository: RoomRepository
) : MviViewModel<DatesListIntent, DatesListAction, DatesListState>() {

    override fun intentToAction(intent: DatesListIntent): DatesListAction {
        return when (intent) {
            DatesListIntent.LoadDatesList -> DatesListAction.GetDatesList
        }
    }

    override fun handleAction(action: DatesListAction) {
        launch {
            stateProtected.postValue(DatesListState.Loading)
            when (action) {
                DatesListAction.GetDatesList -> {
                    val result = repository.getAllDates()
                    stateProtected.postValue(result.reduce())
                }
            }
        }
    }

    private fun ListResult<List<DateItem>>.reduce(): DatesListState {
        return when (this) {
            ListResult.EmptyList -> DatesListState.ResultEmptyDatesList
            is ListResult.Success -> DatesListState.ResultDatesList(data)
            is ListResult.Error -> DatesListState.Error(message)
        }
    }
}