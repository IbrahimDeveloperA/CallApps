package com.on.callapps.ui.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.databinding.ItemMessageBinding

class MessageAdapter(private val onClick: (String) -> Unit) :
    Adapter<MessageAdapter.MessageViewHolder>() {

    private val list =
        mutableListOf<String>(
            "Wow!",
            "What are your plans for the evening?",
            "Cool!",
            "Hello world!",
            "What's your name?"
        )

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        ViewHolder(binding.root) {
        fun onBind(text: String) {
            binding.tvMessage.text = text
            binding.tvMessage.setOnClickListener {
                onClick(text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(list[position])
    }

}