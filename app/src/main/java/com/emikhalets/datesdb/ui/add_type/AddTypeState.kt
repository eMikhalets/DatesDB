package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.mvi.MviState

sealed class AddTypeState : MviState() {
    object Added : AddTypeState()
    data class Error(val message: String) : AddTypeState()
}
