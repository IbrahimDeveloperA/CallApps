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
import com.on.callapps.databinding.FragmentContactBinding
import com.on.callapps.utils.InterAd
import com.on.callapps.utils.Key
import com.on.callapps.utils.RewardAd

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    private val rewardAd by lazy { RewardAd(requireContext(), requireActivity()) }


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
            requireContext(), R.animator.button_click_animation
        )
        binding.cardView.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(), R.animator.button_click_animation
        )
        binding.cardView2.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(), R.animator.button_click_animation
        )
        binding.cardView3.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(), R.animator.button_click_animation
        )
        binding.cardView4.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(), R.animator.button_click_animation
        )
    }

    private fun one() {
        binding.contactOne.setOnClickListener {
            pref.saveContact = 1
            check()
        }
    }

    private fun check() {
        if (pref.getNameVolume(Key.KEY_TWO) == 2) {
            whenTwo()
        }
        if (pref.getNameVolume(Key.KEY_FOUR) == 4) {
            whenFour()
        }
        if (pref.getNameVolume(Key.KEY_THREE) == 3) {
            whenThree()
        }
    }

    private fun two() {
        val ratingText = pref.getNameVolume(Key.KEY_TWO)
        binding.tvRatingTwo.text = getString(R.string._2, ratingText)

        if (pref.getNameVolume(Key.KEY_TWO) == 2) {
            binding.contactFour.setOnClickListener {
                pref.saveContact = 2
                whenTwo()
            }
        } else {
            binding.contactFour.setOnClickListener {
                rewardAd.onClick(
                    pref.getNameVolume(Key.KEY_TWO),
                    Key.KEY_TWO,
                    two = { two() },
                    whenTwo = { whenTwo() },
                    tvRatingTwo = binding.tvRatingTwo
                )
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
                if (pref.getNameVolume(Key.KEY_THREE) == 3) {
                    binding.imgOkTwoThree.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_TWO) == 2) {
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
                if (pref.getNameVolume(Key.KEY_FOUR) == 4) {
                    binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_THREE) == 3) {
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
                if (pref.getNameVolume(Key.KEY_TWO) == 2) {
                    binding.imgOkTwoTwo.setImageResource(R.drawable.ic_empty_circle)
                }
                if (pref.getNameVolume(Key.KEY_FOUR) == 4) {
                    binding.imgOkTwoFour.setImageResource(R.drawable.ic_empty_circle)
                }
            }
        }
    }


    private fun four() {
        val rating4 = pref.getNameVolume(Key.KEY_FOUR)
        binding.tvRateTwo.text = getString(R.string._4, rating4)

        if (pref.getNameVolume(Key.KEY_FOUR) == 4) {
            binding.contactSix.setOnClickListener {
                pref.saveContact = 4
                whenFour()
            }
        } else {
            binding.contactSix.setOnClickListener {
                rewardAd.onClick(
                    pref.getNameVolume(Key.KEY_FOUR),
                    Key.KEY_FOUR,
                    tvRateTwo = binding.tvRateTwo,
                    four = { four() },
                    whenFour = { whenFour() }
                )
            }
        }
    }

    private fun three() {
        val rating3 = pref.getNameVolume(Key.KEY_THREE)
        binding.tvRatingThree.text = getString(R.string._3, rating3)

        if (pref.getNameVolume(Key.KEY_THREE) == 3) {
            binding.contactFive.setOnClickListener {
                pref.saveContact = 3
                whenThree()
            }
        } else {
            binding.contactFive.setOnClickListener {
                rewardAd.onClick(
                    pref.getNameVolume(Key.KEY_THREE),
                    Key.KEY_THREE,
                    tvRatingThree = binding.tvRatingThree,
                    whenThree = { whenThree() },
                    three = { three() }
                )
            }
        }
    }
}