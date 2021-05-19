package com.emikhalets.datesdb.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.ui.add_date_item.AddDateItemDialogDirections
import com.emikhalets.datesdb.ui.date_edit.DateEditFragmentDirections
import com.emikhalets.datesdb.ui.date_item.DateItemFragmentDirections
import com.emikhalets.datesdb.ui.dates_list.DatesListFragmentDirections
import com.emikhalets.datesdb.ui.types_list.TypesDialogDirections

fun Fragment.navFromDatesListToDateItem(dateItem: DateItem) {
    val action = DatesListFragmentDirections.actionDatesListToDateItem(dateItem)
    this.findNavController().navigate(action)
}

fun Fragment.navFromDatesListToAddDateItem() {
    val action = DatesListFragmentDirections.actionDatesListToAddDate()
    this.findNavController().navigate(action)
}

fun Fragment.navFromAddDateToDatePicker(ts: Long = 0) {
    val action = AddDateItemDialogDirections.actionAddDateToDatePicker(ts)
    this.findNavController().navigate(action)
}

fun Fragment.navFromAddDateToTypes() {
    val action = AddDateItemDialogDirections.actionAddDateToTypes()
    this.findNavController().navigate(action)
}

fun Fragment.navFromTypesToAddDateType() {
    val action = TypesDialogDirections.actionTypesToAddType()
    this.findNavController().navigate(action)
}

fun Fragment.navFromDateItemToDateEdit(dateItem: DateItem) {
    val action = DateItemFragmentDirections.actionDateItemToDateEdit(dateItem)
    this.findNavController().navigate(action)
}

fun Fragment.navFromDateEditToDatePicker(ts: Long = 0) {
    val action = DateEditFragmentDirections.actionDateEditToDatePicker(ts)
    this.findNavController().navigate(action)
}

fun Fragment.navFromDateEditToTypes() {
    val action = DateEditFragmentDirections.actionDateEditToTypes()
    this.findNavController().navigate(action)
}