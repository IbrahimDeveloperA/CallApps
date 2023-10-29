package com.on.callapps.ui.call

import android.Manifest
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
import com.on.callapps.utils.format

class CallFragment : Fragment() {

    private lateinit var binding: FragmentCallBinding
    private var isCall = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private var millisseconds = 0
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

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.ibAccept.setOnClickListener {
            isCall = true
            trueOrFalse()
        }
        binding.ibReset.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvVideoCall.setOnClickListener {
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
        binding.tvCharacters.setOnClickListener{
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvChat.setOnClickListener {
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
        when (pref.saveContact) {
            1 -> {
                binding.tvName.text = "Max"
                binding.ivLogo.setImageResource(R.drawable.ic_image_dog)
            }

            2 -> {
                binding.tvName.text = "Rocky"
                binding.ivLogo.setImageResource(R.drawable.c2)
            }

            3 -> {
                binding.tvName.text = "Charlie"
                binding.ivLogo.setImageResource(R.drawable.c3)
            }

            4 -> {
                binding.tvName.text = "Milo"
                binding.ivLogo.setImageResource(R.drawable.c4)
            }
        }

        trueOrFalse()

        binding.ibResetCallRed.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.progressFragment)
        }

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
        millisseconds += 10
        val minutes = (millisseconds % 3600000) / 60000
        val secs = (millisseconds % 60000) / 1000
        val timeString = String.format("%02d:%02d", minutes, secs)
        binding.tvTimer.text = timeString
    }
}