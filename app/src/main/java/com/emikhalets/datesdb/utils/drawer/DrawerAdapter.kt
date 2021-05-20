package com.emikhalets.datesdb.utils.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emikhalets.datesdb.databinding.ItemDrawerBinding

class DrawerAdapter(
    val click: (String) -> Unit
) :
    ListAdapter<DrawerItem, DrawerAdapter.DrawerViewHolder>(DrawerItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDrawerBinding.inflate(inflater, parent, false)
        return DrawerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: DrawerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { click.invoke(getItem(holder.adapterPosition).name) }
    }

    override fun onViewDetachedFromWindow(holder: DrawerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    class DrawerViewHolder(private val binding: ItemDrawerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DrawerItem) {
            binding.apply {
                imageIcon.load(item.iconRes)
                textName.text = item.name
            }
        }
    }

    class DrawerItemCallback : DiffUtil.ItemCallback<DrawerItem>() {

        override fun areItemsTheSame(oldItem: DrawerItem, newItem: DrawerItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DrawerItem, newItem: DrawerItem): Boolean {
            return oldItem.name == newItem.name
        }
    }
}