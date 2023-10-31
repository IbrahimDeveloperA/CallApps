package com.on.callapps.ui.call

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.FragmentDetailVideoCallBinding
import com.on.callapps.utils.InterAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DetailVideoCallFragment : Fragment() {

    private lateinit var binding: FragmentDetailVideoCallBinding
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailVideoCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupControls()
        interAd.loadAd()
        when (pref.saveContact) {
            1 -> {
                binding.videoView.
                setVideoURI(Uri.parse("android.resource://com.on.callapps/" +
                        "${R.raw.video_call_character1}"))
            }

            2 -> {
                binding.videoView.
                setVideoURI(Uri.parse("android.resource://com.on.callapps/" +
                        "${R.raw.video_call_character_2}"))
            }

            3 -> {
                binding.videoView.
                setVideoURI(Uri.parse("android.resource://com.on.callapps/" +
                        "${R.raw.video_call_character_3}"))
            }

            4 -> {
                binding.videoView.setVideoURI(Uri.parse("android.resource://com.on.callapps/" +
                        "${R.raw.video_call_character_4}"))
            }
        }
        binding.videoView.setOnCompletionListener { mp ->
            mp.start()
        }
        if (!binding.videoView.isPlaying) {
            binding.videoView.start()
        }
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.ibResetCallRed.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.ibResetCallRed.setOnClickListener {
            interAd.showInter()
            findNavController().popBackStack()
            findNavController().navigate(R.id.progressFragment)
        }
    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .setFacing(CameraSource.CAMERA_FACING_FRONT)
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
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
