package com.on.callapps.ui.progress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {
    private lateinit var binding: FragmentProgressBinding

    private var isStarted = false
    private var primaryProgressStatus = 0
    var handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val maxProgress = 100
        val animationDuration = 700
        val progressIncrement = maxProgress / (animationDuration / 200)

        handler = Handler(Looper.getMainLooper()) {
            if (isStarted) {
                primaryProgressStatus += progressIncrement
                if (primaryProgressStatus > maxProgress) {
                    primaryProgressStatus = maxProgress
                }
            }

            updateProgress(primaryProgressStatus)
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        }

        updateProgress(primaryProgressStatus)

        if (!isStarted) {
            isStarted = true
            handler?.sendEmptyMessage(0)
        } else {
            isStarted = false
            handler?.removeMessages(0)
            binding.progressBar.visibility = View.GONE
            binding.tvProcent.visibility = View.GONE
        }

    }

    private fun updateProgress(progress: Int) {
        binding.progressBar.progress = progress
        binding.tvProcent.text = "$progress%"
        if (binding.tvProcent.text == "100%") {
//            binding.progressBar.visibility = View.GONE
//            binding.tvProcent.visibility = View.GON
            findNavController().popBackStack()
                   findNavController().navigate(R.id.detailCallFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        handler.removeCallbacksAndMessages(null)
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStop() {
        super.onStop()
        // Уберите все сообщения из Handler
        handler.removeCallbacksAndMessages(null)
        handler.removeCallbacksAndMessages(null)
    }


}