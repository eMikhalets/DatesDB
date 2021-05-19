package com.emikhalets.datesdb.ui.add_group

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class AddGroupState : MviState() {
    object Added : AddGroupState()
    data class Error(val message: String) : AddGroupState()
}

sealed class AddGroupAction : MviAction() {
    data class AddGroup(val name: String) : AddGroupAction()
}

sealed class AddGroupIntent : MviIntent() {
    data class ClickAddGroup(val name: String) : AddGroupIntent()
}