package com.loyalflower.filtercamera.view.screen

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.loyalflower.filtercamera.presenter.CameraPresenter
import com.loyalflower.filtercamera.view.component.FilterSelector

/**
 * 캡처된 이미지를 표시하고, 필터를 적용하고, 저장하는 기능을 제공하는 화면을 구성하는 컴포저블 함수입니다.
 *
 * @param capturedImage 표시할 캡처된 이미지 [Bitmap]
 * @param lifecycleOwner [LifecycleOwner]
 * @param presenter 화면에 연결될 [CameraPresenter]
 */
@Composable
fun CapturedImageScreen(
    capturedImage: Bitmap,
    lifecycleOwner: LifecycleOwner,
    presenter: CameraPresenter = hiltViewModel()
) {
    Image(
        bitmap = capturedImage.asImageBitmap(), // Bitmap을 ImageBitmap으로 변환
        contentDescription = "Filtered Image",
        modifier = Modifier.fillMaxSize() // 이미지를 전체 화면에 채우도록 설정
    )
    Column {
        Spacer(modifier = Modifier.height(64.dp)) // 상단 여백
        FilterSelector(filters = presenter.getAvailableFilters()) { filterType ->
            presenter.applyFilter(filterType) // 선택된 필터를 Presenter에 적용 요청
        }
        Button(onClick = { presenter.saveImageToGallery() }) { // 이미지 저장 버튼
            Text("Save Image")
        }
    }
    BackHandler { // 뒤로 가기 처리
        presenter.resetCapturedImage(lifecycleOwner) // Presenter에 캡처된 이미지 초기화 요청
    }
}
