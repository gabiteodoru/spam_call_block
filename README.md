# spam_call_block

A minimal Android app (shown on the phone as **PhoneBlock**) that rejects incoming calls from numbers starting with `+346`. The interface is a single on/off switch.

It uses Android's `CallScreeningService`, so it has no background service or polling of its own: Android starts it when there is a call to screen. It has no third-party libraries and requests no runtime permissions or internet access.

**Warning:** `+346` covers Spanish mobile numbers beginning with 6. This includes many legitimate callers, not just spam.

## What's hardcoded

There are no settings besides the switch. To change any of these, edit the source and rebuild.

- **Blocked numbers** (`NumberFilter.java`):
  - numbers starting with `+346`
  - numbers starting with `00346`
  - nine-digit numbers starting with `6`, only while the phone is on a Spanish network (`es`)
- **What happens to a blocked call** (`BlockService.java`): it is rejected and the app asks Android to skip the notification. The app does not ask to hide it from the call log.
- **Direction** (`BlockService.java`): only incoming calls are filtered.
- **Switch label** (`MainActivity.java`): "Block +346", in English only. The filter is off until you turn it on.
- **App name** (`AndroidManifest.xml`): PhoneBlock.
- **Package name** (`app/build.gradle`): `com.gabi.phoneblock`.
- **Android versions** (`app/build.gradle`): minimum Android 10 (API 29), compile and target API 34.

The source files are in `app/src/main/`.

## Building

You need JDK 17, Android SDK platform 34, build tools 34.0.0 and Gradle 8.2 (the version used here; there is no Gradle wrapper in the repo). The Android Gradle plugin is version 8.2.2.

Set `ANDROID_HOME` to your SDK directory, or put `sdk.dir=/path/to/sdk` in `local.properties`. Then run:

```
gradle assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Using it

1. Open PhoneBlock and turn on the switch.
2. When Android asks, set it as your caller ID and spam app. Only one app can have this role at a time.

Calls from your contacts are not screened, because the app has no contacts permission. SMS and messaging apps are not affected.

## Status

The app builds and installs on Android 14. Number matching was checked with a temporary test that is not included in the repo. Blocking has not yet been verified with a real call.

## License

[MIT](LICENSE)
