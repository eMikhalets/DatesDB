package com.emikhalets.datesdb.ui

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.model.repositories.RoomRepository
import com.emikhalets.datesdb.mvi.MviViewModel

class ActivityViewModel(
    private val repository: RoomRepository
) : MviViewModel<ActivityIntent, ActivityAction, ActivityState>() {

    override fun intentToAction(intent: ActivityIntent): ActivityAction {
        return when (intent) {
            ActivityIntent.LoadGroupsList -> ActivityAction.GetGroups
            is ActivityIntent.InitGroupsDB -> ActivityAction.InsertGroups(intent.names)
        }
    }

    override fun handleAction(action: ActivityAction) {
        launch {
            when (action) {
                ActivityAction.GetGroups -> {
                    val result = repository.getAllGroups()
                    stateProtected.postValue(result.reduce())
                }
                is ActivityAction.InsertGroups -> {
                    val list = mutableListOf<Group>()
                    action.names.forEach { list.add(Group(it)) }
                    val result = repository.insertGroups(list)
                    stateProtected.postValue(result.reduce())
                }
            }
        }
    }

    private fun ListResult<List<Group>>.reduce(): ActivityState {
        return when (this) {
            is ListResult.Success -> ActivityState.ResultGroupsList(data)
            ListResult.EmptyList -> ActivityState.Error("empty")
            is ListResult.Error -> ActivityState.Error(message)
        }
    }

    private fun CompleteResult<Nothing>.reduce(): ActivityState {
        return when (this) {
            CompleteResult.Complete -> ActivityState.GroupsCreated
            is CompleteResult.Error -> ActivityState.Error(message)
        }
    }
}