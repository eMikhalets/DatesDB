package com.emikhalets.datesdb.ui.edit

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateEditFragment : Fragment() {

    private var _binding: FragmentDateEditBinding? = null
    private val binding get() = _binding!!

    private val editViewModel: DateEditViewModel by viewModels()
    private var typesAdapter: ArrayAdapter<String>? = null
    private var dateListener: OnDateSetListener? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            arguments?.let {
                val id = it.getInt("") ?: -1
                if (id >= 0) {
                    editViewModel.getDate(id)
                    editViewModel.getAllTypes()
                }
            }
        }

        editViewModel.date.observe(viewLifecycleOwner, { dateItem ->
            with(binding) {
                tedDate.setText(dateItem.name)
                tedDate.setText(dateItem.date.toString())
                acType.setText(dateItem.type)
            }
        })

        editViewModel.updating.observe(viewLifecycleOwner, { count ->
            binding.root.findNavController().popBackStack()
        })

        editViewModel.types.observe(viewLifecycleOwner, { list ->
            createAdapter()
        })

        editViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        dateListener = OnDateSetListener { _, year, month, dayOfMonth ->
            val new = LocalDateTime.now()
                    .withYear(year)
                    .withMonth(month)
                    .withDayOfMonth(dayOfMonth)
            editViewModel.dateTime = new
            setDate(new)
        }

        binding.tedDate.setOnClickListener { onDateClick() }
        binding.acType.setOnClickListener { onTypeClick() }
        binding.fabSaveDate.setOnClickListener { onSaveClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        typesAdapter = null
        dateListener = null
        _binding = null
    }

    private fun onDateClick() {
        DatePickerDialog(
                requireContext(), dateListener,
                editViewModel.dateTime.year,
                editViewModel.dateTime.monthValue - 1,
                editViewModel.dateTime.dayOfMonth,
        ).show()

        setDate(editViewModel.dateTime)
    }

    private fun setDate(date: LocalDateTime) {
        val dateText = date.format(DateTimeFormatter.ofPattern("d E MMM"))
        binding.tedDate.setText(dateText)
    }

    private fun onTypeClick() {
        createAdapter()
    }

    private fun createAdapter() {
        typesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                editViewModel.typeItems
        )
        binding.acType.setAdapter(typesAdapter)
    }

    private fun onSaveClick() {
        val name = binding.tedName.text.toString().trim()
        val type = binding.acType.text.toString().trim()
        val date = editViewModel.dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        editViewModel.update(name, date, type)
    }
}