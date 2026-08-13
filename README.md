# 안드로이드 CLI 따라하기 — 예제 코드

위키독스 책 「안드로이드 CLI 따라하기 (Claude Code로 안드로이드앱 만들기)」의 예제 코드 저장소입니다.

- 책: https://wikidocs.net/book/20876

## 예제 목록

| 폴더 | 내용 | 관련 장 |
|---|---|---|
| `01-hello-android` | 안드로이드 스튜디오 없이 만든 최소 구성 Hello Android 앱 | 03장 |
| `02-counter-app` | Claude Code가 생성·수정한 카운터 앱 (에이전트 실습 결과물) | 03-5, 05장 |
| `03-compose-counter` | Jetpack Compose로 만든 카운터 앱 | 06장 |
| `04-counter-datastore` | DataStore로 값이 유지되는 카운터 앱 | 07-1 |
| `05-notes-room` | Room 데이터베이스 메모 앱 | 07-2 |
| `06-api-list` | Retrofit으로 API 목록을 표시하는 앱 | 07-3, 07-4 |
| `07-todo-app` | 할 일 관리 앱 (Room CRUD + Compose 종합) | 10-1 |
| `08-weather-app` | 날씨 앱 (Open-Meteo API) | 10-2 |
| `09-dice-app` | 기획서(PLAN.md)로 Claude Code가 전체 생성한 주사위 앱 | 10-3 |

## 빌드 방법

각 예제 폴더에서:

```bash
./gradlew assembleDebug          # 디버그 APK 빌드
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 기준 버전

- JDK 17 / Gradle 9.7.0 / AGP 9.3.1 / compileSdk 36 (Android 16)

자세한 환경 구축 방법은 책 02장을 참고하세요.
