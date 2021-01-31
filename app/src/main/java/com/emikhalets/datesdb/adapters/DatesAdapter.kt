package com.emikhalets.datesdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.databinding.ItemDateBinding
import java.text.SimpleDateFormat
import java.util.*

class DatesAdapter(onDateItemClickListener: OnDateItemClickListener) : RecyclerView.Adapter<DatesAdapter.ViewHolder>() {
    private var dates: List<DateItem?>
    private val onDateItemClickListener: OnDateItemClickListener

    interface OnDateItemClickListener {
        fun onDateItemClick(id: Int)
    }

    fun setDates(dates: List<DateItem?>) {
        sortingList(dates.toMutableList())
        this.dates = dates
        notifyDataSetChanged()
    }

    fun sortingList(dates: MutableList<DateItem?>) {
        var isSorted = false
        var counter = 0
        while (!isSorted) {
            for (y in 0 until dates.size - 1) {
                val current = Calendar.getInstance()
                val currentDay = current[Calendar.DAY_OF_YEAR]
                val first = Calendar.getInstance()
                dates[y]?.date.also { first.timeInMillis = it!! }
                val firstDay = first[Calendar.DAY_OF_YEAR]
                var firstDaysLeft = 0
                firstDaysLeft = if (currentDay > firstDay) {
                    365 + firstDay - currentDay
                } else {
                    firstDay - currentDay
                }
                val second = Calendar.getInstance()
                second.timeInMillis = dates[y + 1]?.date!!
                val secondDay = second[Calendar.DAY_OF_YEAR]
                var secondDaysLeft = 0
                secondDaysLeft = if (currentDay > secondDay) {
                    365 + secondDay - currentDay
                } else {
                    secondDay - currentDay
                }
                if (firstDaysLeft > secondDaysLeft) {
                    counter++
                    val temp = dates[y]
                    dates.add(y, dates[y + 1])
                    dates.removeAt(y + 1)
                    dates.add(y + 1, temp)
                    dates.removeAt(y + 2)
                }
            }
            if (counter > 0) {
                counter = 0
            } else {
                isSorted = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dates[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    inner class ViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dateItem: DateItem) {
            val current = Calendar.getInstance()
            val currentDay = current[Calendar.DAY_OF_YEAR]
            val selected = Calendar.getInstance()
            selected.timeInMillis = dateItem.date
            val selectedDay = selected[Calendar.DAY_OF_YEAR]
            var daysLeft = 0
            daysLeft = if (currentDay > selectedDay) {
                365 + selectedDay - currentDay
            } else {
                selectedDay - currentDay
            }
            val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
            binding.textName.text = dateItem.name
            binding.textDate.text = dateFormat.format(dateItem.date)
            binding.textDaysLeft.text = dateItem.id.toString()
            binding.textDaysLeft.text = daysLeft.toString()
            binding.root.setOnClickListener { v: View? -> onDateItemClickListener.onDateItemClick(dateItem.id) }
        }
    }

    init {
        dates = ArrayList()
        this.onDateItemClickListener = onDateItemClickListener
    }
}