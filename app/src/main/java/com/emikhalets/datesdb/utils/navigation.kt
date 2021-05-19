package com.emikhalets.datesdb.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emikhalets.datesdb.ui.add_date_item.AddDateItemDialogDirections

fun Fragment.navFromAddDateToDatePicker(ts: Long = 0) {
    val action = AddDateItemDialogDirections.actionAddDateToDatePicker(ts)
    this.findNavController().navigate(action)
}

fun Fragment.navFromAddDateToTypes() {
    val action = AddDateItemDialogDirections.actionAddDateToTypes()
    this.findNavController().navigate(action)
}