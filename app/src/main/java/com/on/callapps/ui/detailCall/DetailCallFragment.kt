package com.on.callapps.ui.detailCall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.on.callapps.utils.createDialog

class DetailCallFragment : Fragment() {

    private lateinit var binding: FragmentDetailCallBinding
    private val pref by lazy { Pref(requireContext()) }
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

        binding.imgSettingsDetail.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvRate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp"))
            startActivity(intent)
        }
        binding.tvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.dolzhenko.dogfakecallapp")
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

        binding.tvShare.setOnClickListener {
            val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
            dialog.first.tvTitle.text =
                "          Watch the short video to unlock           \nthe character "

            dialog.first.btnNo.setOnClickListener {
                dialog.second.dismiss()
            }
        }
    }
}