package com.on.callapps.ui

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.WebViewActivity
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentLogoBinding
import com.on.callapps.utils.InterAd

class LogoFragment : Fragment() {
    private lateinit var binding: FragmentLogoBinding
    private val pref by lazy { Pref(requireContext()) }
    private var isStarted = false
    private var primaryProgressStatus = 0
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var handler = Handler(Looper.getMainLooper())
        val maxProgress = 100
        val animationDuration = 7000
        val progressIncrement = maxProgress / (animationDuration / 200)
        interAd.loadAd()

        handler = Handler(Looper.getMainLooper()) {
            if (isStarted) {
                primaryProgressStatus += progressIncrement
                if (primaryProgressStatus > maxProgress) {
                    primaryProgressStatus = maxProgress
                }
            }

            updateProgress(primaryProgressStatus)
            handler.sendEmptyMessageDelayed(0, 100)

            true
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

        updateProgress(primaryProgressStatus)

        if (!isStarted) {
            isStarted = true
            handler.sendEmptyMessage(0)
        } else {
            isStarted = false
            handler.removeMessages(0)
            binding.progressBar.visibility = View.GONE
            binding.tvProcent.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
            binding.clBy.visibility = View.VISIBLE
        }

        animBtn()
        initListener()
    }

    private fun initListener() {
        binding.btnContinue.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.imageFragment)
        }
        binding.clPrivacy.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.privacy_url))
            startActivity(intent)
        }
    }

    private fun animBtn() {
        binding.btnContinue.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.clPrivacy.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
    }

    private fun updateProgress(progress: Int) {
        binding.progressBar.progress = progress
        binding.tvProcent.text = "$progress%"
        if (binding.tvProcent.text == "100%") {
            binding.progressBar.visibility = View.GONE
            binding.tvProcent.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
            binding.clBy.visibility = View.VISIBLE
        }
    }
}