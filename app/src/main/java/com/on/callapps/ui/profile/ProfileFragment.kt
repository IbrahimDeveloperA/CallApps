package com.on.callapps.ui.profile

import android.Manifest
import android.animation.AnimatorInflater
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.on.callapps.R
import com.on.callapps.WebViewActivity
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentProfileBinding
import com.on.callapps.utils.InterAd

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val requestCodeCameraPermission = 1001
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }

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

        interAd.loadAd()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.tvChat.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.messageFragment)
        }
        binding.imgSettings.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.tvVideoCall.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.videoCallFragment)
        }
        binding.btnBack.setOnClickListener {
            interAd.showInter()
            findNavController().navigateUp()
        }
        binding.tvRate.setOnClickListener {
            interAd.showInter()
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.rate_url))
            )
            startActivity(intent)
        }
        binding.tvShare.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_url)
            )
            startActivity(Intent.createChooser(intent, "Share"))
        }

        binding.tvCharecters.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvCall.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvChat.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvVideoCall.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvLive.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvPlay.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvShare.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvRate.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.btnBack.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.imgSettings.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )

        binding.tvCharecters.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvCall.setOnClickListener {
            interAd.showInter()
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
            interAd.showInter()
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.gamezop))
            startActivity(intent)
        }

        binding.tvLive.setOnClickListener {
            interAd.showInter()
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