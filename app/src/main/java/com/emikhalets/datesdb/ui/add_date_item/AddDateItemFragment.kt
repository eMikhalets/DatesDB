package com.emikhalets.datesdb.ui.add_date_item

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.emikhalets.datesdb.databinding.FragmentAddDateItemBinding
import com.emikhalets.datesdb.utils.navFromAddDateToDatePicker
import com.emikhalets.datesdb.utils.navFromAddDateToTypes

class AddDateItemFragment : DialogFragment() {

    private var _binding: FragmentAddDateItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddDateItemViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentAddDateItemBinding.inflate(layoutInflater)
        viewModel.state.observe(viewLifecycleOwner, { state ->
            fetchState(state)
        })

        binding.apply {
            imageAvatar.setOnClickListener {
                // TODO: register take image result intent
            }
            layoutDate.setOnClickListener {
                navFromAddDateToDatePicker()
            }
            layoutType.setOnClickListener {
                navFromAddDateToTypes()
            }
            btnAdd.setOnClickListener {
                // TODO: get date ts and type id
                viewModel.dispatchIntent(
                    AddDateItemIntent.ClickAddDateItem(
                        binding.inputName.text.toString(),
                        0,
                        0
                    )
                )
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO: set data when fetching states
    private fun fetchState(state: AddDateItemState) {
        when (state) {
            AddDateItemState.Added -> {
                binding.textError.visibility = View.GONE
            }
            is AddDateItemState.ResultChangeImage -> {
                binding.textError.visibility = View.GONE
            }
            is AddDateItemState.ResultChangeDate -> {
                binding.textError.visibility = View.GONE
            }
            is AddDateItemState.ResultChangeGroup -> {
                binding.textError.visibility = View.GONE
            }
            is AddDateItemState.Error -> {
                binding.textError.text = state.message
                binding.textError.visibility = View.VISIBLE
            }
        }
    }
}