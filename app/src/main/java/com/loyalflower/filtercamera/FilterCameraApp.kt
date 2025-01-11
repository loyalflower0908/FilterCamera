package com.loyalflower.filtercamera

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Dagger-Hilt를 사용하는 Filter Camera 애플리케이션의 Application 클래스입니다.
 *
 * @HiltAndroidApp 어노테이션을 통해 Hilt가 코드 생성을 시작하도록 합니다.
 */
@HiltAndroidApp
class FilterCameraApp:Application()