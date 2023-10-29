package com.on.callapps.ui.image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentImageBinding
import kotlin.system.exitProcess

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private val pref by lazy { Pref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.btnRate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp"))
            startActivity(intent)
        }
        when (pref.saveContact) {
            1 -> {
                binding.ivLogo.setImageResource(R.drawable.ic_image_dog)
            }

            2 -> {

                binding.ivLogo.setImageResource(R.drawable.c2)
            }

            3 -> {

                binding.ivLogo.setImageResource(R.drawable.c3)
            }

            4 -> {
                binding.ivLogo.setImageResource(R.drawable.c4)
            }
        }

        binding.btnExit.setOnClickListener{
            requireActivity().finishAffinity()
            exitProcess(0)
        }
    }
}