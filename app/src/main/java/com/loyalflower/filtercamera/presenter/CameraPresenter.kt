package com.loyalflower.filtercamera.presenter

import android.graphics.Bitmap
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyalflower.filtercamera.model.camera.CameraManager
import com.loyalflower.filtercamera.model.filter.FilterApplier
import com.loyalflower.filtercamera.model.filter.FilterManager
import com.loyalflower.filtercamera.model.filter.FilterType
import com.loyalflower.filtercamera.model.storage.ImageSaver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Camera 화면의 Presenter 역할을 하는 ViewModel 클래스입니다.
 * UI 로직과 비즈니스 로직을 연결하고, 상태를 관리합니다.
 *
 * @param cameraManager 카메라 관리를 위한 [CameraManager] 객체
 * @param imageSaver 이미지 저장을 위한 [ImageSaver] 객체
 * @param filterApplier 필터 적용을 위한 [FilterApplier] 객체
 * @param filterManager 필터 관리를 위한 [FilterManager] 객체
 */
@HiltViewModel
class CameraPresenter @Inject constructor(
    private val cameraManager: CameraManager,
    private val imageSaver: ImageSaver,
    private val filterApplier: FilterApplier,
    private val filterManager: FilterManager
) : ViewModel() {

    /** 사용자에게 보여지고 저장에 사용되는 캡쳐한 이미지 변수이다, 필터를 입히기 위한 이미지를 저장하는 [MutableState]!   */
    val capturedImage: MutableState<Bitmap?> = mutableStateOf(null)

    /** 현재 적용된 필터 타입을 저장하는 [MutableState] */
    private var currentFilterType: MutableState<FilterType> = mutableStateOf(FilterType.NONE)

    /** 필터 적용 전 원본 이미지를 저장하는 변수 */
    private var originalBitmap: Bitmap? = null

    /**
     * 카메라를 설정하고 [FilterApplier]를 초기화합니다.
     *
     * @param lifecycleOwner 카메라를 바인딩할 [LifecycleOwner]
     */
    fun setupCamera(lifecycleOwner: LifecycleOwner) {
        cameraManager.setupCamera(lifecycleOwner)
        filterApplier.setup()
    }

    /**
     * [PreviewView]를 설정합니다.
     *
     * @param previewView 카메라 미리보기를 표시할 [PreviewView]
     */
    fun setupPreview(previewView: PreviewView) {
        cameraManager.setupPreview(previewView)
    }

    /** 카메라를 해제합니다 */
    fun releaseCamera() {
        cameraManager.releaseCamera()
    }

    /**
     * 사용 가능한 필터 목록을 반환합니다.
     *
     * @return 사용 가능한 필터 목록 ([List]<[FilterType]>)
     */
    fun getAvailableFilters(): List<FilterType> = filterManager.availableFilters

    /**
     * 지정된 필터를 적용합니다.
     *
     * @param filterType 적용할 필터 종류
     */
    fun applyFilter(filterType: FilterType) {
        currentFilterType.value = filterType
        originalBitmap?.let { bitmap ->
            capturedImage.value = filterApplier.applyFilter(bitmap, filterType)
        }
    }

    /**
     * 사진을 촬영하고 필터를 적용하여 [capturedImage]에 저장합니다.
     */
    fun takePhoto() {
        cameraManager.takePhoto { bitmap ->
            originalBitmap = bitmap // 원본 이미지 저장
            viewModelScope.launch(Dispatchers.Main) {
                capturedImage.value = currentFilterType.value.let {
                    if(it == FilterType.NONE) originalBitmap else filterApplier.applyFilter(originalBitmap!!, it)
                }
            }
        }
    }

    /**
     * [capturedImage]에 저장된 이미지를 갤러리에 저장합니다.
     */
    fun saveImageToGallery() {
        capturedImage.value?.let { bitmap ->
            viewModelScope.launch {
                imageSaver.saveImage(bitmap)
                // UI 스레드에서 Toast 메시지 표시
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        cameraManager.getContext(),
                        "사진이 저장되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * 캡처된 이미지를 리셋하고, 카메라를 다시 설정합니다.
     * @param lifecycleOwner 카메라 생명주기 오너
     */
    fun resetCapturedImage(lifecycleOwner: LifecycleOwner) {
        originalBitmap = null
        capturedImage.value = null

        viewModelScope.launch(Dispatchers.Main) {
            // 약간의 딜레이를 주어 UI가 업데이트될 시간을 줍니다
            delay(100)
            setupCamera(lifecycleOwner)
        }
    }
}