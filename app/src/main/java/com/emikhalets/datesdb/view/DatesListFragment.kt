package com.emikhalets.datesdb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding
import com.emikhalets.datesdb.viewmodel.DatesListViewModel

class DatesListFragment : Fragment() {

    private var _binding: FragmentDatesListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: DatesListViewModel by viewModels()

    private var datesAdapter: DatesAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            listViewModel.getAllDates()
        }

        datesAdapter = DatesAdapter { onDateClick(it) }
        binding.listDates.setHasFixedSize(true)
        binding.listDates.adapter = datesAdapter

        listViewModel.dates.observe(viewLifecycleOwner, { dates ->
            if (dates.isNotEmpty()) {
                binding.listDates.isVisible = true
                binding.textEmptyDates.isVisible = false
            } else {
                binding.listDates.isVisible = false
                binding.textEmptyDates.isVisible = true
            }
        })

        listViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.fabAddDate.setOnClickListener { onAddClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        datesAdapter = null
        _binding = null
    }

    private fun onDateClick(id: Int) {
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateItem(id)
        binding.root.findNavController().navigate(action)
    }

    private fun onAddClick() {
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateAdd()
        binding.root.findNavController().navigate(action)
    }
}