package com.on.callapps.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.tvCharecters.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }

        binding.tvPlay.setOnClickListener{
            findNavController().navigate(R.id.playGameFragment)
        }
    }
}