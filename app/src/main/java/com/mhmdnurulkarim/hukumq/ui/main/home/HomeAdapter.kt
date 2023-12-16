package com.mhmdnurulkarim.hukumq.ui.main.home

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.data.model.Chat
import com.mhmdnurulkarim.hukumq.databinding.ItemMessageBinding

class HomeAdapter(
    private val currentUser: Boolean
) : ListAdapter<Chat, HomeAdapter.MessageViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Chat) {
            binding.tvMessage.text = item.message.text
            setTextColor(item.message.currentUser, binding.tvMessage)
            binding.tvMessenger.text = item.user.name
            Glide.with(itemView.context)
                .load(item.user.photoUrl)
                .circleCrop()
                .into(binding.ivMessenger)
            binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.message.timestamp)
        }

        private fun setTextColor(bot: Boolean?, textView: TextView) {
            if (currentUser == bot) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue) //Bot
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_yellow) //Human
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.message.messageId == newItem.message.messageId
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }
        }
    }
}