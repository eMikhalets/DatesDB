package com.emikhalets.datesdb.ui.types

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.model.entities.DateType

sealed class TypesAction : MviAction() {
    object AddTypeToDatabase : TypesAction()
    object NavigateBack : TypesAction()
    object GetAllTypes : TypesAction()
    data class GetType(val type: DateType) : TypesAction()
}
