package com.mhmdnurulkarim.hukumq.ui.main.home

import android.graphics.Color
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
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
            if (currentUser == item.message.currentUser) {
                //Bot
                binding.apply {
                    tvMessage.text = item.message.text
                    tvMessage.setBackgroundResource(R.drawable.message_bot)
                    tvMessage.setTextColor(Color.parseColor("#FFFFFFFF"))
                    tvMessenger.text = itemView.resources.getString(R.string.hukubot)
                    tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.message.timestamp)
                }
                Glide.with(itemView.context)
                    .load("https://static.vecteezy.com/system/resources/previews/001/877/002/large_2x/cute-robot-face-icon-vector.jpg")
                    .circleCrop()
                    .into(binding.ivMessenger)
            } else {
                //Human
                binding.apply {
                    tvMessage.text = item.message.text
                    tvMessage.setBackgroundResource(R.drawable.message_user)
                    tvMessage.setTextColor(Color.parseColor("#FF000000"))
                    tvMessenger.text = item.user.name
                    tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.message.timestamp)
                }
                Glide.with(itemView.context)
                    .load(item.user.photoUrl)
                    .circleCrop()
                    .into(binding.ivMessenger)
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