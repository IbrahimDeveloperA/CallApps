package com.on.callapps.ui.contact

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentContactBinding
import com.on.callapps.ui.contact.adapter.ContactAdapter
import com.on.callapps.utils.Key
import com.on.callapps.utils.createDialog

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    private val pref by lazy { Pref(requireContext()) }
    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        val ratingText = pref.getNameVolume(Key.KEY_ONE)
        binding.tvRatingTwo.text = "$ratingText/4"

        binding.contactFour.setOnClickListener {
            onClick(ratingText ?: 0, Key.KEY_ONE)
        }

        val rating3 = pref.getNameVolume(Key.KEY_TWO)
        binding.tvRatingThree.text  = "$rating3/4"

        binding.contactFive.setOnClickListener {
            rating3?.let { it1 -> onClick(it1, Key.KEY_TWO) }
        }

        val rating4 = pref.getNameVolume(Key.KEY_THREE)
        binding.tvRateTwo.text = "$rating4/4"

        binding.contactSix.setOnClickListener {
             onClick(rating4 ?: 0, Key.KEY_THREE)
        }
    }

    private fun onClick(text: Int, key: String) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        dialog.first.tvNumber.text = "$text/4"
        dialog.first.btnYes.setOnClickListener {

            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(
                requireContext(),
                "ca-app-pub-3940256099942544/5224354917",
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError.toString().let { it1 -> Log.d("ololoFailed", it1) }
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d("ololoLoaded", "Ad was loaded.")
                        rewardedAd = ad
                    }
                })
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    val newText = text + 1
                    pref.saveNumVolume(key, newText)
                    dialog.first.tvNumber.text = newText.toString()
                    rewardedAd = null
                }
            }
            dialog.second.dismiss()
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
        }
    }
}