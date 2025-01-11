package com.loyalflower.filtercamera.model.storage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * Bitmap 이미지를 기기에 저장하는 클래스입니다.
 *
 * @param context [Context] 객체
 */
class ImageSaver @Inject constructor(private val context: Context) {

    /**
     * 주어진 Bitmap 이미지를 갤러리에 저장합니다.
     *
     * @param bitmap 저장할 [Bitmap] 이미지
     * @throws Exception 이미지 저장에 실패했을 경우 예외를 발생시킵니다.
     */
    suspend fun saveImage(bitmap: Bitmap) {
        withContext(Dispatchers.IO) { // IO 스레드에서 실행
            val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) // 현재 시간을 기준으로 파일 이름 생성
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name) // 파일 이름 설정
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg") // MIME 타입 설정
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilterCamera") // 저장 경로 설정 (Android Q 이상)
                }
            }

            val savedUri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return@withContext // 이미지 저장을 위한 URI 생성, 실패 시 리턴

            context.contentResolver.openOutputStream(savedUri)?.use { outputStream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) { // JPEG 형식으로 압축하여 저장
                    throw Exception("Failed to save bitmap.")
                }
            }
        }
    }
}