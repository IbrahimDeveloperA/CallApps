package com.on.callapps.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentLogoBinding

class LogoFragment : Fragment() {
    private lateinit var binding: FragmentLogoBinding

    var isStarted = false
    var primaryProgressStatus = 0

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
        var handler = Handler()
        val maxProgress = 100
        val animationDuration = 7000
        val progressIncrement = maxProgress / (animationDuration / 200)

        handler = Handler(Handler.Callback {
            if (isStarted) {
                primaryProgressStatus += progressIncrement
                if (primaryProgressStatus > maxProgress) {
                    primaryProgressStatus = maxProgress
                }
            }

            updateProgress(primaryProgressStatus)
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })

        updateProgress(primaryProgressStatus)

        if (!isStarted) {
            isStarted = true
            handler?.sendEmptyMessage(0)
        } else {
            isStarted = false
            handler?.removeMessages(0)
            binding.progressBar.visibility = View.GONE
            binding.tvProcent.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
            binding.tvBy.visibility = View.VISIBLE
        }


        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.imageFragment)
        }


    }

    private fun updateProgress(progress: Int) {
        binding.progressBar.progress = progress
        binding.tvProcent.text = "$progress%"
        if (binding.tvProcent.text == "100%") {
            binding.progressBar.visibility = View.GONE
            binding.tvProcent.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
            binding.tvBy.visibility = View.VISIBLE
        }
    }
}