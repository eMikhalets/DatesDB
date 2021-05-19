package com.emikhalets.datesdb.ui.groups_list

import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class GroupsListState : MviState() {
    data class ResultGroupsList(val data: List<Group>) : GroupsListState()
    data class Error(val message: String) : GroupsListState()
}

sealed class GroupsListAction : MviAction() {
    object GetGroups : GroupsListAction()
}

sealed class GroupsListIntent : MviIntent() {
    object LoadGroupsList : GroupsListIntent()
}