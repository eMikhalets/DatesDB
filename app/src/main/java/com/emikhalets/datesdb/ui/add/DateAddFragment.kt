package com.emikhalets.datesdb.ui.add

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.databinding.FragmentDateAddBinding
import java.text.SimpleDateFormat
import java.util.*

class DateAddFragment : Fragment() {

    private var _binding: FragmentDateAddBinding? = null
    private val binding get() = _binding!!

    private val addViewModel: DateAddViewModel by viewModels()

    private var selectedDate: Long = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addViewModel.adding.observe(viewLifecycleOwner, { insertedId ->
            binding.root.findNavController().popBackStack()
        })

        addViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.btnDatePicker.setOnClickListener { }
        binding.fabSaveDate.setOnClickListener { onSaveClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSaveClick() {
        val name = binding.etName.text.toString().trim()
        val type = binding.spinnerType.selectedItem.toString()
        addViewModel.insert(name, selectedDate, type)
    }

//    private fun onBtnDatePickerClick() {
//        val current = Calendar.getInstance()
//        DatePickerDialog(requireContext(), dateSetListener,
//                current[Calendar.YEAR],
//                current[Calendar.MONTH],
//                current[Calendar.DAY_OF_MONTH])
//                .show()
//    }

    private fun initPickDate() {
        val current = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
        binding.btnDatePicker.text = dateFormat.format(current.time)
        selectedDate = current.timeInMillis
    }

    private val dateSetListener = OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
        val selected = Calendar.getInstance()
        selected[year, month] = day
        val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
        binding.btnDatePicker.text = dateFormat.format(selected.time)
        selectedDate = selected.timeInMillis
        addViewModel.isDateEntered = true
    }
}