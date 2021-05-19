package com.emikhalets.datesdb.ui.date_item

import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.FragmentDateItemBinding
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.mvi.MviFragment

class DateItemFragment :
    MviFragment<DateItemIntent, DateItemAction, DateItemState, DateItemViewModel>(
        R.layout.fragment_date_item,
        DateItemViewModel::class.java
    ) {

    override val viewModel: DateItemViewModel by viewModels()

    private val binding by viewBinding(FragmentDateItemBinding::bind)
    private val args: DateItemFragmentArgs by navArgs()
    private lateinit var dateItem: DateItem

    override fun initData() {
        dateItem = args.dateItem
        viewModel.dispatchIntent(DateItemIntent.LoadDateType(dateItem.typeId))
    }

    override fun initView() {
        binding.apply {
            if (dateItem.imageUri.isEmpty()) {
                imageAvatar.visibility = View.GONE
            } else {
                imageAvatar.setImageURI(Uri.parse(dateItem.imageUri))
                imageAvatar.visibility = View.VISIBLE
            }
            textName.text = dateItem.name
            textAge.text = resources.getQuantityText(
                R.plurals.date_item_text_age,
                dateItem.age
            )
            textDaysLeft.text = resources.getQuantityText(
                R.plurals.date_item_text_days_left,
                dateItem.daysLeft
            )
        }
    }

    override fun initIntents() {
        binding.apply {
            btnDeleteDate.setOnClickListener {
                viewModel.dispatchIntent(DateItemIntent.ClickDeleteDateItem(dateItem))
            }
            btnEditDate.setOnClickListener {
                val action = DateItemFragmentDirections.actionDateItemToDateEdit(dateItem)
                findNavController().navigate(action)
            }
        }
    }

    override fun fetchState(state: DateItemState) {
        when (state) {
            DateItemState.Deleted -> {
                findNavController().popBackStack()
            }
            is DateItemState.DateTypeLoaded -> {
                binding.textType.text = state.dateType.name
            }
            is DateItemState.Error -> {
                binding.textError.text = state.message
                binding.textError.visibility = View.VISIBLE
                binding.layoutInfo.visibility = View.GONE
            }
        }
    }
}