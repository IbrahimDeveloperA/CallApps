package com.on.callapps.ui.contact

import android.animation.AnimatorInflater
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.on.callapps.utils.InterAd
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
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interAd.loadAd()
        binding.btnBack.setOnClickListener {
            interAd.showInter()
            findNavController().navigateUp()
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        pref.saveContact
        if (pref.getNameVolume(Key.KEY_ONE) == 2) {
            whenTwo()
        }
        if (pref.getNameVolume(Key.KEY_THREE) == 4) {
            whenFour()
        }
        if (pref.getNameVolume(Key.KEY_TWO) == 3) {
            whenThree()
        }
        binding.btnBack.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.cardView.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.cardView2.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.cardView3.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.cardView4.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )

        one()

        two()

        three()

        four()
    }

    private fun whenFour() {
        when (pref.saveContact) {
            1 -> {
                binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOk.setImageResource(R.drawable.img_ok)
            }

            4 -> {
                binding.imgOkTwoFour.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
            }
        }
    }

    private fun one() {
        binding.contactOne.setOnClickListener {
            interAd.showInter()
            boolOne = if (!boolOne) {
                true
            } else {
                binding.imgOk.setImageResource(R.drawable.img_ok)
                pref.saveContact = 1
                if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                    whenTwo()
                }
                if (pref.getNameVolume(Key.KEY_THREE) == 4) {
                    whenFour()
                }
                if (pref.getNameVolume(Key.KEY_TWO) == 3) {
                    whenThree()
                }
                false
            }
        }
    }

    private fun whenTwo() {
        when (pref.saveContact) {
            1 -> {
                binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOk.setImageResource(R.drawable.img_ok)
            }

            2 -> {
                binding.imgOkTwoTwo.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
            }
        }
    }

    private fun whenThree() {
        when (pref.saveContact) {
            1 -> {
                binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                binding.imgOk.setImageResource(R.drawable.img_ok)
            }

            3 -> {
                binding.imgOkTwoThree.setImageResource(R.drawable.img_ok)
                binding.imgOk.setImageResource(R.drawable.ic_empty_circle)
            }
        }
    }

    private fun two() {
        val ratingText = pref.getNameVolume(Key.KEY_ONE)
        binding.tvRatingTwo.text = "$ratingText/2"

        if (pref.getNameVolume(Key.KEY_ONE) == 2) {

            binding.contactFour.setOnClickListener {
                interAd.showInter()
                boolTwo = if (!boolTwo) {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                    true
                } else {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.img_ok)
                    pref.saveContact = 2
                    whenTwo()
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
        binding.tvRateTwo.text = "$rating4/4"

        if (pref.getNameVolume(Key.KEY_ONE) == 4) {
            binding.contactSix.setOnClickListener {
                interAd.showInter()
                boolFour = if (!boolFour) {
                    binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                    true
                } else {
                    binding.imgOkTwoFour.setImageResource(R.drawable.img_ok)
                    pref.saveContact = 4
                    whenFour()
                    false
                }
            }
        } else {
            binding.contactSix.setOnClickListener {
                rating4?.let { it2 -> onClick(it2, Key.KEY_THREE) }
            }
        }
    }

    private fun three() {
        val rating3 = pref.getNameVolume(Key.KEY_TWO)
        binding.tvRatingThree.text = "$rating3/3"

        if (pref.getNameVolume(Key.KEY_TWO) == 3) {
            binding.contactFive.setOnClickListener {
                interAd.showInter()
                bool = if (!bool) {
                    binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                    true
                } else {
                    binding.imgOkTwoThree.setImageResource(R.drawable.img_ok)
                    pref.saveContact = 3
                    whenThree()
                    false
                }
            }
        } else {
            binding.contactFive.setOnClickListener {
                rating3?.let { onClick(it, Key.KEY_TWO) }
            }
        }
    }

    private fun onClick(text: Int, key: String) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        updateRewarded(key, dialog, text)
        dialog.first.progressBar.isVisible = true
        dialog.first.btnYes.isEnabled = false
        loadAd(text, key, dialog)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                dialog.first.progressBar.isVisible = false
                dialog.first.btnYes.isEnabled = true
            },
            3000L
        )
        dialog.first.btnYes.setOnClickListener {
            rewardedAd?.show(requireActivity()) {
                updateRewarded(key, dialog, text)
            }
            dialog.second.dismiss()
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
            rewardedAd = null
        }
    }

    private fun updateRewarded(
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>,
        text: Int
    ) {
        if (pref.getNameVolume(Key.KEY_ONE) == 2) {
            whenTwo()
        }
        if (pref.getNameVolume(Key.KEY_THREE) == 4) {
            whenFour()
        }
        if (pref.getNameVolume(Key.KEY_TWO) == 3) {
            whenThree()
        }
        when (key) {
            Key.KEY_ONE -> {
                dialog.first.tvNumber.text = "$text/2"
            }

            Key.KEY_TWO -> {
                dialog.first.tvNumber.text = "$text/3"
            }

            Key.KEY_THREE -> {
                dialog.first.tvNumber.text = "$text/4"
            }
        }
    }

    private fun adListener(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            rewardedAd = null
            val newText = text + 1
            pref.saveNumVolume(key, newText)
            dialog.first.tvNumber.text = newText.toString()
            binding.tvRatingTwo.text = "${pref.getNameVolume(Key.KEY_ONE)}/2"
            binding.tvRatingThree.text = "${pref.getNameVolume(Key.KEY_TWO)}/3"
            binding.tvRateTwo.text = "${pref.getNameVolume(Key.KEY_THREE)}/4"
            updateRewarded(key, dialog, text)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            rewardedAd = null
        }
    }


    private fun loadAd(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(requireContext(),
            getString(R.string.reward_key),
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { it1 -> Log.d("ololoFailed", it1) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("ololoLoaded", "Ad was loaded.")
                    rewardedAd = ad
                    rewardedAd?.fullScreenContentCallback = adListener(text, key, dialog)
                }
            })
    }
}