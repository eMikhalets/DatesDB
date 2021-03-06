package com.emikhalets.datesdb.ui.add_date_item

import android.net.Uri
import com.emikhalets.datesdb.model.entities.Group
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class AddDateItemState : MviState() {
    object Added : AddDateItemState()
    data class ResultChangeImage(val uri: Uri) : AddDateItemState()
    data class ResultChangeDate(val ts: Long) : AddDateItemState()
    data class ResultChangeGroup(val group: Group) : AddDateItemState()
    data class Error(val message: String) : AddDateItemState()
}

sealed class AddDateItemAction : MviAction() {
    data class AddDateItem(
        val name: String,
        val date: Long,
        val group: String
    ) : AddDateItemAction()
}

sealed class AddDateItemIntent : MviIntent() {
    data class ClickAddDateItem(
        val name: String,
        val date: Long,
        val group: String
    ) : AddDateItemIntent()
}