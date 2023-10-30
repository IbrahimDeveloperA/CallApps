package com.on.callapps.ui.call


import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.media.MediaPlayer
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
import com.on.callapps.databinding.FragmentVideoCallBinding
import com.on.callapps.utils.InterAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


class VideoCallFragment : Fragment() {

    private lateinit var binding: FragmentVideoCallBinding
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var mediaPlayer2: MediaPlayer
    private var scannedValue = ""
    private val pref by lazy { Pref(requireContext()) }
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupControls()
        interAd.loadAd()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.ibAccept.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.ibResetCall.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.ibAccept.setOnClickListener {
            interAd.showInter()
            findNavController().popBackStack()
            findNavController().navigate(R.id.detailVideoCallFragment)
        }

        mediaPlayer2 = MediaPlayer.create(requireContext(), R.raw.audio_call_bell_v3)
        mediaPlayer2.isLooping = true
        mediaPlayer2.start()

        binding.ibResetCall.setOnClickListener {
            interAd.showInter()
            findNavController().popBackStack()
            findNavController().navigate(R.id.contactFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer2.stop()
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}