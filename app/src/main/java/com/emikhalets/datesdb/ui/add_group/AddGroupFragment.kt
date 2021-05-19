package com.emikhalets.datesdb.ui.add_group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.emikhalets.datesdb.databinding.DialogAddTypeBinding
import com.emikhalets.datesdb.ui.date_edit.DateEditViewModel

class AddGroupFragment : DialogFragment() {

    private var _binding: DialogAddTypeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DateEditViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insertingType.observe(viewLifecycleOwner, { dismiss() })
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            val name = binding.etTypeName.text.toString().trim()
            if (name.isNotEmpty()) viewModel.insertType(name)
        }
    }
}