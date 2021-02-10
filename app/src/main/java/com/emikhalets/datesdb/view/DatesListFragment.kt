package com.emikhalets.datesdb.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding
import com.emikhalets.datesdb.utils.Tags.SP_KEY_TYPES_DB_EXS
import com.emikhalets.datesdb.utils.Tags.SP_NAME
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

        if (savedInstanceState == null) checkTypes()

        datesAdapter = DatesAdapter { onDateClick(it) }
        binding.listDates.setHasFixedSize(true)
        binding.listDates.adapter = datesAdapter

        listViewModel.dates.observe(viewLifecycleOwner, { dates ->
            Log.d("TAG", "dates.observe: ${dates.size}")
            dates.forEach {
                Log.d("TAG", "dates.observe: ${it.id} ${it.name}")
            }
            if (dates.isNotEmpty()) {
                datesAdapter?.submitList(dates)
                binding.listDates.isVisible = true
                binding.textEmptyDates.isVisible = false
            } else {
                binding.listDates.isVisible = false
                binding.textEmptyDates.isVisible = true
            }
        })

        listViewModel.defTypesCreating.observe(viewLifecycleOwner, {
            val sp = requireActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE).edit()
            sp.putBoolean(SP_KEY_TYPES_DB_EXS, true).apply()
        })

        listViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.fabAddDate.setOnClickListener { onAddClick() }
    }

    override fun onResume() {
        super.onResume()
        datesAdapter?.submitList(null)
        listViewModel.getAllDates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        datesAdapter = null
        _binding = null
    }

    private fun onDateClick(id: Int) {
        Log.d("TAG", "onAddClick: $id")
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateItem(id)
        binding.root.findNavController().navigate(action)
    }

    private fun onAddClick() {
        val action = DatesListFragmentDirections.actionFragmentDatesListToFragmentDateEdit()
        binding.root.findNavController().navigate(action)
    }

    private fun checkTypes() {
        val sp = requireActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE)
        val isTypesDbExist = sp.getBoolean(SP_KEY_TYPES_DB_EXS, false)
        if (!isTypesDbExist) {
            listViewModel.createDefaultTypesTable(
                    getString(R.string.def_type_birthday),
                    R.string.def_type_birthday,
                    getString(R.string.def_type_holiday),
                    R.string.def_type_holiday,
                    getString(R.string.def_type_anniversary),
                    R.string.def_type_anniversary,
            )
        }
    }
}