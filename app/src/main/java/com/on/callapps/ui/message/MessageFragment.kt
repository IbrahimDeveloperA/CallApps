package com.on.callapps.ui.message

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drakeet.multitype.MultiTypeAdapter
import com.on.callapps.databinding.FragmentMessageBinding
import com.on.callapps.ui.message.adapter.MessageAdapter
import com.on.callapps.ui.message.adapter.adapersss.AdapterFriend
import com.on.callapps.ui.message.adapter.adapersss.FriendModel
import com.on.callapps.ui.message.adapter.adapersss.MyAdapter
import com.on.callapps.ui.message.adapter.adapersss.MyModel
import java.util.ArrayList

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private val adapter = MessageAdapter(this::onClick)
    private lateinit var items: MutableList<Any>
    private lateinit var multiAdapter: MultiTypeAdapter
    private val adapterFriend = AdapterFriend()
    private val myAdapter = MyAdapter()

    private fun onClick(text: String) {
        Toast.makeText(requireContext(), "${items.size}", Toast.LENGTH_SHORT).show()
        multiAdapter.notifyItemInserted(0)
        items.add(MyModel(text = text))
        multiAdapter.notifyDataSetChanged()

        if (text == "Wow!") {
            binding.tvStatus.text = "печатает..."
            Handler(Looper.getMainLooper()).postDelayed({
                items.add(FriendModel("You are a great boy"))
                multiAdapter.notifyDataSetChanged()
                // Здесь, после завершения асинхронной задачи, установите tvStatus обратно в "online"
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tvStatus.text = "online"
                }, 1000) // 1000 миллисекунд (1 секунда) после завершения задачи
            }, 2000)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        items = ArrayList()
        multiAdapter = MultiTypeAdapter()
        multiAdapter.register(myAdapter)
        multiAdapter.register(adapterFriend)

        multiAdapter.items = items
        adapter.notifyDataSetChanged()

        binding.recyclerManager.adapter = multiAdapter

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        //binding.recyclerManager.adapter = adapterCorrespondence
    }
}