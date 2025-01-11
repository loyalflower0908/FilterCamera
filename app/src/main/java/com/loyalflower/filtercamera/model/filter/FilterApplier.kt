package com.loyalflower.filtercamera.model.filter

import android.content.Context
import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.GPUImage
import javax.inject.Inject

/**
 * 이미지에 필터를 적용하는 클래스입니다.
 * @param context [Context] 객체
 * @param filterManager 필터 제공을 위한 [FilterManager] 객체
 */
class FilterApplier @Inject constructor(private val context: Context, private val filterManager: FilterManager) {

    private lateinit var gpuImage: GPUImage

    /**
     * GPUImage 객체를 초기화합니다.
     */
    fun setup() {
        gpuImage = GPUImage(context)
    }

    /**
     * 이미지에 지정된 필터를 적용합니다.
     * @param bitmap 필터를 적용할 [Bitmap] 객체
     * @param filterType 적용할 필터 종류
     * @return 필터가 적용된 [Bitmap] 객체
     */
    fun applyFilter(bitmap: Bitmap, filterType: FilterType): Bitmap {
        gpuImage.setImage(bitmap)
        gpuImage.setFilter(filterManager.getFilter(filterType))
        return gpuImage.bitmapWithFilterApplied
    }
}