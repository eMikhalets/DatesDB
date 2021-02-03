package com.emikhalets.datesdb.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.databinding.ItemDateBinding

class DatesAdapter(private val click: (Int) -> Unit):
        ListAdapter<DateItem, DatesAdapter.ViewHolder>(DatesDiffCallback()) {

    interface OnDateItemClickListener {
        fun onDateItemClick(id: Int)
    }

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
//            val current = Calendar.getInstance()
//            val currentDay = current[Calendar.DAY_OF_YEAR]
//            val selected = Calendar.getInstance()
//            selected.timeInMillis = dateItem.date
//            val selectedDay = selected[Calendar.DAY_OF_YEAR]
//            var daysLeft = 0
//            daysLeft = if (currentDay > selectedDay) {
//                365 + selectedDay - currentDay
//            } else {
//                selectedDay - currentDay
//            }
//            val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")

            binding.textName.text = dateItem.name
//            binding.textDate.text = dateFormat.format(dateItem.date)
            binding.textDaysLeft.text = dateItem.id.toString()
//            binding.textDaysLeft.text = daysLeft.toString()
            binding.root.setOnClickListener { dateItem.id?.let { it1 -> click.invoke(it1) } }
        }
    }

    class DatesDiffCallback : DiffUtil.ItemCallback<DateItem>() {

        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean =
                oldItem == newItem
    }
}