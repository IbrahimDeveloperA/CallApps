package com.on.callapps.ui.live

import android.Manifest
import android.os.Bundle

import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.databinding.FragmentLiveBinding
import com.on.callapps.ui.live.adapter.LiveAdapter
import com.on.callapps.utils.format
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
  /*  private lateinit var cameraExecutor: ExecutorService
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    requireActivity().baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.recyclerView.adapter = adapter
        adapter.addDataWithDelay()
        binding.tvPeople.text = people.toString()
        scheduleDataUpdate()
        handler.postDelayed(runnable, 1)
   /*     if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }
        cameraExecutor = Executors.newSingleThreadExecutor()*/
        initListener()
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

 /*   private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
*/
/*    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }*/


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

  /*  override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }*/

    companion object {
        private const val TAG = "CameraXApp"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
