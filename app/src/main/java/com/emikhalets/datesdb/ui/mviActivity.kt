package com.emikhalets.datesdb.ui

import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class ActivityState : MviState() {
    object GroupsCreated : ActivityState()
    data class ResultGroupsList(val data: List<Group>) : ActivityState()
    data class Error(val message: String) : ActivityState()
}

sealed class ActivityAction : MviAction() {
    object GetGroups : ActivityAction()
    data class InsertGroups(val names: List<String>) : ActivityAction()
}

sealed class ActivityIntent : MviIntent() {
    object LoadGroupsList : ActivityIntent()
    data class InitGroupsDB(val names: List<String>) : ActivityIntent()
}