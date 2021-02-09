package com.emikhalets.datesdb.view

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.databinding.ItemDateBinding
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
        Log.d("TAG", "dates.observe: pos=$position id=${getItem(position).id}")
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { getItem(position).id?.let { id -> click.invoke(id) } }
    }

    class ViewHolder(private val binding: ItemDateBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: DateItem) {
            with(binding) {
                imageAvatar.setImageURI(Uri.parse(dateItem.image))
                textName.text = dateItem.name
                textDaysLeft.text = root.context.getString(
                        R.string.text_list_item_days_left,
                        dateItem.daysLeft
                )

                if (dateItem.isYear) textDate.text = root.context.getString(
                        R.string.text_list_item_date,
                        formatDate(dateItem.date),
                        dateItem.age + 1
                )
                else textDate.text = formatDateWithoutYear(dateItem.date)
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

    class DatesDiffCallback : DiffUtil.ItemCallback<DateItem>() {

        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem == newItem
    }
}