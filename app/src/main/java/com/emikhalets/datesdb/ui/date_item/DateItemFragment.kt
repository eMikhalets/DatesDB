package com.emikhalets.datesdb.ui.date_item

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.common.BaseFragment
import com.emikhalets.datesdb.databinding.FragmentDateItemBinding
import com.emikhalets.datesdb.view.DateItemFragmentArgs
import com.emikhalets.datesdb.view.DateItemFragmentDirections

class DateItemFragment : BaseFragment<DateItemIntent, DateItemAction, DateItemState, DateItemViewModel>(
        R.layout.fragment_date_item,
        DateItemViewModel::class.java
) {

    private val binding by viewBinding(FragmentDateItemBinding::bind)
    override val viewModel: DateItemViewModel by viewModels()
    private val args: DateItemFragmentArgs by navArgs()

    override fun initView() {
    }

    override fun initData() {
        if (args.id > 0) dispatchIntent(DateItemIntent.LoadDateItem(args.id))
    }

    override fun initEvents() {
        binding.apply {
            fabDeleteDate.setOnClickListener {
                if (args.id > 0) {
                    viewModel.dispatchIntent(DateItemIntent.DeleteDateItem(args.id))
                }
            }
            fabEditDate.setOnClickListener {
                if (args.id > 0) {
                    val action = DateItemFragmentDirections.actionDateItemToDateEdit(args.id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun render(state: DateItemState) {
        binding.progressBar.isVisible = state is DateItemState.Loading
        binding.textError.isVisible = state is DateItemState.Error
        binding.layoutDateItem.isVisible = state is DateItemState.ResultDateItem

        when (state) {
            is DateItemState.Error -> binding.textError.text = state.message
            is DateItemState.Deleted -> findNavController().popBackStack()
            is DateItemState.ResultDateItem -> {
                binding.apply {
                    // TODO: set data
                    if (state.data.image.isEmpty()) {
                        imageAvatar.isVisible = false
                    } else {
                        imageAvatar.setImageURI(Uri.parse(state.data.image))
                        imageAvatar.isVisible = true
                    }
//                    if (state.data.isYear) textDate.text = formatDate(state.data.date)
//                    else textDate.text = formatDateWithoutYear(state.data.date)
                    textName.text = state.data.name
                    textAge.text = getString(R.string.text_item_age, state.data.age)
                    textType.text = state.data.type
                    textDaysLeft.text = getString(R.string.text_item_days_left, state.data.daysLeft)
                }
            }
            else -> {
            }
        }
    }
}