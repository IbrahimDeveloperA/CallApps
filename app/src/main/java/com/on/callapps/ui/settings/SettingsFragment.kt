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
        animBtn()
        initListener()
    }

    private fun animBtn() {
        binding.btnBack.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llPrivacy.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llPlayGames.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llLicence.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llRateUs.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llShare.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.llMoreApps.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            interAd.showInter()
            findNavController().navigateUp()
        }

        binding.llPrivacy.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.privacy_url))
            startActivity(intent)
        }

        binding.llLicence.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.license_url))
            startActivity(intent)
        }
        binding.llRateUs.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.rate_url)))
            startActivity(intent)
        }
        binding.llMoreApps.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.more_app_url)))
            startActivity(intent)
        }
        binding.llShare.setOnClickListener {
            interAd.showInter()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_url))
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

        binding.llPlayGames.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.playGameFragment)
        }
    }
}