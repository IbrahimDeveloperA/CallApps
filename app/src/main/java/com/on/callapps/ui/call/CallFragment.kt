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
import com.on.callapps.R
import com.on.callapps.databinding.FragmentCallBinding
import com.on.callapps.utils.format

class CallFragment : Fragment() {

    private lateinit var binding: FragmentCallBinding
    private var isCall = false
    private lateinit var mediaPlayer: MediaPlayer
    private var millisseconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            updateTimer()
            handler.postDelayed(this, 1)
        }
    }
    private val requestCodeCameraPermission = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibAccept.setOnClickListener {
            isCall = true
            trueOrFalse()
        }
        binding.ibReset.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ibResetCallRed.setOnClickListener {

        }
        binding.tvVideoCall.setOnClickListener {
            navigateInCall()
            mediaPlayer.stop()
        }
        trueOrFalse()

        binding.ibResetCallRed.setOnClickListener {
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
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.audio_call_bell_v3)
            mediaPlayer.start()
            binding.llTime.visibility = View.GONE
            binding.clBtn.visibility = View.GONE
            binding.llCenter.visibility = View.VISIBLE
            binding.clAcceptReset.visibility = View.VISIBLE
        } else {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_call_character_1)
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
        mediaPlayer.stop()
    }

    private fun updateTimer() {
        millisseconds += 10
        val minutes = (millisseconds % 3600000) / 60000
        val secs = (millisseconds % 60000) / 1000
        val timeString = String.format("%02d:%02d", minutes, secs)
        binding.tvTimer.text = timeString
    }
}