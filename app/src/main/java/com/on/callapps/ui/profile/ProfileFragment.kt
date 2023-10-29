package com.on.callapps.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.WebViewActivity
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val requestCodeCameraPermission = 1001
    private val pref by lazy { Pref(requireContext()) }

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
        binding.tvVideoCall.setOnClickListener {
            findNavController().navigate(R.id.videoCallFragment)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvRate.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp")
            )
            startActivity(intent)
        }
        binding.tvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp"
            )
            startActivity(Intent.createChooser(intent, "Share"))
        }

        binding.tvCharecters.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvCall.setOnClickListener {
            findNavController().navigate(R.id.action_profile_call)
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

        binding.tvPlay.setOnClickListener {
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", "https://www.gamezop.com/?id=3178")
            startActivity(intent)
        }

        binding.tvLive.setOnClickListener {
            navigateInLive()
        }
    }

    private fun navigateInLive() {
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
        findNavController().navigate(R.id.liveFragment)
    }
}