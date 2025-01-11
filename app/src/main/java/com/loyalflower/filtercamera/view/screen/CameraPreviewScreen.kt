package com.loyalflower.filtercamera.view.screen

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalflower.filtercamera.presenter.CameraPresenter
import com.loyalflower.filtercamera.view.component.CameraShutterButton

/**
 * 카메라 미리보기와 촬영 버튼을 표시하는 화면을 구성하는 컴포저블 함수입니다.
 *
 * @param presenter 화면에 연결될 [CameraPresenter]
 */
@Composable
fun CameraPreviewScreen(presenter: CameraPresenter = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()){
        AndroidView(
            factory = { context ->
                PreviewView(context).also { previewView ->
                    previewView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    previewView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE // 성능 우선 모드로 설정합니다.
                    previewView.scaleType = PreviewView.ScaleType.FILL_CENTER // 이미지를 중앙에 꽉 차도록 스케일링합니다.
                    presenter.setupPreview(previewView) // Presenter에 PreviewView를 설정하여 카메라와 연결합니다.
                }
            },
            modifier = Modifier.fillMaxSize()
        ) // 카메라 미리보기를 표시하는 AndroidView
        CameraShutterButton(
            onClick = { presenter.takePhoto() }, // 버튼 클릭 시 사진 촬영
            modifier = Modifier
                .align(Alignment.BottomCenter) // 화면 하단 중앙에 배치
                .padding(bottom = 64.dp) // 하단 여백
        ) // 카메라 촬영 버튼
    }
}