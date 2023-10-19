package com.on.callapps.ui.live.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.R
import com.on.callapps.databinding.ItemLiveBinding
import com.on.callapps.ui.live.adapter.models.LiveModel

class LiveAdapter() : Adapter<LiveAdapter.LiveViewHolder>() {

    private val list =  mutableListOf<LiveModel>()

    private fun addData() {
        list.add(LiveModel(text = "Hi", image = R.drawable.frame_new4))
        handler("Cool that I found this!", (100..10000).random())
        handler("Hope to see you again soon!", (100..10000).random())
        handler("How are you?", (100..10000).random())
        handler("I've heard so much about you", (100..10000).random())
        handler("What's up?", (100..10000).random())
        handler("Thanks for having me", (100..10000).random())
        handler("How is everything?", (100..10000).random())
    }

    fun addDataWithDelay() {
        addData()
        scheduleDataUpdate()
    }

    private fun scheduleDataUpdate() {
        val handler = Handler(Looper.getMainLooper())
        val delayMillis = (2000..5000).random()

        handler.postDelayed({
            addData()
            notifyDataSetChanged()
            scheduleDataUpdate()
        }, delayMillis.toLong())
    }

    private fun handler(text: String, delay: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            list.add(
                LiveModel(
                    text = text,
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, delay.toLong())
    }

    class LiveViewHolder(private val binding: ItemLiveBinding) : ViewHolder(binding.root) {
        fun onBind(liveModel: LiveModel) {
            binding.tvName.text = liveModel.title
            binding.tvText.text = liveModel.text
            liveModel.image?.let { binding.img.setImageResource(it) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        return LiveViewHolder(
            ItemLiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: LiveViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}