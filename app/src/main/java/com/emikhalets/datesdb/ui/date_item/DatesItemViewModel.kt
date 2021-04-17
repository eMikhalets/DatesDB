package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.BaseViewModel
import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import kotlinx.coroutines.flow.collect

class DatesItemViewModel(
        private val repository: RoomRepository
) : BaseViewModel<DatesItemIntent, DatesItemAction, DatesItemState>() {

    override fun intentToAction(intent: DatesItemIntent): DatesItemAction {
        return when (intent) {
            is DatesItemIntent.LoadDateItem -> DatesItemAction.LoadDateItem(intent.id)
            is DatesItemIntent.DeleteDateItem -> DatesItemAction.DeleteDateItem(intent.id)
        }
    }

    override fun handleAction(action: DatesItemAction) {
        launch {
            _state.postValue(DatesItemState.Loading)
            when (action) {
                is DatesItemAction.LoadDateItem -> repository.getDateById(action.id).collect {
                    _state.postValue(it.reduce())
                }
                is DatesItemAction.DeleteDateItem -> repository.deleteDate(action.id).collect {
                    _state.postValue(it.reduce())
                }
            }
        }
    }

    private fun AppResult<DateItem>.reduce(): DatesItemState {
        return when (this) {
            is AppResult.Loading -> DatesItemState.Loading
            is AppResult.Complete -> DatesItemState.Deleted
            is AppResult.Success -> DatesItemState.ResultDateItem(data)
            is AppResult.Error.EmptyData -> DatesItemState.Error("Empty data")
            is AppResult.Error.DatabaseError -> DatesItemState.Error(exception.message.toString())
        }
    }
}