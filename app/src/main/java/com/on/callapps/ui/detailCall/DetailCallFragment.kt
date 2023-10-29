package com.on.callapps.ui.detailCall

import android.app.Dialog
import android.content.Intent
import android.net.Uri
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
import com.on.callapps.WebViewActivity
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogChooseBinding
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentDetailCallBinding
import com.on.callapps.utils.Key
import com.on.callapps.utils.createDialog

class DetailCallFragment : Fragment() {

    private lateinit var binding: FragmentDetailCallBinding
    private val pref by lazy { Pref(requireContext()) }
    private var rewardedAd: RewardedAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constCall.setOnClickListener {
            val dialog = requireContext().createDialog(DialogChooseBinding::inflate)
            dialog.first.tvChoose.text =
                "                                 Choose:                                "
            dialog.first.btnChat.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.messageFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnCall.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.callFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnLive.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.liveFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnVideoCall.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.videoCallFragment)
                dialog.second.dismiss()
            }
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.constPlay.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", "https://www.gamezop.com/?id=3178")
            startActivity(intent)
        }

        binding.contact1.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        binding.contact2.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_ONE)?.let { it1 -> onClick(it1, Key.KEY_ONE) }
            }
        }
        binding.contact3.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_TWO) == 2) {
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_TWO)?.let { it1 -> onClick(it1, Key.KEY_TWO) }
            }
        }
        binding.contact4.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_THREE) == 2) {
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_THREE)?.let { it1 -> onClick(it1, Key.KEY_THREE) }
            }
        }

        binding.imgSettingsDetail.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvRate.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.rate_url))
            )
            startActivity(intent)
        }
        binding.tvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_url)
            )
            startActivity(Intent.createChooser(intent, "Share"))
        }

        binding.imgSettingsDetail.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
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
    }

    private fun onClick(text: Int, key: String) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
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
        loadAd(text, key, dialog)
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
            loadAd(text, key, dialog)
            val newText = text + 1
            pref.saveNumVolume(key, newText)
            dialog.first.tvNumber.text = newText.toString()
            updateRewarded(key, dialog, text)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            rewardedAd = null
            loadAd(text, key, dialog)
        }
    }


    private fun loadAd(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) {
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
                    rewardedAd?.fullScreenContentCallback = adListener(text, key, dialog)
                }
            })
    }
}