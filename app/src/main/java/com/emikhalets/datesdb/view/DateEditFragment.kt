package com.emikhalets.datesdb.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import com.emikhalets.datesdb.viewmodel.DateEditViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateEditFragment : Fragment() {

    private var _binding: FragmentDateEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DateEditViewModel by activityViewModels()
    private var typesAdapter: ArrayAdapter<String>? = null
    private var dateListener: OnDateSetListener? = null

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

        if (savedInstanceState == null) {
            if (viewModel.isDateDialogOpen) onDateClick()
            arguments?.let {
                val id = DateEditFragmentArgs.fromBundle(it).id ?: -1
                viewModel.currentDateId = id
                viewModel.getAllTypes()
                if (viewModel.currentDateId >= 0) {
                    viewModel.getDate(id)
                }
            }
        }

        viewModel.date.observe(viewLifecycleOwner, { dateItem -> updateUI(dateItem) })
        viewModel.insertingDate.observe(viewLifecycleOwner, { navigateBack() })
        viewModel.updatingDate.observe(viewLifecycleOwner, { navigateBack() })
        viewModel.types.observe(viewLifecycleOwner, { initTypesAdapter() })
        viewModel.notice.observe(viewLifecycleOwner, { message -> showToast(message) })

        dateListener = onSetDateClick()
        with(binding) {
            imageAvatar.setOnClickListener { onSetImageClick() }
            btnAvatar.setOnClickListener { onSetImageClick() }
            tedDate.setOnClickListener { onDateClick() }
            acType.setOnClickListener { initTypesAdapter() }
            acType.setOnItemClickListener { _, _, position, _ -> onNewTypeClick(position) }
            fabSaveDate.setOnClickListener { onSaveClick() }
            cbIsYear.setOnCheckedChangeListener(onYearClick())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        typesAdapter = null
        dateListener = null
        _binding = null
    }

    private fun updateUI(dateItem: DateItem) {
        with(binding) {
            if (dateItem.image.isNotEmpty()) {
                imageAvatar.setImageURI(Uri.parse(dateItem.image))
                binding.btnAvatar.text = getString(R.string.text_change_avatar)
                viewModel.image = dateItem.image
            }
            viewModel.setDateTime(dateItem.date)
            viewModel.isDateVisible = true
            viewModel.isYear = dateItem.isYear
            tedName.setText(dateItem.name)
            tedDate.setText(viewModel.formatDateString())
            cbIsYear.isChecked = viewModel.isYear
            acType.setText(dateItem.type)
        }
    }

    private fun navigateBack() {
        binding.root.findNavController().popBackStack()
    }

    private fun onSetImageClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            binding.imageAvatar.setImageURI(uri)
            viewModel.image = uri.toString()
        }
    }

    private fun initDateDialog() = DatePickerDialog(
            requireContext(),
            dateListener,
            viewModel.localDateTime.year,
            viewModel.localDateTime.monthValue - 1,
            viewModel.localDateTime.dayOfMonth,
    )

    private fun onDateClick() {
        viewModel.isDateDialogOpen = true
        initDateDialog().show()
    }

    private fun onSetDateClick() = OnDateSetListener { _, year, month, dayOfMonth ->
        val new = LocalDateTime.now()
                .withYear(year)
                .withMonth(month + 1)
                .withDayOfMonth(dayOfMonth)
        viewModel.localDateTime = new
        viewModel.isDateVisible = true
        viewModel.isDateEntered = true
        viewModel.isDateDialogOpen = false
        binding.tedDate.setText(viewModel.formatDateString())
    }

    private fun onNewTypeClick(position: Int) {
        if (position == 0) {
            val action = DateEditFragmentDirections.actionFragmentDateEditToAddTypeDialog()
            binding.root.findNavController().navigate(action)
            binding.acType.setText("")
        }
    }

    private fun initTypesAdapter() {
        binding.acType.setAdapter(
                ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        viewModel.typesItems
                )
        )
    }

    private fun onYearClick() = CompoundButton.OnCheckedChangeListener { _, bool ->
        viewModel.isYear = !bool
        binding.tedDate.setText(viewModel.formatDateString())
    }

    private fun onSaveClick() {
        val name = binding.tedName.text.toString().trim()
        viewModel.name = name
        val date = viewModel.localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        viewModel.dateField = date
        val type = binding.acType.text.toString().trim()
        viewModel.type = type
        if (viewModel.currentDateId > 0) viewModel.updateDate()
        else viewModel.insertDate()
    }

    private fun showToast(msg: String) =
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}