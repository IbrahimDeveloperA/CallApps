package com.on.callapps.ui.call

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.on.callapps.databinding.FragmentVideoCallBinding

class VideoCallFragment : Fragment() {

    private lateinit var binding: FragmentVideoCallBinding
    private lateinit var cameraManager: CameraManager
    private var cameraDevice: CameraDevice? = null
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private var backgroundHandler: Handler? = null

    private val cameraStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice?.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice?.close()
            cameraDevice = null
        }
    }
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
                openCamera()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        if (hasCameraPermission()) {
            openCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        activityResultLauncher.launch(arrayOf(Manifest.permission.CAMERA))
    }

    override fun onPause() {
        super.onPause()
        closeCamera()
    }

    private fun openCamera() {
        cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // Проверьте, есть ли разрешение на использование камеры
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Получите список доступных камер
            val cameraIds = cameraManager.cameraIdList

            // Проверьте, есть ли передняя камера
            val cameraId = cameraIds.firstOrNull { id ->
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val cameraFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                cameraFacing == CameraCharacteristics.LENS_FACING_FRONT
            }

            if (cameraId != null) {
                cameraManager.openCamera(cameraId, cameraStateCallback, backgroundHandler)
            } else {
                // Передняя камера не найдена, выполните соответствующие действия
                Toast.makeText(
                    requireActivity().baseContext,
                    "Front camera not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Если разрешение не предоставлено, запросите его
            activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun createCameraPreviewSession() {
        try {
            val texture = binding.viewFinder.surfaceTexture

            val surface = Surface(texture)
            val captureRequestBuilder =
                cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder?.addTarget(surface)

            cameraDevice?.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        cameraCaptureSession = session
                        captureRequestBuilder?.set(
                            CaptureRequest.CONTROL_AF_MODE,
                            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                        )

                        val captureRequest = captureRequestBuilder?.build()
                        if (captureRequest != null) {
                            cameraCaptureSession.setRepeatingRequest(
                                captureRequest,
                                null,
                                backgroundHandler
                            )
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        // Обработка ошибки настройки CaptureSession
                    }
                },
                backgroundHandler
            )
        } catch (e: CameraAccessException) {
        }
    }


    private fun closeCamera() {
        cameraCaptureSession.close()
        cameraDevice?.close()
        cameraDevice = null
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 1
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
