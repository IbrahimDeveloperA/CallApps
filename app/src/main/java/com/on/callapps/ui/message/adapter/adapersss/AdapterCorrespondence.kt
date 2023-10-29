package com.on.callapps.ui.message.adapter.adapersss

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.drakeet.multitype.ViewHolderInflater
import com.on.callapps.databinding.ItemCorrespondenceBinding

class AdapterFriend() : ViewHolderInflater<MyModel, AdapterFriend.FriendViewHolder>() {

    inner class FriendViewHolder(private val binding: ItemCorrespondenceBinding) :
        ViewHolder(binding.root) {
        fun onBind(item: MyModel) {
            binding.tvMessage.text = item.text
        }
    }

    override fun onBindViewHolder(holder: FriendViewHolder, item: MyModel) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FriendViewHolder {
        return FriendViewHolder(
            ItemCorrespondenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}