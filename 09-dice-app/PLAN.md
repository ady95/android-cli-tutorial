# 앱 기획서: 주사위 굴리기

## 한 줄 소개
버튼을 누르면 주사위 두 개를 굴려 결과를 보여주는 앱

## 화면 (단일 화면)
- 주사위 두 개의 눈을 큰 글씨로 표시 (예: "3 + 5 = 8")
- 처음 실행하면 "주사위를 굴려 보세요" 안내 문구 표시
- [굴리기] 버튼: 누를 때마다 1~6 사이 무작위 눈 두 개를 새로 표시
- 두 눈이 같으면(더블) "더블!" 문구를 함께 표시

## 기술 요구사항
- Kotlin + Jetpack Compose (Material3)
- AGP 9.3.1, compileSdk 36, minSdk 24, applicationId com.example.diceapp
- 중요: AGP 9은 Kotlin 지원 내장 - org.jetbrains.kotlin.android 플러그인 추가 금지
- Compose 컴파일러 플러그인: org.jetbrains.kotlin.plugin.compose 버전 2.3.21
- Compose BOM 2026.06.01, activity-compose 1.13.0
- 매니페스트: NoActionBar 테마(@android:style/Theme.Material.Light.NoActionBar) 사용
- 상단 고정 UI가 있으면 Modifier.safeDrawingPadding() 적용

## 완료 조건
- .\gradlew.bat assembleDebug 빌드 성공 (실패하면 스스로 고쳐서 성공할 때까지)
- 빌드 성공 후: 생성한 파일 목록과 APK 경로 보고
