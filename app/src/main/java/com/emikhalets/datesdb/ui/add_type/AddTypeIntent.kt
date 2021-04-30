package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.common.ViewIntent

sealed class AddTypeIntent : ViewIntent() {
    object NavigateBack : AddTypeIntent()
    data class PressAddType(val name: String) : AddTypeIntent()
}
