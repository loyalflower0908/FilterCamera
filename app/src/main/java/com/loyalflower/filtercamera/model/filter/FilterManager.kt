package com.loyalflower.filtercamera.model.filter

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSphereRefractionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter
import javax.inject.Inject

/**
 * 필터를 관리하고 제공하는 클래스입니다.
 */
class FilterManager {

    /** 사용 가능한 필터 목록 */
    val availableFilters: List<FilterType> = listOf(
        FilterType.NONE,
        FilterType.GRAYSCALE,
        FilterType.SEPIA,
        FilterType.INVERT,
        FilterType.SKETCH,
        FilterType.TOON,
        FilterType.PIXEL,
        FilterType.CROSSHATCH,
        FilterType.BULGE,
        FilterType.SWIRL,
        FilterType.Sphere,
        FilterType.EMBOSS,
        FilterType.POSTER
    )

    /**
     * [filterType]에 해당하는 GPUImage 필터를 반환합니다.
     * @param filterType 가져올 필터의 종류
     * @return [filterType]에 대응하는 [GPUImageFilter] 객체
     */
    fun getFilter(filterType: FilterType): GPUImageFilter {
        return when (filterType) {
            FilterType.NONE -> GPUImageFilter()
            FilterType.GRAYSCALE -> GPUImageGrayscaleFilter()
            FilterType.SEPIA -> GPUImageSepiaToneFilter()
            FilterType.INVERT -> GPUImageColorInvertFilter()
            FilterType.SKETCH -> GPUImageSketchFilter()
            FilterType.TOON -> GPUImageToonFilter()
            FilterType.PIXEL -> GPUImagePixelationFilter()
            FilterType.CROSSHATCH -> GPUImageCrosshatchFilter()
            FilterType.BULGE -> GPUImageBulgeDistortionFilter()
            FilterType.SWIRL -> GPUImageSwirlFilter()
            FilterType.Sphere -> GPUImageSphereRefractionFilter()
            FilterType.EMBOSS -> GPUImageEmbossFilter()
            FilterType.POSTER -> GPUImagePosterizeFilter()
        }
    }
}