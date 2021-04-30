package com.emikhalets.datesdb.ui.add_date_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.databinding.DialogAddTypeBinding
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding
import com.emikhalets.datesdb.ui.dates_list.DatesAdapter
import com.emikhalets.datesdb.ui.dates_list.DatesListViewModel

class AddDateItemDialog : DialogFragment() {

    private val binding by viewBinding(FragmentDatesListBinding::bind)
    private lateinit var datesAdapter: DatesAdapter
    override val viewModel: DatesListViewModel by viewModels()

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