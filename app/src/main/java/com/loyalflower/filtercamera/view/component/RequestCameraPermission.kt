package com.loyalflower.filtercamera.view.component

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * 사용자에게 카메라 권한을 요청하고, 권한 획득 여부에 따라 지정된 작업을 수행하는 컴포저블 함수입니다.
 *
 * @param onPermissionGranted 권한이 허용되었을 때 실행할 콜백 함수
 * @param onPermissionDenied 권한이 거부되었을 때 실행할 콜백 함수
 */
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    var hasCameraPermission by remember {
        mutableStateOf(false) // 카메라 권한 상태 (초기값: false)
    }

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), // 단일 권한 요청 contract
        onResult = { granted ->
            hasCameraPermission = granted // 권한 획득 여부 업데이트
            if (granted) {
                onPermissionGranted() // 권한 허용 시 콜백 실행
            } else {
                onPermissionDenied() // 권한 거부 시 콜백 실행
            }
        }
    )

    LaunchedEffect(Unit) { // 한 번만 실행되는 LaunchedEffect
        // 권한 상태를 먼저 확인
        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            hasCameraPermission = true
            onPermissionGranted() // 이미 권한이 있으면 콜백 실행
        } else {
            // 권한이 없는 경우 요청
            permissionLauncher.launch(Manifest.permission.CAMERA) // 권한 요청
        }
    }
}