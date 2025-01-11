package com.loyalflower.filtercamera.model.filter

/**
 * 이미지에 적용할 수 있는 필터 종류를 정의한 enum 클래스입니다.
 */
enum class FilterType {
    /** 흑백 필터 */
    GRAYSCALE,
    /** 세피아 필터 */
    SEPIA,
    /** 색상 반전 필터 */
    INVERT,
    /** 스케치 필터 */
    SKETCH,
    /** 만화 효과 필터 */
    TOON,
    /** 필터 없음 */
    NONE,
    /** 픽셀화 필터 */
    PIXEL,
    /** 교차 해칭 필터 */
    CROSSHATCH,
    /** 볼록 효과 필터 */
    BULGE,
    /** 소용돌이 필터 */
    SWIRL,
    /** 구면 효과 필터 */
    Sphere,
    /** 엠보싱 필터 */
    EMBOSS,
    /** 포스터라이즈 필터 */
    POSTER
}