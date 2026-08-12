# 안드로이드 CLI 따라하기 — 예제 코드

위키독스 책 「안드로이드 CLI 따라하기 (Claude Code로 안드로이드앱 만들기)」의 예제 코드 저장소입니다.

- 책: https://wikidocs.net/book/20876

## 예제 목록

| 폴더 | 내용 | 관련 장 |
|---|---|---|
| `01-hello-android` | 안드로이드 스튜디오 없이 만든 최소 구성 Hello Android 앱 | 03장 |
| `02-counter-app` | Claude Code가 생성·수정한 카운터 앱 (에이전트 실습 결과물) | 03-5, 05장 |
| `03-compose-counter` | Jetpack Compose로 만든 카운터 앱 | 06장 |

## 빌드 방법

각 예제 폴더에서:

```bash
./gradlew assembleDebug          # 디버그 APK 빌드
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 기준 버전

- JDK 17 / Gradle 9.7.0 / AGP 9.3.1 / compileSdk 36 (Android 16)

자세한 환경 구축 방법은 책 02장을 참고하세요.
