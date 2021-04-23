package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.common.BaseViewModel
import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import kotlinx.coroutines.flow.collect

class DateItemViewModel(
        private val repository: RoomRepository
) : BaseViewModel<DateItemIntent, DateItemAction, DateItemState>() {

    override fun intentToAction(intent: DateItemIntent): DateItemAction {
        return when (intent) {
            is DateItemIntent.LoadDateItem -> DateItemAction.LoadDateItem(intent.id)
            is DateItemIntent.DeleteDateItem -> DateItemAction.DeleteDateItem(intent.id)
        }
    }

    override fun handleAction(action: DateItemAction) {
        launch {
            _state.postValue(DateItemState.Loading)
            when (action) {
                is DateItemAction.DeleteDateItem -> repository.deleteDate(action.id).collect {
                    _state.postValue(it.reduce())
                }
                is DateItemAction.LoadDateItem -> repository.getDateById(action.id).collect {
                    _state.postValue(it.reduce())
                }
            }
        }
    }

    private fun AppResult<DateItem>.reduce(): DateItemState {
        return when (this) {
            is AppResult.Loading -> DateItemState.Loading
            is AppResult.Success -> DateItemState.ResultDateItem(data)
            is AppResult.Error.EmptyData -> DateItemState.Error("Empty data")
            is AppResult.Error.DatabaseError -> DateItemState.Error(exception.message.toString())
            else -> DateItemState.Error("Empty data")
        }
    }
}