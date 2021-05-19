package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.mvi.MviViewModel
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import kotlinx.coroutines.flow.collect

class DateEditViewModel(
        private val repository: RoomRepository
) : MviViewModel<DateEditIntent, DateEditAction, DateEditState>() {

    override fun intentToAction(intent: DateEditIntent): DateEditAction {
        return when (intent) {
            is DateEditIntent.LoadDateItem -> DateEditAction.LoadDateItem(intent.id)
            is DateEditIntent.UpdateDateItem -> DateEditAction.UpdateDateItem(intent.id)
        }
    }

    override fun handleAction(action: DateEditAction) {
        launch {
            _state.postValue(DateEditState.Loading)
            when (action) {
                is DateEditAction.LoadDateItem -> repository.getDateById(action.id).collect {
                    _state.postValue(it.reduce())
                }
                is DateEditAction.UpdateDateItem -> repository.updateDate(action.id).collect {
                    _state.postValue(it.reduce())
                }
            }
        }
    }

    private fun SingleResult<DateItem>.reduce(): DateEditState {
        return when (this) {
            is SingleResult.Loading -> DateEditState.Loading
            is SingleResult.Success -> DateEditState.ResultDateItem(data)
            is SingleResult.Error.EmptyData -> DateEditState.Error("Empty data")
            is SingleResult.Error.DatabaseError -> DateEditState.Error(exception.message.toString())
            else -> DateEditState.Error("Empty data")
        }
    }
}