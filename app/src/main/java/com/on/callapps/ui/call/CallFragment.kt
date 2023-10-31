package com.on.callapps.ui.call

import android.Manifest
import android.animation.AnimatorInflater
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentCallBinding
import com.on.callapps.utils.InterAd
import com.on.callapps.utils.format

class CallFragment : Fragment() {

    private lateinit var binding: FragmentCallBinding
    private var isCall = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private var milliseconds = 0
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            updateTimer()
            handler.postDelayed(this, 1)
        }
    }
    private val pref by lazy { Pref(requireContext()) }
    private val requestCodeCameraPermission = 1001
    private var microphone = true
    private var audioLevel = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interAd.loadAd()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        animBtn()
        initListener()
        when (pref.saveContact) {
            1 -> {
                binding.tvName.text = getString(R.string.max)
                binding.ivLogo.setImageResource(R.drawable.ic_image_dog)
            }

            2 -> {
                binding.tvName.text = getString(R.string.rocky)
                binding.ivLogo.setImageResource(R.drawable.c2)
            }

            3 -> {
                binding.tvName.text = getString(R.string.charlie)
                binding.ivLogo.setImageResource(R.drawable.c3)
            }

            4 -> {
                binding.tvName.text = getString(R.string.milo)
                binding.ivLogo.setImageResource(R.drawable.c4)
            }
        }
        trueOrFalse()
    }

    private fun initListener() {
        binding.ibResetCallRed.setOnClickListener {
            interAd.showInter()
            mediaPlayer.stop()
            findNavController().popBackStack()
            findNavController().navigate(R.id.progressFragment)
        }
        binding.ibAccept.setOnClickListener {
            interAd.showInter()
            isCall = true
            trueOrFalse()
        }
        binding.ibReset.setOnClickListener {
            interAd.showInter()
            findNavController().popBackStack()
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvVideoCall.setOnClickListener {
            interAd.showInter()
            navigateInCall()
            mediaPlayer.stop()
        }
        binding.tvMicrophone.setOnClickListener {
            microphone = if (!microphone) {
                binding.tvMicrophone.setImageResource(R.drawable.microphone)
                true
            } else {
                binding.tvMicrophone.setImageResource(R.drawable.microphone_off)
                false
            }
        }
        binding.tvCharacters.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvChat.setOnClickListener {
            interAd.showInter()
            findNavController().navigate(R.id.messageFragment)
        }
        binding.tvAudioLevel.setOnClickListener {
            when (audioLevel) {
                100 -> {
                    binding.tvAudioLevel.setImageResource(R.drawable.audio_level_50)
                    audioLevel = 50
                }

                50 -> {
                    binding.tvAudioLevel.setImageResource(R.drawable.audio_level_0)
                    audioLevel = 0
                }

                0 -> {
                    binding.tvAudioLevel.setImageResource(R.drawable.audio_level_100)
                    audioLevel = 100
                }
            }
        }
    }

    private fun animBtn() {
        binding.ibAccept.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.ibReset.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvVideoCall.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvMicrophone.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvAudioLevel.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvChat.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.tvCharacters.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.ibResetCallRed.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
    }

    private fun navigateInCall() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            showScanner()
        }
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    private fun showScanner() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.videoCallFragment)
    }

    private fun trueOrFalse() {
        if (!isCall) {
            mediaPlayer2 = MediaPlayer.create(requireContext(), R.raw.audio_call_bell_v3)
            mediaPlayer2.isLooping = true
            mediaPlayer2.start()
            binding.llTime.visibility = View.GONE
            binding.clBtn.visibility = View.GONE
            binding.llCenter.visibility = View.VISIBLE
            binding.clAcceptReset.visibility = View.VISIBLE
        } else {
            mediaPlayer2.stop()
            when (pref.saveContact) {
                1 -> {
                    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_call_character_1)
                }

                2 -> {
                    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_call_character_2)
                }

                3 -> {
                    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_call_character_3)
                }

                4 -> {
                    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_call_character_4)
                }
            }
            mediaPlayer.isLooping = true
            mediaPlayer.start()
            binding.llTime.visibility = View.VISIBLE
            binding.clBtn.visibility = View.VISIBLE
            binding.llCenter.visibility = View.GONE
            binding.clAcceptReset.visibility = View.GONE
            handler.postDelayed(runnable, 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer2.stop()
    }

    private fun updateTimer() {
        milliseconds += 10
        val minutes = (milliseconds % 3600000) / 60000
        val secs = (milliseconds % 60000) / 1000
        val timeString = String.format("%02d:%02d", minutes, secs)
        binding.tvTimer.text = timeString
    }
}