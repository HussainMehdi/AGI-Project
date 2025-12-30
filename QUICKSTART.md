# Quick Start Guide

## Prerequisites

- Android Studio (latest stable version)
- Android SDK with API 24+ (Android 7.0+)
- Gradle 8.2+

## Building and Running

1. **Open the project** in Android Studio

2. **Sync Gradle** - Android Studio should automatically sync, or click "Sync Now"

3. **Run the sample app**:
   - Select `sample-app` configuration
   - Choose an emulator or connected device (API 24+)
   - Click Run

4. **Test the SDK**:
   - In the sample app, tap "Capture UI"
   - View the JSON snapshot in the scrollable text view
   - The app automatically tests a click action when capturing

## Project Structure

```
AGI-Project/
├── assistant-sdk/          # SDK library module
│   └── src/main/java/com/agi/assistantsdk/
│       ├── AssistantSdk.kt          # Main API
│       ├── AssistantSdkConfig.kt    # Configuration
│       ├── ViewCaptureEngine.kt     # UI capture logic
│       ├── ActionExecutor.kt        # Action execution
│       ├── MaskingUtils.kt          # Privacy masking
│       └── models/                  # Data models
├── sample-app/             # Sample application
│   └── src/main/java/com/agi/sampleapp/
│       ├── SampleApplication.kt     # App initialization
│       └── MainActivity.kt          # Sample UI
└── README.md               # Full documentation
```

## Running Tests

### Unit Tests

```bash
./gradlew :assistant-sdk:test
```

### Instrumentation Tests

```bash
./gradlew :sample-app:connectedAndroidTest
```

Note: Instrumentation tests require a connected device or emulator.

## Integration in Your App

1. Add the module to your `settings.gradle`:
   ```gradle
   include ':assistant-sdk'
   ```

2. Add dependency in your app's `build.gradle`:
   ```gradle
   dependencies {
       implementation project(':assistant-sdk')
   }
   ```

3. Initialize in your Application class:
   ```kotlin
   class MyApp : Application() {
       override fun onCreate() {
           super.onCreate()
           AssistantSdk.init(this)
       }
   }
   ```

4. Use the SDK:
   ```kotlin
   val snapshot = AssistantSdk.capture()
   val result = AssistantSdk.perform(Action.Click("buttonId"))
   ```

See README.md for detailed API documentation.

