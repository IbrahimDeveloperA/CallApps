package com.on.callapps.ui.message.adapter.adapersss

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.drakeet.multitype.ViewHolderInflater
import com.on.callapps.databinding.ItemCorrespondenceFriendsBinding

class MyAdapter : ViewHolderInflater<FriendModel, MyAdapter .FriendViewHolder>() {

    inner class FriendViewHolder(private val binding: ItemCorrespondenceFriendsBinding) :
        ViewHolder(binding.root) {
        fun onBind(item: FriendModel) {
            binding.tvMessageFriend.text = item.friend
        }
    }

    override fun onBindViewHolder(holder: FriendViewHolder, item: FriendModel) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FriendViewHolder {
        return FriendViewHolder(
            ItemCorrespondenceFriendsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}