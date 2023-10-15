package com.on.callapps.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentLogoBinding

class LogoFragment : Fragment() {
    private lateinit var binding: FragmentLogoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()
        val maxProgress = 100
        val animationDuration = 7000

        var currentProgress = 0
        val progressIncrement = maxProgress / (animationDuration / 200)

        val runnable = object : Runnable {
            override fun run() {
                if (currentProgress < maxProgress) {
                    currentProgress += progressIncrement
                    binding.progressBar.progress = currentProgress
                    binding.tvProcent.text = "$currentProgress%"
                    handler.postDelayed(this, animationDuration.toLong() / maxProgress)
                } else {
                    binding.progressBar.progress = maxProgress
                    binding.tvProcent.text = "$maxProgress%"
                    binding.progressBar.visibility = View.GONE
                    binding.tvProcent.visibility = View.GONE
                    binding.btnContinue.visibility = View.VISIBLE
                    binding.tvBy.visibility = View.VISIBLE
                }
            }
        }

        handler.post(runnable)
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.imageFragment)
        }
    }
}