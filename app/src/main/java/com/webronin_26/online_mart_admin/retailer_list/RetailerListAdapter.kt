package com.webronin_26.online_mart_admin.retailer_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart_admin.Event
import com.webronin_26.online_mart_admin.data.remote.Response
import com.webronin_26.online_mart_admin.databinding.RetailerListItemBinding

class RetailerListAdapter(private val viewModel: RetailerListViewModel):
    ListAdapter<Response.Retailers, RetailerListAdapter.RetailerListViewHolder>(RetailerListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetailerListViewHolder {
        return RetailerListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RetailerListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    override fun submitList(list: MutableList<Response.Retailers>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    class RetailerListViewHolder private constructor(private val binding: RetailerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: RetailerListViewModel , item: Response.Retailers) {

            binding.viewmodel = viewModel
            binding.retailers = item
            binding.retailerListItemLinearLayout.setOnClickListener { viewModel.retailerId.value = Event(item.id) }

            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : RetailerListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RetailerListItemBinding.inflate(layoutInflater , parent , false)
                return RetailerListViewHolder(binding)
            }
        }
    }
}

class RetailerListDiffCallback : DiffUtil.ItemCallback<Response.Retailers>() {

    override fun areContentsTheSame(oldItem: Response.Retailers, newItem: Response.Retailers): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.Retailers, newItem: Response.Retailers): Boolean {
        return oldItem == newItem
    }
}