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

    private var list = mutableListOf<LiveModel>()

    fun addData() {
        list.add(LiveModel(text = "Hello", image = R.drawable.frame_new4))
        notifyDataSetChanged()
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 2000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 6000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 8000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 10000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 12000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 14000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 16000)
        Handler().postDelayed({
            list.add(
                LiveModel(
                    text = "Второй данный",
                    image = R.drawable.frame_new4
                )
            )
            notifyDataSetChanged()
        }, 18000)
        notifyDataSetChanged()
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