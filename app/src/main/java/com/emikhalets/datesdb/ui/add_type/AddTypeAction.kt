package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.common.ViewAction

sealed class AddTypeAction : ViewAction() {
    object NavigateBack : AddTypeAction()
    object NavigateToAddType : AddTypeAction()
}