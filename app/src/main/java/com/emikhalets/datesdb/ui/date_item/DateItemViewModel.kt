package com.emikhalets.datesdb.ui.date_item

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.model.repositories.RoomRepository
import com.emikhalets.datesdb.mvi.MviViewModel

class DateItemViewModel(
    private val repository: RoomRepository
) : MviViewModel<DateItemIntent, DateItemAction, DateItemState>() {

    override fun intentToAction(intent: DateItemIntent): DateItemAction {
        return when (intent) {
            is DateItemIntent.LoadDateType -> DateItemAction.GetDateType(intent.id)
            is DateItemIntent.ClickDeleteDateItem -> DateItemAction.DeleteDateItem(intent.dateItem)
        }
    }

    override fun handleAction(action: DateItemAction) {
        launch {
            when (action) {
                is DateItemAction.GetDateType -> {
                    val result = repository.getTypeById(action.id)
                    stateProtected.postValue(result.reduce())
                }
                is DateItemAction.DeleteDateItem -> {
                    val result = repository.deleteDate(action.dateItem)
                    stateProtected.postValue(result.reduce())
                }
            }
        }
    }

    private fun SingleResult<Group>.reduce(): DateItemState {
        return when (this) {
            is SingleResult.Success -> DateItemState.DateTypeLoaded(data)
            is SingleResult.Error -> DateItemState.Error(message)
        }
    }

    private fun CompleteResult<DateItem>.reduce(): DateItemState {
        return when (this) {
            CompleteResult.Complete -> DateItemState.Deleted
            is CompleteResult.Error -> DateItemState.Error(message)
        }
    }
}