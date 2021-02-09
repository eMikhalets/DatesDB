package com.emikhalets.datesdb.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.emikhalets.datesdb.databinding.DialogAddTypeBinding
import com.emikhalets.datesdb.viewmodel.DateAddViewModel

class AddTypeDialog : DialogFragment() {

    private var _binding: DialogAddTypeBinding? = null
    private val binding get() = _binding!!

    private val addViewModel: DateAddViewModel by activityViewModels()

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
        Log.d("TAG", "AddTypeDialog: $addViewModel")

        addViewModel.typeAdding.observe(viewLifecycleOwner, { type ->
            dismiss()
        })

        binding.btnAdd.setOnClickListener { onAddClick() }
        binding.btnCancel.setOnClickListener { onCancelClick() }
    }

    private fun onAddClick() {
        val name = binding.etTypeName.text.toString().trim()
        if (name.isNotEmpty()) addViewModel.insertType(name)
    }

    private fun onCancelClick() {
        dismiss()
    }
}