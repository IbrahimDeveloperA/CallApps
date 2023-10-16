package com.on.callapps.ui.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.databinding.ItemCorrespondenceBinding

class AdapterCorrespondence : Adapter<AdapterCorrespondence.CorrespondenceView>() {

    private var list = mutableListOf(
        CorrespondenceModel("Hi, blonde!", "Hi"),
    )

    fun addText(coreModel: String) {
        if (coreModel == "Wow!") {
            list.add(CorrespondenceModel(text = coreModel, textFriend = "You are friend"))

        }
        notifyDataSetChanged ()
    }

    inner class CorrespondenceView(private val binding: ItemCorrespondenceBinding) :
        ViewHolder(binding.root) {
        fun onBind(model: CorrespondenceModel) {
            binding.tvMessage.text = model.text
            binding.tvMessageFriend.text = model.textFriend
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrespondenceView {
        return CorrespondenceView(
            ItemCorrespondenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CorrespondenceView, position: Int) {
        holder.onBind(list[position])
    }
}