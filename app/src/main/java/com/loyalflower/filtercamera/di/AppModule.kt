package com.loyalflower.filtercamera.di

import android.content.Context
import com.loyalflower.filtercamera.model.camera.CameraManager
import com.loyalflower.filtercamera.model.filter.FilterApplier
import com.loyalflower.filtercamera.model.filter.FilterManager
import com.loyalflower.filtercamera.model.storage.ImageSaver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger-Hilt 의존성 주입을 위한 AppModule.
 * Application 전체에서 Singleton으로 사용될 객체들을 제공합니다.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * [FilterManager] 객체를 제공합니다.
     * @return [FilterManager]의 Singleton 인스턴스
     */
    @Provides
    @Singleton
    fun provideFilterManager(): FilterManager {
        return FilterManager()
    }

    /**
     * [CameraManager] 객체를 제공합니다.
     * @param context Application Context
     * @return [CameraManager]의 Singleton 인스턴스
     */
    @Provides
    @Singleton
    fun provideCameraManager(@ApplicationContext context: Context): CameraManager {
        return CameraManager(context)
    }

    /**
     * [ImageSaver] 객체를 제공합니다.
     * @param context Application Context
     * @return [ImageSaver]의 Singleton 인스턴스
     */
    @Provides
    @Singleton
    fun provideImageSaver(@ApplicationContext context: Context): ImageSaver {
        return ImageSaver(context)
    }

    /**
     * [FilterApplier] 객체를 제공합니다.
     * @param context Application Context
     * @param filterManager 필터 적용에 사용될 [FilterManager] 인스턴스
     * @return [FilterApplier]의 Singleton 인스턴스
     */
    @Provides
    @Singleton
    fun provideFilterApplier(@ApplicationContext context: Context, filterManager: FilterManager): FilterApplier {
        return FilterApplier(context, filterManager)
    }
}