package com.emikhalets.datesdb.ui.types_list

import com.emikhalets.datesdb.mvi.MviAction
import com.emikhalets.datesdb.model.entities.Group

sealed class TypesAction : MviAction() {
    object AddTypeToDatabase : TypesAction()
    object NavigateBack : TypesAction()
    object GetAllTypes : TypesAction()
    data class GetType(val type: Group) : TypesAction()
}
