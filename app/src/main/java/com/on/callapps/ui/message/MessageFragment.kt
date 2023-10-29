package com.on.callapps.ui.message

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.data.local.Pref
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
    private val requestCodeCameraPermission = 1001
    private val pref by lazy { Pref(requireContext()) }

    private fun onClick(text: String) {
        multiAdapter.notifyItemInserted(0)
        items.add(MyModel(text = text))
        multiAdapter.notifyDataSetChanged()
        sendMessage(text,"What's your name?","I'm Max")
        sendMessage(text,"How are you?","I'm fine, thank you")
        sendMessage(text,"Nice to meet you","Me too")
        sendMessage(text,"Where are you from?","I'm from United States")
        sendMessage(text,"What kind of food do you like?","I love pizza!")
        sendMessage(text,"Can I call you now?","Yes! I'm waiting for your call!!!")
        sendMessage(text,"Can we have a video call with you now?","Yes, of course")
        sendMessage(text,"What is you favourite cartoon?","My favourite cartoon is Lion King")
        sendMessage(text,"What is your education?","I'm in high school in London")
        sendMessage(text,"Is it possible to be friends?","Yes, of course. Let's become friends")
        sendMessage(text,"Goodbye! See you!","See you again")
        sendMessage(text,"Have a nice day!","Thank you for this chat, you too!")
        sendMessage(text,"How old are you?","I'm 25 years old")

        message(text)
    }


   private fun sendMessage(text:String,reply:String,end:String){
       if (text == reply) {
           binding.tvStatus.text = "typing..."
           Handler(Looper.getMainLooper()).postDelayed({
               items.add(FriendModel(end))
               multiAdapter.notifyDataSetChanged()
               Handler(Looper.getMainLooper()).postDelayed({
                   binding.tvStatus.text = "online"
               }, 900)
           }, 2000)
       }
   }
    private fun message(text: String) {
        if (text == "Hi!") {
            binding.tvStatus.text = "typing..."
            Handler(Looper.getMainLooper()).postDelayed({
                items.add(FriendModel("Hi! Welcome to my chat"))
                multiAdapter.notifyDataSetChanged()
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tvStatus.text = "online"
                }, 900)
            }, 2000)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.recyclerView.adapter = adapter
        items = ArrayList()
        multiAdapter = MultiTypeAdapter()
        multiAdapter.register(myAdapter)
        multiAdapter.register(adapterFriend)

        multiAdapter.items = items
        adapter.notifyDataSetChanged()

        binding.btnGift.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }

        binding.recyclerManager.adapter = multiAdapter

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnVideoCall.setOnClickListener {
            navigateInCall()
        }

        binding.imgCall.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.callFragment)
        }
        when (pref.saveContact) {
            1 -> {
                binding.tvName.text = "Max"
                binding.ivLogo.setImageResource(R.drawable.ic_image_dog)
            }

            2 -> {
                binding.tvName.text = "Rocky"
                binding.ivLogo.setImageResource(R.drawable.c2)
            }

            3 -> {
                binding.tvName.text = "Charlie"
                binding.ivLogo.setImageResource(R.drawable.c3)
            }

            4 -> {
                binding.tvName.text = "Milo"
                binding.ivLogo.setImageResource(R.drawable.c4)
            }
        }
    }

    private fun navigateInCall() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            showScanner()
        }
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    private fun showScanner() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.videoCallFragment)
    }
}