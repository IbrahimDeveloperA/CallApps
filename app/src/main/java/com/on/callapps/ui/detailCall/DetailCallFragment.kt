package com.on.callapps.ui.detailCall

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
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogChooseBinding
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentDetailCallBinding
import com.on.callapps.utils.InterAd
import com.on.callapps.utils.Key
import com.on.callapps.utils.RewardAd
import com.on.callapps.utils.createDialog

class DetailCallFragment : Fragment() {

    private lateinit var binding: FragmentDetailCallBinding
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    private val rewardAd by lazy { RewardAd(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interAd.loadAd()
        binding.constCall.setOnClickListener {
            interAd.showInter()
            val dialog = requireContext().createDialog(DialogChooseBinding::inflate)
            dialog.first.tvChoose.text =
                "                                 Choose:                                "
            dialog.first.btnChat.setOnClickListener {
                interAd.showInter()
                findNavController().popBackStack()
                findNavController().navigate(R.id.messageFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnCall.setOnClickListener {
                interAd.showInter()
                findNavController().popBackStack()
                findNavController().navigate(R.id.callFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnLive.setOnClickListener {
                interAd.showInter()
                findNavController().popBackStack()
                findNavController().navigate(R.id.liveFragment)
                dialog.second.dismiss()
            }

            dialog.first.btnVideoCall.setOnClickListener {
                interAd.showInter()
                findNavController().popBackStack()
                findNavController().navigate(R.id.videoCallFragment)
                dialog.second.dismiss()
            }
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.constPlay.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.gamezop))
            startActivity(intent)
        }

        binding.contact1.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.contactFragment)
        }
        binding.contact2.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_ONE) == 2) {
                interAd.showInter()
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_ONE)?.let { it1 -> onClick(it1, Key.KEY_ONE) }
            }
        }
        binding.contact3.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_TWO) == 2) {
                interAd.showInter()
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_TWO)?.let { it1 -> onClick(it1, Key.KEY_TWO) }
            }
        }
        binding.contact4.setOnClickListener {
            if (pref.getNameVolume(Key.KEY_THREE) == 2) {
                interAd.showInter()
                findNavController().navigate(R.id.contactFragment)
            } else {
                pref.getNameVolume(Key.KEY_THREE)?.let { it1 -> onClick(it1, Key.KEY_THREE) }
            }
        }

        binding.imgSettingsDetail.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.imageView.setOnClickListener {
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

        binding.imgSettingsDetail.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.imageView.setOnClickListener {
            interAd.showInter()
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
                dialog.second.dismiss()
            }
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
            rewardAd.rewardedAd = null
        }
    }
}