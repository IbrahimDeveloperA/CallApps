package com.on.callapps.ui.call


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Insets.add
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.on.callapps.databinding.FragmentVideoCallBinding


class VideoCallFragment : Fragment() {

    private lateinit var binding: FragmentVideoCallBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentVideoCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
