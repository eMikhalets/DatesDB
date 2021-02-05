package com.emikhalets.datesdb.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.ItemDateBinding
import com.emikhalets.datesdb.model.entities.DateItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DatesAdapter(private val click: (Int) -> Unit) :
        ListAdapter<DateItem, DatesAdapter.ViewHolder>(DatesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), click)
    }

    class ViewHolder(private val binding: ItemDateBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: DateItem, click: (Int) -> Unit) {
            binding.textName.text = dateItem.name
            binding.textDate.text = binding.root.context.getString(
                    R.string.text_list_item_date,
                    formatDate(dateItem.date),
                    dateItem.age
            )
            binding.textDaysLeft.text = dateItem.id.toString()
            binding.textDaysLeft.text = binding.root.context.getString(
                    R.string.text_list_item_days_left,
                    dateItem.daysLeft
            )
            binding.root.setOnClickListener { dateItem.id?.let { it1 -> click.invoke(it1) } }
        }

        private fun formatDate(timestamp: Long): String {
            val date = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(timestamp),
                    ZoneId.of("UTC")
            )
            return date.format(DateTimeFormatter.ofPattern("d MMM y, E"))
        }
    }

    class DatesDiffCallback : DiffUtil.ItemCallback<DateItem>() {

        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem == newItem
    }
}