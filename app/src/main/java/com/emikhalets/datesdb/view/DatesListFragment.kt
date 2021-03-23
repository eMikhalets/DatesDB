package com.emikhalets.datesdb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding
import com.emikhalets.datesdb.utils.setGone
import com.emikhalets.datesdb.utils.setVisible
import com.emikhalets.datesdb.viewmodel.DatesListViewModel

class DatesListFragment : Fragment() {

    private var _binding: FragmentDatesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DatesListViewModel by viewModels()
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

        datesAdapter = DatesAdapter { onDateClick(it) }
        with(binding) {
            listDates.setHasFixedSize(true)
            listDates.adapter = datesAdapter
        }

        with(viewModel) {
            dates.observe(viewLifecycleOwner) { datesObserve(it) }
            notice.observe(viewLifecycleOwner, { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            })
        }

        binding.fabAddDate.setOnClickListener { onAddClick() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllDates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        datesAdapter = null
        _binding = null
    }

    private fun datesObserve(dates: List<DateItem>) {
        if (dates.isNotEmpty()) {
            datesAdapter?.submitList(dates)
            binding.listDates.setVisible()
            binding.textEmptyDates.setGone()
        } else {
            binding.listDates.setGone()
            binding.textEmptyDates.setVisible()
        }
    }

    private fun onDateClick(id: Int) {
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateItem(id)
        binding.root.findNavController().navigate(action)
    }

    private fun onAddClick() {
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateEdit()
        binding.root.findNavController().navigate(action)
    }
}