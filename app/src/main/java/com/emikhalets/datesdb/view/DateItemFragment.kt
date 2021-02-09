package com.emikhalets.datesdb.view

import android.net.Uri
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
import com.emikhalets.datesdb.databinding.FragmentDateItemBinding
import com.emikhalets.datesdb.viewmodel.DateItemViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateItemFragment : Fragment() {

    private var _binding: FragmentDateItemBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel: DateItemViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.let {
                val id = DateItemFragmentArgs.fromBundle(it).id ?: -1
                Log.d("TAG", "arguments: $id")
                itemViewModel.id = id
                if (id >= 0) itemViewModel.getDate(id)
            }
        }

        itemViewModel.date.observe(viewLifecycleOwner, { dateItem ->
            with(binding) {
                if (dateItem.image.isEmpty()) {
                    imageAvatar.isVisible = false
                } else {
                    imageAvatar.setImageURI(Uri.parse(dateItem.image))
                    imageAvatar.isVisible = true
                }

                if (dateItem.isYear) textDate.text = formatDate(dateItem.date)
                else textDate.text = formatDateWithoutYear(dateItem.date)

                textName.text = dateItem.name
                textAge.text = getString(R.string.text_item_age, dateItem.age)
                textType.text = dateItem.type
                textDaysLeft.text = getString(R.string.text_item_days_left, dateItem.daysLeft)
            }
        })

        itemViewModel.deleting.observe(viewLifecycleOwner, { count ->
            binding.root.findNavController().popBackStack()
        })

        itemViewModel.notice.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.fabDeleteDate.setOnClickListener { itemViewModel.delete() }
        binding.fabEditDate.setOnClickListener { onEditClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onEditClick() {
        val id = itemViewModel.id
        if (id >= 0) {
            val action = DateItemFragmentDirections.actionFragmentDateItemToFragmentDateEdit(id)
            binding.root.findNavController().navigate(action)
        }
    }

    private fun formatDate(timestamp: Long): String {
        val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("UTC")
        )
        return date.format(DateTimeFormatter.ofPattern("d MMM y, E"))
    }

    private fun formatDateWithoutYear(timestamp: Long): String {
        val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("UTC")
        ).withYear(LocalDateTime.now().year + 1)
        return date.format(DateTimeFormatter.ofPattern("d MMM, E"))
    }
}