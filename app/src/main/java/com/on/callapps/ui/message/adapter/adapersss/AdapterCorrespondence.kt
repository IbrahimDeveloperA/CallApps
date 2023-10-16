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

//class AdapterCorrespondence : Adapter<AdapterCorrespondence.CorrespondenceView>() {
//
//    private var list = mutableListOf<CorrespondenceModel>(
//    )
//
//    fun addText(coreModel: String) {
//        var model = CorrespondenceModel(text = coreModel)
//        list.add(CorrespondenceModel(text = model.text))
//        notifyDataSetChanged()
//        if (coreModel == "Wow!") {
//            Handler(Looper.getMainLooper()).postDelayed({
//                model.copy( textFriend = "You are friend")
//                list.add(CorrespondenceModel( textFriend = model.textFriend))
//                notifyDataSetChanged()
//            }, 2000)
//        }
//    }
//
//    fun deleteNull() {
//        val iterator = list.iterator()
//        while (iterator.hasNext()) {
//            val element = iterator.next()
//            if (element == null) {
//                iterator.remove()
//            }
//        }
//    }
//
//    inner class CorrespondenceView(private val binding: ItemCorrespondenceBinding) :
//        ViewHolder(binding.root) {
//        fun onBind(model: CorrespondenceModel) {
//            binding.tvMessage.text = model.text
//            binding.tvMessageFriend.text = model.textFriend
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrespondenceView {
//        return CorrespondenceView(
//            ItemCorrespondenceBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun getItemCount() = list.size
//
//    override fun onBindViewHolder(holder: CorrespondenceView, position: Int) {
//        holder.onBind(list[position])
//    }
//}