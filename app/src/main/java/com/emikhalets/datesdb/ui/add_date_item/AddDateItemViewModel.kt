package com.emikhalets.datesdb.ui.add_date_item

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.repositories.RoomRepository
import com.emikhalets.datesdb.mvi.MviViewModel

class AddDateItemViewModel(
    private val repository: RoomRepository
) : MviViewModel<AddDateItemIntent, AddDateItemAction, AddDateItemState>() {

    override fun intentToAction(intent: AddDateItemIntent): AddDateItemAction {
        return when (intent) {
            is AddDateItemIntent.ClickAddDateItem -> AddDateItemAction.AddDateItem(
                intent.name, intent.date, intent.typeId
            )
        }
    }

    override fun handleAction(action: AddDateItemAction) {
        launch {
            when (action) {
                is AddDateItemAction.AddDateItem -> {
                    val dateItem = DateItem(action.name, action.date, action.typeId)
                    val result = repository.insertDate(dateItem)
                    stateProtected.postValue(result.reduce())
                }
            }
        }
    }

    private fun CompleteResult<Nothing>.reduce(): AddDateItemState {
        return when (this) {
            CompleteResult.Complete -> AddDateItemState.Added
            is CompleteResult.Error -> AddDateItemState.Error(message)
        }
    }
}