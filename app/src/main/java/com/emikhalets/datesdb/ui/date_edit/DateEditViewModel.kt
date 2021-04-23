package com.emikhalets.datesdb.ui.date_edit

import com.emikhalets.datesdb.common.BaseViewModel
import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import kotlinx.coroutines.flow.collect

class DateEditViewModel(
        private val repository: RoomRepository
) : BaseViewModel<DateEditIntent, DateEditAction, DateEditState>() {

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

    private fun AppResult<DateItem>.reduce(): DateEditState {
        return when (this) {
            is AppResult.Loading -> DateEditState.Loading
            is AppResult.Success -> DateEditState.ResultDateItem(data)
            is AppResult.Error.EmptyData -> DateEditState.Error("Empty data")
            is AppResult.Error.DatabaseError -> DateEditState.Error(exception.message.toString())
            else -> DateEditState.Error("Empty data")
        }
    }
}