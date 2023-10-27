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
    private var bool = false
    private var boolFour = false
    private var boolTwo = false
    private var boolOne = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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
        pref.saveContact
        whenChangeContact()

        one()

        two()

        three()

        four()
    }

    private fun whenChangeContact() {
        when (pref.saveContact) {
            1 -> {
                binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOk.setImageResource(R.drawable.img_ok)
            }

            2 -> {
                binding.imgOkTwoTwo.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
            }

            3 -> {
                binding.imgOkTwoThree.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
            }

            4 -> {
                binding.imgOkTwoFour.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
            }
        }
    }

    private fun one() {
        binding.contactOne.setOnClickListener {
            boolOne = if (!boolOne) {
                true
            } else {
                binding.imgOk.setImageResource(R.drawable.img_ok)
                pref.saveContact = 1
                whenChangeContact()
                false
            }
        }
    }

    private fun two() {
        val ratingText = pref.getNameVolume(Key.KEY_ONE)
        binding.tvRatingTwo.text = "$ratingText/4"


        if (pref.getNameVolume(Key.KEY_ONE) == 4) {

            binding.contactFour.setOnClickListener {
                boolTwo = if (!boolTwo) {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                    true
                } else {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.img_ok)
                    pref.saveContact = 2
                    whenChangeContact()
                    false
                }
            }
        } else {
            binding.contactFour.setOnClickListener {
                ratingText?.let { it1 -> onClick(it1, Key.KEY_ONE) }
            }
        }

    }

    private fun four() {
        val rating4 = pref.getNameVolume(Key.KEY_THREE)
        binding.tvRateTwo.text = "4/4"
        binding.imgOkTwoFour.setOnClickListener {
            boolFour = if (!boolFour) {
                binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                true
            } else {
                binding.imgOkTwoFour.setImageResource(R.drawable.img_ok)
                pref.saveContact = 4
                whenChangeContact()
                false
            }
        }

        if (binding.tvRateTwo.text == "4/4") {
            binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
            boolFour = true
        } else {
            binding.contactSix.setOnClickListener {
                rating4?.let { it1 -> onClick(it1, Key.KEY_THREE) }
            }
        }
    }

    private fun three() {
        val rating3 = pref.getNameVolume(Key.KEY_TWO)
        binding.tvRatingThree.text = "4/4"
        binding.imgOkTwoThree.setOnClickListener {
            bool = if (!bool) {
                binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                true
            } else {
                binding.imgOkTwoThree.setImageResource(R.drawable.img_ok)
                pref.saveContact = 3
                whenChangeContact()
                false
            }
        }

        if (binding.tvRatingThree.text == "4/4") {
            binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
            bool = true
        } else {
            binding.contactFive.setOnClickListener {
                rating3?.let { it1 -> onClick(it1, Key.KEY_TWO) }
            }
        }
    }

    private fun onClick(text: Int, key: String) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        dialog.first.tvNumber.text = "$text/4"
        dialog.first.btnYes.setOnClickListener {

            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(requireContext(),
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