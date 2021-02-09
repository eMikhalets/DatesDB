package com.emikhalets.datesdb.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import com.emikhalets.datesdb.viewmodel.DateEditViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateEditFragment : Fragment() {

    private var _binding: FragmentDateEditBinding? = null
    private val binding get() = _binding!!

    private val editViewModel: DateEditViewModel by viewModels()
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
            if (editViewModel.isDateDialogOpen) onDateClick()
            arguments?.let {
                val id = DateEditFragmentArgs.fromBundle(it).id
                if (id >= 0) {
                    editViewModel.id = id
                    editViewModel.getDate(id)
                    editViewModel.getAllTypes()
                }
            }
        }

        editViewModel.date.observe(viewLifecycleOwner, { dateItem ->
            with(binding) {
                if (dateItem.image.isNotEmpty()) {
                    imageAvatar.setImageURI(Uri.parse(dateItem.image))
                    binding.btnAvatar.text = getString(R.string.text_change_avatar)
                    editViewModel.imageUri = dateItem.image
                }
                editViewModel.setDateTime(dateItem.date)
                editViewModel.isDateVisible = true
                editViewModel.isYear = dateItem.isYear
                tedName.setText(dateItem.name)
                tedDate.setText(editViewModel.formatDateString())
                cbIsYear.isChecked = editViewModel.isYear
                acType.setText(dateItem.type)
            }
        })

        editViewModel.updating.observe(viewLifecycleOwner, { count ->
            binding.root.findNavController().popBackStack()
        })

        editViewModel.types.observe(viewLifecycleOwner, { list ->
            createAdapter()
        })

        editViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        dateListener = onSetDateClick()
        binding.imageAvatar.setOnClickListener { onAvatarClick() }
        binding.btnAvatar.setOnClickListener { onAvatarClick() }
        binding.tedDate.setOnClickListener { onDateClick() }
        binding.acType.setOnClickListener { onTypeClick() }
        binding.fabUpdateDate.setOnClickListener { onUpdateClick() }
        binding.cbIsYear.setOnCheckedChangeListener(onYearClick())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        typesAdapter = null
        dateListener = null
        _binding = null
    }

    private fun onAvatarClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            binding.imageAvatar.setImageURI(uri)
            editViewModel.imageUri = uri.toString()
        }
    }

    private fun initDateDialog() = DatePickerDialog(
            requireContext(), dateListener,
            editViewModel.dateTime.year,
            editViewModel.dateTime.monthValue - 1,
            editViewModel.dateTime.dayOfMonth,
    )

    private fun onDateClick() {
        editViewModel.isDateDialogOpen = true
        initDateDialog().show()
    }

    private fun onSetDateClick() = OnDateSetListener { _, year, month, dayOfMonth ->
        val new = LocalDateTime.now()
                .withYear(year)
                .withMonth(month + 1)
                .withDayOfMonth(dayOfMonth)
        editViewModel.dateTime = new
        editViewModel.isDateVisible = true
        editViewModel.isDateDialogOpen = false
        binding.tedDate.setText(editViewModel.formatDateString())
    }

    private fun onTypeClick() {
        createAdapter()
    }

    private fun createAdapter() {
        typesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                editViewModel.typeItems
        )
        binding.acType.setAdapter(typesAdapter)
    }

    private fun onYearClick() = CompoundButton.OnCheckedChangeListener { _, bool ->
        editViewModel.isYear = !bool
        binding.tedDate.setText(editViewModel.formatDateString())
    }

    private fun onUpdateClick() {
        val name = binding.tedName.text.toString().trim()
        val type = binding.acType.text.toString().trim()
        val date = editViewModel.dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        editViewModel.update(name, date, type)
    }
}