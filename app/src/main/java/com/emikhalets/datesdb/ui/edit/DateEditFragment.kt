package com.emikhalets.datesdb.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import java.lang.Exception

class DateEditFragment : Fragment() {

    private var _binding: FragmentDateEditBinding? = null
    private val binding get() = _binding!!

    private val editViewModel: DateEditViewModel by viewModels()

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
                if (id >= 0) editViewModel.getDate(id)
            }
        }

        editViewModel.date.observe(viewLifecycleOwner, { dateItem ->
            with(binding) {
                etName.setText(dateItem.name)
                etDate.setText(dateItem.date.toString())
                etType.setText(dateItem.type)
            }
        })

        editViewModel.updating.observe(viewLifecycleOwner, { count ->
            binding.root.findNavController().popBackStack()
        })

        editViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.fabSaveDate.setOnClickListener { onSaveClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSaveClick() {
        val name = binding.etName.text.toString().trim()
        val type = binding.etType.text.toString().trim()
        try {
            val date = binding.etDate.text.toString().toLong()
            editViewModel.update(name, date, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}