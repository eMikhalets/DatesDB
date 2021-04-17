package com.emikhalets.datesdb.ui.dates_list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.ItemDateBinding
import com.emikhalets.datesdb.model.entities.DateItem

class DatesAdapter(private val click: (Long) -> Unit)
    : ListAdapter<DateItem, DatesAdapter.ViewHolder>(DatesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { click.invoke(getItem(holder.adapterPosition).id) }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemDateBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: DateItem) {
            with(binding) {
                imageAvatar.setImageURI(Uri.parse(dateItem.image))
                textName.text = dateItem.name
                textDaysLeft.text = root.context.getString(
                        R.string.text_list_item_days_left,
                        dateItem.daysLeft
                )

//                if (dateItem.isYear) textDate.text = root.context.getString(
//                        R.string.text_list_item_date,
//                        formatDate(dateItem.date),
//                        dateItem.age + 1
//                )
//                else textDate.text = formatDateWithoutYear(dateItem.date)
            }
        }
    }

    class DatesDiffCallback : DiffUtil.ItemCallback<DateItem>() {

        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem == newItem
    }
}