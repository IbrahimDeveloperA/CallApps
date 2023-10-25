package com.on.callapps.ui.live

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle

import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.on.callapps.R
import com.on.callapps.databinding.FragmentLiveBinding
import com.on.callapps.ui.live.adapter.LiveAdapter
import com.on.callapps.utils.format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class LiveFragment : Fragment() {

    private lateinit var binding: FragmentLiveBinding
    private val adapter by lazy { LiveAdapter() }
    private var people = 10
    private var millisseconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            updateTimer()
            handler.postDelayed(this, 1)
        }
    }
    private var favorite = false
    private var emoji = false
    private var like = false
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupControls()
        binding.recyclerView.adapter = adapter
        adapter.addDataWithDelay()
        binding.tvPeople.text = people.toString()
        scheduleDataUpdate()
        handler.postDelayed(runnable, 1)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        initListener()

        binding.ibEndStream.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.progressFragment)
        }
    }

    private fun initListener() {
        binding.ibFavorite.setOnClickListener {
            favorite = if (favorite) {
                binding.ibFavorite.setImageResource(R.drawable.ic_heart_life)
                false
            } else {
                binding.ibFavorite.setImageResource(R.drawable.ic_favorite)
                true
            }
        }
        binding.tvEmoji.setOnClickListener {
            emoji = if (emoji) {
                binding.tvEmoji.setBackgroundResource(R.drawable.ic_bg)
                false
            } else {
                binding.tvEmoji.setBackgroundResource(R.drawable.ic_bg_white)
                true
            }
        }
        binding.ibLike.setOnClickListener {
            like = if (like) {
                binding.ibLike.setImageResource(R.drawable.ic_like_life)
                false
            } else {
                binding.ibLike.setImageResource(R.drawable.ic_like)
                true
            }
        }
        binding.btnPlay.setOnClickListener {
            binding.motionLl.isVisible = false
            binding.ibEndStream.isVisible = true
        }
    }

    private fun updateTimer() {
        millisseconds += 10
        val minutes = (millisseconds % 3600000) / 60000
        val secs = (millisseconds % 60000) / 1000
        val timeString = String.format("%02d:%02d", minutes, secs)
        binding.tvLife.text = "LIVE $timeString"
    }

    private fun scheduleDataUpdate() {
        val handler = Handler(Looper.getMainLooper())
        val delayMillis = (2000..5000).random()

        handler.postDelayed({
            people += (5..500).random()
            binding.tvPeople.text = people.toString()
            scheduleDataUpdate()
        }, delayMillis.toLong())
    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .setFacing(CameraSource.CAMERA_FACING_FRONT)
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(requireContext(), "OPLOLOL", Toast.LENGTH_SHORT).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        cameraSource.stop()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}
