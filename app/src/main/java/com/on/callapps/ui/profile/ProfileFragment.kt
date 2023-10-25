package com.on.callapps.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.WebViewActivity
import com.on.callapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.tvChat.setOnClickListener {
            findNavController().navigate(R.id.messageFragment)
        }
        binding.imgSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvCharecters.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvCall.setOnClickListener {
            findNavController().navigate(R.id.callFragment)
        }

        binding.tvPlay.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", "https://www.gamezop.com/?id=3178")
            startActivity(intent)
        }

        binding.tvLive.setOnClickListener {
            findNavController().navigate(R.id.liveFragment)
        }
    }
}