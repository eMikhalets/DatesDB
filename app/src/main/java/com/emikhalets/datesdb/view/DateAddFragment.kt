package com.emikhalets.datesdb.view

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
import com.emikhalets.datesdb.databinding.FragmentDateAddBinding
import com.emikhalets.datesdb.viewmodel.DateAddViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateAddFragment : Fragment() {

    private var _binding: FragmentDateAddBinding? = null
    private val binding get() = _binding!!

    private val addViewModel: DateAddViewModel by viewModels()
    private var typesAdapter: ArrayAdapter<String>? = null
    private var dateListener: OnDateSetListener? = null

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

        addViewModel.getAllTypes()

        addViewModel.adding.observe(viewLifecycleOwner, { insertedId ->
            binding.root.findNavController().popBackStack()
        })

        addViewModel.types.observe(viewLifecycleOwner, { list ->
            createAdapter()
        })

        addViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        dateListener = OnDateSetListener { _, year, month, dayOfMonth ->
            val new = LocalDateTime.now()
                    .withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(dayOfMonth)
            addViewModel.dateTime = new
            setDate(new)
        }

        binding.tedDate.setOnClickListener { onDateClick() }
        binding.acType.setOnClickListener { onTypeClick() }
        binding.fabSaveDate.setOnClickListener { onSaveClick() }
        binding.cbIsYear.setOnCheckedChangeListener { _, bool -> addViewModel.isYear = bool }
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
                addViewModel.dateTime.year,
                addViewModel.dateTime.monthValue - 1,
                addViewModel.dateTime.dayOfMonth,
        ).show()

        setDate(addViewModel.dateTime)
    }

    private fun setDate(date: LocalDateTime) {
        val dateText = date.format(DateTimeFormatter.ofPattern("d MMM y"))
        binding.tedDate.setText(dateText)
    }

    private fun onTypeClick() {
        createAdapter()
    }

    private fun createAdapter() {
        typesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                addViewModel.typeItems
        )
        binding.acType.setAdapter(typesAdapter)
    }

    private fun onSaveClick() {
        val name = binding.tedName.text.toString().trim()
        val type = binding.acType.text.toString().trim()
        addViewModel.insert(name, type)
    }
}