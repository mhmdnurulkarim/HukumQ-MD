package com.mhmdnurulkarim.hukumq.ui.main.laws

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mhmdnurulkarim.hukumq.data.model.Legal
import com.mhmdnurulkarim.hukumq.databinding.ItemLegalBinding

class LegalAdapter(
    private val onItemClick: (Legal) -> Unit
) : ListAdapter<Legal, LegalAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemLegalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(
        private val binding: ItemLegalBinding,
        val onItemClick: (Legal) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Legal) {
            binding.tvTitleLegal.text = item.title
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Legal>() {
            override fun areItemsTheSame(oldItem: Legal, newItem: Legal): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Legal, newItem: Legal): Boolean {
                return oldItem == newItem
            }
        }
    }
}