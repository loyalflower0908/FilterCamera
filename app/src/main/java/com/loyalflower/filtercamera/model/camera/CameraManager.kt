package com.loyalflower.filtercamera.model.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * CameraX 라이브러리를 사용하여 카메라를 관리하는 클래스입니다.
 * 카메라 설정, 미리보기, 사진 촬영 기능을 제공합니다.
 *
 * @param context [Context] 객체
 */
class CameraManager @Inject constructor(private val context: Context) {

    private var cameraProvider: ProcessCameraProvider? = null
    private var previewView: PreviewView? = null
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    /**
     * 카메라를 설정하고 LifecycleOwner에 바인딩합니다.
     *
     * @param lifecycleOwner 카메라를 바인딩할 [LifecycleOwner]
     */
    fun setupCamera(lifecycleOwner: LifecycleOwner) {
        cameraExecutor = Executors.newSingleThreadExecutor()

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindPreview(lifecycleOwner)
            } catch (exc: Exception) {
                Log.e("CameraManager", "Failed to get camera provider", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    /**
     * 카메라 미리보기를 설정하고 LifecycleOwner에 바인딩합니다.
     *
     * @param lifecycleOwner 카메라를 바인딩할 [LifecycleOwner]
     */
    private fun bindPreview(lifecycleOwner: LifecycleOwner) {
        val preview = Preview.Builder()
            .setTargetRotation(Surface.ROTATION_0) // 회전 설정
            .build()

        previewView?.let { previewView ->
            preview.surfaceProvider = previewView.surfaceProvider // PreviewView에 미리보기 연결
        }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK) // 후면 카메라 사용
            .build()

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // 지연 시간 최소화 모드
            .setTargetRotation(Surface.ROTATION_0) // 회전 설정
            .build()

        try {
            cameraProvider?.let { provider ->
                provider.unbindAll() // 기존 바인딩 해제
                provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture // 이미지 캡쳐 객체 바인딩
                )
            }
        } catch (exc: Exception) {
            Log.e("CameraManager", "Use case binding failed", exc)
        }
    }

    /**
     * PreviewView를 설정합니다.
     *
     * @param previewView 카메라 미리보기를 표시할 [PreviewView]
     */
    fun setupPreview(previewView: PreviewView) {
        this.previewView = previewView
    }

    /**
     * 사진을 촬영하고 [onPhotoTaken] 콜백을 통해 Bitmap으로 변환된 이미지를 전달합니다.
     *
     * @param onPhotoTaken 촬영된 사진을 처리할 콜백 함수
     */
    fun takePhoto(onPhotoTaken: (Bitmap) -> Unit) {
        imageCapture.takePicture(
            cameraExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraManager", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onCaptureSuccess(image: ImageProxy) {
                    val rotationDegrees = image.imageInfo.rotationDegrees
                    val bitmap = image.toBitmap() // ImageProxy를 Bitmap으로 변환
                    val rotatedBitmap = rotateBitmap(bitmap, rotationDegrees) // 회전 정보에 따라 Bitmap 회전
                    onPhotoTaken(rotatedBitmap) // 콜백 함수 호출
                    image.close()
                }
            }
        )
    }

    /**
     * Bitmap 이미지를 주어진 각도만큼 회전합니다.
     *
     * @param bitmap 회전할 [Bitmap] 이미지
     * @param degrees 회전 각도 (단위: 도)
     * @return 회전된 [Bitmap] 이미지
     */
    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * 카메라를 해제하고 관련 리소스를 정리합니다.
     */
    fun releaseCamera() {
        cameraProvider?.unbindAll()
        previewView = null
        cameraProvider = null
        cameraExecutor.shutdown()
    }

    /**
     * [Context] 객체를 가져옵니다.
     * @return [Context]
     */
    fun getContext(): Context {
        return context
    }
}