package com.emikhalets.datesdb.ui.date_edit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.common.BaseFragment
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.ui.dates_list.DatesListIntent
import com.emikhalets.datesdb.view.DateEditFragmentArgs
import com.emikhalets.datesdb.view.DateEditFragmentDirections
import com.emikhalets.datesdb.view.DatesListFragmentDirections

class DateEditFragment : BaseFragment<DateEditIntent, DateEditAction, DateEditState, DateEditViewModel>(
        R.layout.fragment_date_edit,
        DateEditViewModel::class.java
) {

    private val binding by viewBinding(FragmentDateEditBinding::bind)
    override val viewModel: DateEditViewModel by activityViewModels()
    private val args: DateEditFragmentArgs by navArgs()

    private val imageResultActivity = registerForActivityResult(StartActivityForResult()) {
        it.data?.data?.let { uri ->
            viewModel.imageUri = uri.toString()
            binding.imageAvatar.setImageURI(uri)
        }
    }

    override fun initView() {
    }

    override fun initData() {
        if (args.id > 0) dispatchIntent(DateEditIntent.LoadDateItem(args.id))
    }

    override fun initEvents() {
        binding.fabSaveDate.setOnClickListener {
            dispatchIntent(DateEditIntent.UpdateDateItem(args.id))
        }
    }

    override fun render(state: DateEditState) {
        binding.progressBar.isVisible = state is DateEditState.Loading
        binding..isVisible = state is DateEditState.Error
        binding.listDates.isVisible = state is DateEditState.ResultDateItem

        when (state) {
            is DateEditState.ResultDateItem -> datesAdapter.submitList(state.data)
            is DateEditState.Error -> binding.textError.text = state.message
            else -> {
            }
        }
    }

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

        arguments?.let {
            val dateId = DateEditFragmentArgs.fromBundle(it).id
            viewModel.dateItemId = dateId
            if (dateId >= 0) viewModel.getDate(dateId)
        }

        with(viewModel) {
            dateItem.observe(viewLifecycleOwner, { setDateItemData(it) })
            dateString.observe(viewLifecycleOwner, { binding.tedDate.setText(it) })
            notice.observe(viewLifecycleOwner, { message -> showToast(message) })
        }

        with(binding) {
            imageAvatar.setOnClickListener { onImageClick() }
            btnAvatar.setOnClickListener { onImageClick() }
            tedDate.setOnClickListener { onDateClick() }
            acType.setOnClickListener { onTypesClick() }
            fabSaveDate.setOnClickListener { onSaveClick() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDateItemData(dateItem: DateItem) {
        with(viewModel) {
            imageUri = dateItem.image
            setDateTime(dateItem.date)
        }
        with(binding) {
            if (dateItem.image.isNotEmpty()) {
                imageAvatar.setImageURI(Uri.parse(dateItem.image))
                binding.btnAvatar.text = getString(R.string.text_change_avatar)
            }
            tedName.setText(dateItem.name)
            tedDate.setText(viewModel.getDateString())
            acType.setText(dateItem.type)
        }
    }

    private fun onImageClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .apply { type = "image/*" }
        imageResultActivity.launch(intent)
    }

    private fun onDateClick() {
        binding.tedDate.findNavController()
                .navigate(DateEditFragmentDirections.actionFragmentDateEditToDatePickerDialog())
    }

    private fun onTypesClick() {
        binding.root.findNavController()
                .navigate(DateEditFragmentDirections.actionFragmentDateEditToTypesDialog())
    }

    private fun onSaveClick() {
        lifecycleScope.launchWhenCreated {
            val name = binding.tedName.text.toString().trim()
            val type = binding.acType.text.toString().trim()
            if (viewModel.dateItemId > 0) viewModel.updateDate(name, type)
            else viewModel.insertDate(name, type)
            binding.root.findNavController().popBackStack()
        }
    }

    private fun showToast(msg: String) =
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}