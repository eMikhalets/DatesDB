package com.emikhalets.datesdb.ui.add_type

import com.emikhalets.datesdb.mvi.MviAction

sealed class AddTypeAction : MviAction() {
    object NavigateBack : AddTypeAction()
    object NavigateToAddType : AddTypeAction()
}