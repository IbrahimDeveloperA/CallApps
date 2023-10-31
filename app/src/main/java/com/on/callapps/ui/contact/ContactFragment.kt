package com.on.callapps.ui.contact

import android.animation.AnimatorInflater
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentContactBinding
import com.on.callapps.utils.InterAd
import com.on.callapps.utils.Key
import com.on.callapps.utils.RewardAd
import com.on.callapps.utils.createDialog

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    private val rewardAd by lazy { RewardAd(requireContext()) }


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
        check()
        animBtn()
        one()
        two()
        three()
        four()
    }

    private fun animBtn() {
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
    }

    private fun one() {
        binding.contactOne.setOnClickListener {
            pref.saveContact = 1
            check()
        }
    }

    private fun check() {
        if (pref.getNameVolume(Key.KEY_ONE) == 2) {
            whenTwo()
        }
        if (pref.getNameVolume(Key.KEY_THREE) == 4) {
            whenFour()
        }
        if (pref.getNameVolume(Key.KEY_TWO) == 3) {
            whenThree()
        }
    }

    private fun two() {
        val ratingText = pref.getNameVolume(Key.KEY_ONE)
        binding.tvRatingTwo.text = "$ratingText/2"

        if (pref.getNameVolume(Key.KEY_ONE) == 2) {
            binding.contactFour.setOnClickListener {
                pref.saveContact = 2
                whenTwo()
            }
        } else {
            binding.contactFour.setOnClickListener {
                pref.getNameVolume(Key.KEY_ONE)
                    ?.let { it1 -> onClick(it1, Key.KEY_ONE) }
            }
        }

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
                if (pref.getNameVolume(Key.KEY_TWO) == 3) {
                    binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                }
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
                if (pref.getNameVolume(Key.KEY_THREE) == 4) {
                    binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_TWO) == 3) {
                    binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                }
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
                if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_THREE) == 4) {
                    binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                }
            }
        }
    }


    private fun four() {
        val rating4 = pref.getNameVolume(Key.KEY_THREE)
        binding.tvRateTwo.text = "$rating4/4"

        if (pref.getNameVolume(Key.KEY_THREE) == 4) {
            binding.contactSix.setOnClickListener {
                pref.saveContact = 4
                whenFour()
            }
        } else {
            binding.contactSix.setOnClickListener {
                pref.getNameVolume(Key.KEY_THREE)?.let { it2 ->
                    onClick(
                        it2,
                        Key.KEY_THREE,
                    )
                }
            }
        }
    }

    private fun three() {
        val rating3 = pref.getNameVolume(Key.KEY_TWO)
        binding.tvRatingThree.text = "$rating3/3"

        if (pref.getNameVolume(Key.KEY_TWO) == 3) {
            binding.contactFive.setOnClickListener {
                pref.saveContact = 3
                whenThree()
            }
        } else {
            binding.contactFive.setOnClickListener {
                pref.getNameVolume(Key.KEY_TWO)?.let { it1 ->
                    onClick(
                        it1,
                        Key.KEY_TWO,
                    )
                }
            }
        }
    }

    private fun onClick(text: Int, key: String) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        when (key) {
            Key.KEY_ONE -> {
                dialog.first.tvNumber.text = "${pref.getNameVolume(Key.KEY_ONE)}/2"
            }

            Key.KEY_TWO -> {
                dialog.first.tvNumber.text = "${pref.getNameVolume(Key.KEY_TWO)}/3"
            }

            Key.KEY_THREE -> {
                dialog.first.tvNumber.text = "${pref.getNameVolume(Key.KEY_THREE)}/4"
            }
        }

        rewardAd.loadAd(text, key, dialog)

        dialog.first.btnYes.setOnClickListener {
            rewardAd.rewardedAd?.show(requireActivity()) {
                pref.saveNumVolume(key, text + 1)
                if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                    whenTwo()
                    two()
                }
                if (pref.getNameVolume(Key.KEY_THREE) == 4) {
                    whenFour()
                    four()
                }
                if (pref.getNameVolume(Key.KEY_TWO) == 3) {
                    whenThree()
                    three()

                }
                binding.tvRatingTwo.text = "${pref.getNameVolume(Key.KEY_ONE)}/2"
                binding.tvRatingThree.text = "${pref.getNameVolume(Key.KEY_TWO)}/3"
                binding.tvRateTwo.text = "${pref.getNameVolume(Key.KEY_THREE)}/4"
                dialog.second.dismiss()
            }
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
            rewardAd.rewardedAd = null
        }
    }


}