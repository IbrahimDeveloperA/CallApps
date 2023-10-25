package com.on.callapps.ui.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.databinding.ItemMessageBinding

class MessageAdapter(private val onClick: (String) -> Unit) :
    Adapter<MessageAdapter.MessageViewHolder>() {

    private val list =
        mutableListOf(
            "Hi!",
            "What's your name?",
            "How are you?",
            "How old are you?",
            "Nice to meet you",
            "Where are you from?",
            "What kind of food do you like?",
            "Can I call you now?",
            "Can we have a video call with you now?",
            "What is you favourite cartoon?",
            "What is your education?",
            "Is it possible to be friends?",
            "Goodbye! See you!",
            "Have a nice day!"
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