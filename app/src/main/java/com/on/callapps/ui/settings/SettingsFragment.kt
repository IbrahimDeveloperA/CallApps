package com.on.callapps.ui.settings

import android.animation.AnimatorInflater
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
import com.on.callapps.WebViewActivity
import com.on.callapps.databinding.FragmentSettingsBinding
import com.on.callapps.utils.InterAd

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interAd.loadAd()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnBack.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvPrivacy.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.btnPlay.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvLicence.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvRate.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvShare.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvMoreApp.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.btnBack.setOnClickListener {
            interAd.showInter()
            findNavController().navigateUp()
        }

        binding.tvPrivacy.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.privacy_url))
            startActivity(intent)
        }
        binding.btnPlay.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.gamezop))
            startActivity(intent)
        }

        binding.tvLicence.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.license_url))
            startActivity(intent)
        }
        binding.tvRate.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.rate_url)))
            startActivity(intent)
        }
        binding.tvMoreApp.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.more_app_url)))
            startActivity(intent)
        }
        binding.tvShare.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_url))
            startActivity(Intent.createChooser(intent, "Share"))
        }

        binding.btnPlay.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.playGameFragment)
        }
    }
}