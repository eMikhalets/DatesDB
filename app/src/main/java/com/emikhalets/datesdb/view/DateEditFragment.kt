package com.emikhalets.datesdb.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.databinding.FragmentDateEditBinding
import com.emikhalets.datesdb.viewmodel.DateEditViewModel

class DateEditFragment : Fragment() {

    private var _binding: FragmentDateEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DateEditViewModel by activityViewModels()

    private val imageResultActivity = registerForActivityResult(StartActivityForResult()) {
        it.data?.data?.let { uri ->
            viewModel.imageUri = uri.toString()
            binding.imageAvatar.setImageURI(uri)
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