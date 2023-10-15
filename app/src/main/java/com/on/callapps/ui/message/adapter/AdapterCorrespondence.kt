package com.on.callapps.ui.message.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.R
import com.on.callapps.databinding.ItemCorrespondenceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdapterCorrespondence : Adapter<AdapterCorrespondence.CorrespondenceView>() {

    private var list = mutableListOf(
        CorrespondenceModel("Hi, blonde!", "Hi"),
    )

    fun addText(coreModel: String) {
        if (coreModel == "Wow!") {
            val correspondenceModel =
                CorrespondenceModel(text = coreModel, textFriend = "You are friend")
            list.add(correspondenceModel)
            notifyDataSetChanged()

            // Запускаем корутину с задержкой
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                correspondenceModel.textFriend = "Delayed textFriend"
                notifyDataSetChanged()
            }
        }
    }

//    fun addText(coreModel: String) {
//        if (coreModel == "Wow!") {
//            list.add(CorrespondenceModel(text = coreModel, textFriend = "You are friend"))
//
//        }
//        notifyDataSetChanged ()
//    }
//        if (coreModel == "Wow!") {
//        Handler().postDelayed({
//            list.add(CorrespondenceModel(text = coreModel, textFriend = "Second friend"))
//            notifyDataSetChanged()
//        }, 5000)}
//        notifyDataSetChanged()


    //        if (coreModel == "Wow!") {
//            list.add(CorrespondenceModel(text = coreModel, textFriend = "You are friend"))
//        } else if (coreModel == "What are your plans for the evening?") {
//            list.add(
//                CorrespondenceModel(
//                    text = coreModel,
//                    textFriend = "In the evening I go with my friends to a cool party on a yacht"
//                )
//            )
//        } else if ( coreModel == "What's your name?"){
//            list.add(CorrespondenceModel(text = coreModel, textFriend = "My name is Aziz"))
//        }

    inner class CorrespondenceView(private val binding: ItemCorrespondenceBinding) :
        ViewHolder(binding.root) {
        fun onBind(model: CorrespondenceModel) {
            binding.tvMessage.text = model.text
            binding.tvMessageFriend.text = model.textFriend
//            if (adapterPosition % 2 == 0) {
//                binding.tvMessage.setBackgroundColor(R.drawable.tv_message_friend)
//            }
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