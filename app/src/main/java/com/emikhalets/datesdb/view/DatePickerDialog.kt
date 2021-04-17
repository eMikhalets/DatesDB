package com.emikhalets.datesdb.view

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.emikhalets.datesdb.utils.setLocalDateTime
import com.emikhalets.datesdb.ui.date_edit.DateEditViewModel
import java.time.LocalDateTime

class DatePickerDialog : DialogFragment() {

    private val viewModel: DateEditViewModel by activityViewModels()

    private val datePickerListener = OnDateSetListener { _, year, month, dayOfMonth ->
        viewModel.setDateString(setLocalDateTime(dayOfMonth, month, year))
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val now = LocalDateTime.now()
        return DatePickerDialog(
                requireContext(),
                datePickerListener,
                now.year,
                now.monthValue - 1,
                now.dayOfMonth,
        )
    }
}