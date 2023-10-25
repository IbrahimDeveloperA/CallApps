package com.on.callapps.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val requestCodeCameraPermission = 1001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvChat.setOnClickListener {
            findNavController().navigate(R.id.messageFragment)
        }
        binding.imgSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvCharecters.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }
        binding.tvCall.setOnClickListener {
           findNavController().navigate(R.id.action_profile_call)
        }

        binding.tvPlay.setOnClickListener{
            findNavController().navigate(R.id.playGameFragment)
        }

        binding.tvLive.setOnClickListener {
            navigateInLive()
        }
    }

    private fun navigateInLive() {
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
        findNavController().navigate(R.id.liveFragment)
    }
}