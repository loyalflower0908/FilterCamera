# 📸 Filter Camera - Jetpack Compose, CameraX, GPUImage를 이용한  카메라 앱

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> Jetpack Compose, CameraX, GPUImage 그리고 MVP 패턴을 활용하여 만든 안드로이드 카메라 애플리케이션입니다.

## 📱 소개

Filter Camera는 Jetpack Compose를 사용하여 UI를 구성하고, CameraX 라이브러리를 통해 카메라 기능을 구현하며, GPUImage를 활용하여 다양한 필터를 실시간으로 적용할 수 있는 안드로이드 카메라 애플리케이션입니다. MVP 패턴을 적용하여 유지보수와 테스트가 용이하도록 설계했습니다.

## ✨ 주요 기능

- **카메라 미리보기**: CameraX를 사용하여 카메라 미리보기 화면을 제공합니다.
- **사진 촬영**: 미리보기 화면에서 셔터 버튼을 눌러 사진을 촬영할 수 있습니다.
- **실시간 필터**: GPUImage를 사용하여 다양한 필터를 실시간으로 적용하고 확인할 수 있습니다. (흑백, 세피아, 스케치, 툰 등 12가지)
- **이미지 저장**: 촬영된 사진은 필터가 적용된 상태로 기기에 저장됩니다.
- **권한 처리**: 카메라 권한을 요청하고 처리합니다.

## 🛠️ 기술 스택

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: 최신 안드로이드 UI 툴킷
- **[CameraX](https://developer.android.com/training/camerax)**: 카메라 기능 구현을 위한 라이브러리
- **[GPUImage](https://github.com/cats-oss/android-gpuimage)**: 이미지 및 비디오 필터 처리를 위한 라이브러리
- **[Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)**: 의존성 주입을 위한 라이브러리
- **[Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)**: 비동기 처리를 위한 코루틴
- **MVP 패턴**: Model-View-Presenter 아키텍처 패턴

## 🏗️ 아키텍처

본 애플리케이션은 MVP (Model-View-Presenter) 패턴을 기반으로 설계되었습니다.

- **Model**: 카메라 (CameraX), 이미지 저장, 필터 (GPUImage) 관련 비즈니스 로직을 담당합니다.
- **View**: Jetpack Compose로 구성된 UI 레이어입니다. 사용자 인터페이스를 표시하고 사용자 입력을 처리합니다.
- **Presenter**: View와 Model 사이의 중재자 역할을 합니다. View의 요청을 받아 Model을 조작하고, Model의 변경 사항을 View에 반영합니다.


## 📄 느낀점

View는 Presenter에 이벤트를 전달하고, Presenter는 Model을 사용하여 데이터를 처리

View는 Model에 직접 접근하지 않음

Presenter는 View에 직접 접근하지 않고, 인터페이스를 통해 상호 작용

이 3가지를 신경쓰면서 작업했다.

MVC, MVP, MVVM 패턴을 이로써 전부 경험해보았는데

확실히 MVVM 패턴이 구조가 명확해서 역할 분리에 더 편한 것 같다.

하지만 구조를 명확하게 나누고 분리할수록 신경써야하는것이 생기고

보일러 플레이트 코드가 생긴다는 점을 요즘 몇 가지 프로젝트를 실패해보면서 느끼는 것 같다..

프로젝트 사이즈에 맞는 구조를 정하는 것도 중요해보인다.

## 🖼️ 스크린샷  

| <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Main%20Screenshot.png" width="200">   | <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Invert%20Filter%20Save%20Screenshot.png" width="200"> | <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Result%20Screenshot.jpg" width="200"> |  
| :----------------------------------------------:  | :----------------------------------------------: | :----------------------------------------------: |  
|                 기본 카메라 화면                  |             Invert 필터 적용 및 저장              |                   결과물 예시                     |  

| <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Sepia%20Filter%20Screenshot.png" width="200"> | <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Sketch%20Filter%20Screenshot.png" width="200"> | <img src="https://github.com/loyalflower0908/FilterCamera/blob/master/Filter%20Camera%20Screenshot/Swirl%20Filter%20Screenshot.png" width="200"> |
 | :----------------------------------------------: | :----------------------------------------------: | :----------------------------------------------: |
|                 Sepia 필터 적용                   |                 Sketch 필터 적용                  |                  Swirl 필터 적용                  | 
_____________________________________________________
### 🕐 개발 기간 🕐
2024.12. 30 ~ 2025. 01. 12
