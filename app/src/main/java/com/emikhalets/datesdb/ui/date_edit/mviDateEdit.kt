package com.emikhalets.datesdb.ui.date_edit

import android.net.Uri
import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class DateEditState : MviState() {
    object Added : DateEditState()
    data class ResultChangeImage(val uri: Uri) : DateEditState()
    data class ResultChangeDate(val ts: Long) : DateEditState()
    data class ResultChangeGroup(val group: Group) : DateEditState()
    data class Error(val message: String) : DateEditState()
}

sealed class DateEditAction : MviAction() {
    data class SaveDateItem(
        val name: String,
        val date: Long,
        val group: String
    ) : DateEditAction()
}

sealed class DateEditIntent : MviIntent() {
    data class ClickSaveDateItem(
        val name: String,
        val date: Long,
        val group: String
    ) : DateEditIntent()
}