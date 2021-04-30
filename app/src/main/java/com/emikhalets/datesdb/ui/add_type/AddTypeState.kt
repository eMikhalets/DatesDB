package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.common.ViewState

sealed class AddTypeState : ViewState() {
    object Added : AddTypeState()
    data class Error(val message: String) : AddTypeState()
}
