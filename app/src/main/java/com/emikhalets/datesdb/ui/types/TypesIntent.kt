package com.emikhalets.datesdb.ui.types

import com.emikhalets.datesdb.common.ViewIntent
import com.emikhalets.datesdb.model.entities.DateType

sealed class TypesIntent : ViewIntent() {
    object PressAddType : TypesIntent()
    object NavigateBack : TypesIntent()
    object LoadData : TypesIntent()
    data class SelectType(val type: DateType) : TypesIntent()
}