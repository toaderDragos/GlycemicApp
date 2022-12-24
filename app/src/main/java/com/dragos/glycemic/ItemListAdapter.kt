package com.dragos.glycemic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dragos.glycemic.data.Item
import com.dragos.glycemic.databinding.ItemListItemBinding

class ItemListAdapter(private val onItemClicked: (Item) -> Unit) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {
    // we do the viewbinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    // we set the clicklistener
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    // boilerplate code for the binding of details from Item class
    // this one takes the already Saved items from the database and puts them ine by one in the viewholder
    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                yourFoodItem.text = item.yourFood
                foodWeightItem.setText(item.foodWeight.toString())
                carbs100GramsItem.setText(item.carbsPer100Grams.toString())
                carbsItem.setText(item.carbs.toString())
                glycemicIndexItem.setText(item.glycemicIndex.toString())
                glycemicLoadItem.setText(item.glycemicLoad.toString())
            }
        }
    }

    // 4 coroutines check to see if we haven't already downloaded stuff twice
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.yourFood == newItem.yourFood
            }
        }
    }
}