package com.emikhalets.datesdb.ui.add_date_item

import android.net.Uri
import com.emikhalets.datesdb.common.ViewState
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType

sealed class AddDateItemState : ViewState() {
    object Saved : AddDateItemState()
    data class ResultChangedImage(val uri: Uri) : AddDateItemState()
    data class ResultChangedDate(val ts: Long) : AddDateItemState()
    data class ResultChangedType(val type: DateType) : AddDateItemState()
    data class ResultDateItem(val data: DateItem) : AddDateItemState()
    data class Error(val message: String) : AddDateItemState()
}
