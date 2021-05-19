package com.emikhalets.datesdb.ui.types_list

import com.emikhalets.datesdb.mvi.MviIntent
import com.emikhalets.datesdb.model.entities.Group

sealed class TypesIntent : MviIntent() {
    object PressAddType : TypesIntent()
    object NavigateBack : TypesIntent()
    object LoadData : TypesIntent()
    data class SelectType(val type: Group) : TypesIntent()
}