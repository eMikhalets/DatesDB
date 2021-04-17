package com.emikhalets.datesdb.ui.dates_list

import com.emikhalets.datesdb.common.BaseViewModel
import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import kotlinx.coroutines.flow.collect

class DatesListViewModel(
        private val repository: RoomRepository
) : BaseViewModel<DatesListIntent, DatesListAction, DatesListState>() {

    override fun intentToAction(intent: DatesListIntent): DatesListAction {
        return when (intent) {
            is DatesListIntent.LoadAllDates -> DatesListAction.AllDates
        }
    }

    override fun handleAction(action: DatesListAction) {
        launch {
            _state.postValue(DatesListState.Loading)
            when (action) {
                is DatesListAction.AllDates -> repository.getAllDates().collect {
                    _state.postValue(it.reduce())
                }
            }
        }
    }

    private fun AppResult<List<DateItem>>.reduce(): DatesListState {
        return when (this) {
            is AppResult.Loading -> DatesListState.Loading
            is AppResult.Success -> DatesListState.ResultAllDates(data)
            is AppResult.Error.EmptyData -> DatesListState.Error("Empty data")
            is AppResult.Error.DatabaseError -> DatesListState.Error(exception.message.toString())
            else -> DatesListState.Error("Empty data")
        }
    }
}