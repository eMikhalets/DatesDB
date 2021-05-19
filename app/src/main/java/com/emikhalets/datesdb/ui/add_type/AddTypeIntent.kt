package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.mvi.MviIntent

sealed class AddTypeIntent : MviIntent() {
    object NavigateBack : AddTypeIntent()
    data class PressAddType(val name: String) : AddTypeIntent()
}
