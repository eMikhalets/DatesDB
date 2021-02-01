package com.emikhalets.datesdb.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.emikhalets.datesdb.databinding.FragmentDateItemBinding

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

        if (savedInstanceState != null) {
            arguments?.let {
                val id = it.getInt("") ?: -1
                itemViewModel.id = id
                if (id >= 0) itemViewModel.getDate(id)
            }
        }

        itemViewModel.date.observe(viewLifecycleOwner, { dateItem ->
            with(binding) {
                textName.text = dateItem.name
                textDate.text = dateItem.date.toString()
                textType.text = dateItem.type
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
}