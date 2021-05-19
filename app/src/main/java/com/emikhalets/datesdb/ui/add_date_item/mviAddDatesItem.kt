package com.emikhalets.datesdb.ui.add_date_item

import android.net.Uri
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.mvi.MviState

sealed class AddDateItemState : MviState() {
    object Added : AddDateItemState()
    data class ResultChangeImage(val uri: Uri) : AddDateItemState()
    data class ResultChangeDate(val ts: Long) : AddDateItemState()
    data class ResultChangeType(val type: DateType) : AddDateItemState()
    data class Error(val message: String) : AddDateItemState()
}

sealed class AddDateItemAction : MviAction() {
    data class AddDateItem(
        val name: String,
        val date: Long,
        val typeId: Long
    ) : AddDateItemAction()
}

sealed class AddDateItemIntent : MviIntent() {
    data class ClickAddDateItem(
        val name: String,
        val date: Long,
        val typeId: Long
    ) : AddDateItemIntent()
}