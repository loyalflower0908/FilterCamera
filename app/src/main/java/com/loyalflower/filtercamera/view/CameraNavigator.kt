package com.loyalflower.filtercamera.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalflower.filtercamera.presenter.CameraPresenter
import com.loyalflower.filtercamera.view.component.RequestCameraPermission
import com.loyalflower.filtercamera.view.screen.CameraPreviewScreen
import com.loyalflower.filtercamera.view.screen.CapturedImageScreen

/**
 * 카메라 권한을 요청하고, 캡처된 이미지 상태에 따라 적절한 화면을 표시하는 컴포저블 함수입니다.
 * 또한, 생명주기에 맞춰 카메라 리소스를 해제합니다.
 *
 * @param presenter 화면에 연결될 [CameraPresenter]
 */
@Composable
fun CameraNavigator(presenter: CameraPresenter = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    RequestCameraPermission(
        onPermissionGranted = {
            presenter.setupCamera(lifecycleOwner) // 권한 획득 시 카메라 설정
        },
        onPermissionDenied = {
            // 권한 거부 시 Toast 메시지 표시
            Toast.makeText(context, "카메라 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    )

    val capturedImage = presenter.capturedImage.value

    if (capturedImage != null) {
        CapturedImageScreen(capturedImage, lifecycleOwner)  // 캡처된 이미지가 있으면 표시
    } else {
        CameraPreviewScreen() // 캡처된 이미지가 없으면 카메라 미리보기 화면 표시
    }

    DisposableEffect(lifecycleOwner) { // 생명주기 이벤트 처리
        onDispose {
            presenter.releaseCamera() // 컴포저블이 제거될 때 카메라 리소스 해제
        }
    }
}