# Assistant SDK

An Android in-app Assistant SDK that can capture the current screen state and interact with UI elements within the same app.

## Features

- **Screen Capture**: Traverse and capture the current UI hierarchy as a structured snapshot
- **UI Interaction**: Perform actions like click, long-click, set text, focus, scroll, and back
- **Privacy-First**: Automatic masking of sensitive fields (passwords, payment info) with configurable allowlists/denylists
- **View System Support**: Full support for Android View system
- **Compose Support**: Compose support available via Semantics (see Compose Integration section)

## Quick Start

### 1. Add Dependency

Add the SDK module to your project's `settings.gradle`:

```gradle
include ':assistant-sdk'
```

And in your app's `build.gradle`:

```gradle
dependencies {
    implementation project(':assistant-sdk')
}
```

### 2. Initialize SDK

Initialize the SDK in your `Application` class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val config = AssistantSdkConfig.default()
        AssistantSdk.init(this, config)
    }
}
```

Don't forget to register your Application class in `AndroidManifest.xml`:

```xml
<application
    android:name=".MyApplication"
    ... >
```

### 3. Bind Activity (Optional)

If you need to bind a specific activity:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AssistantSdk.bind(this)
    }
}
```

### 4. Capture UI State

```kotlin
val snapshot = AssistantSdk.capture()
println("Found ${snapshot.nodes.size} root nodes")
```

### 5. Perform Actions

```kotlin
// Click a button
val result = AssistantSdk.perform(Action.Click("buttonId"))
if (result.success) {
    println("Click successful!")
}

// Set text in an EditText
val result = AssistantSdk.perform(Action.SetText("editTextId", "Hello World"))

// Scroll a RecyclerView
val result = AssistantSdk.perform(Action.Scroll("recyclerViewId", ScrollDirection.DOWN, 100))

// Go back
val result = AssistantSdk.perform(Action.Back)
```

## API Reference

### AssistantSdk

The main entry point for the SDK.

#### `init(app: Application, config: AssistantSdkConfig = default())`

Initialize the SDK with your application instance and optional configuration.

#### `bind(activity: Activity)`

Bind a specific activity for capture and actions. This is optional if you initialize with `init()` (which uses activity lifecycle callbacks).

#### `capture(): UiSnapshot`

Capture the current UI state and return a snapshot containing all visible UI nodes.

#### `perform(action: Action): ActionResult`

Perform an action on a UI node. Returns an `ActionResult` indicating success or failure.

### Data Models

#### UiSnapshot

Contains the captured UI state:

```kotlin
data class UiSnapshot(
    val screenInfo: ScreenInfo,  // Screen dimensions and density
    val nodes: List<UiNode>,      // Root UI nodes
    val timestamp: Long           // Capture timestamp
)
```

#### UiNode

Represents a single UI element:

```kotlin
data class UiNode(
    val id: String,                      // Stable identifier
    val type: String,                    // View class name
    val text: String?,                   // Text content (masked if sensitive)
    val contentDesc: String?,            // Content description
    val hint: String?,                   // Hint text
    val bounds: Rect,                    // Screen bounds
    val state: NodeState,                // Node state (enabled, clickable, etc.)
    val actions: List<SupportedAction>,  // Supported actions
    val children: List<UiNode>           // Child nodes
)
```

#### Action

Actions that can be performed:

- `Action.Click(nodeId: String)` - Click a node
- `Action.LongClick(nodeId: String)` - Long-click a node
- `Action.SetText(nodeId: String, text: String)` - Set text in an EditText
- `Action.Focus(nodeId: String)` - Focus a node
- `Action.Scroll(nodeId: String, direction: ScrollDirection, amount: Int)` - Scroll a scrollable view
- `Action.Back` - System back button

#### ActionResult

Result of performing an action:

```kotlin
data class ActionResult(
    val success: Boolean,
    val code: ActionCode,    // SUCCESS, NODE_NOT_FOUND, etc.
    val message: String?     // Optional error message
)
```

## Configuration

### AssistantSdkConfig

Configure the SDK behavior:

```kotlin
val config = AssistantSdkConfig(
    enableScreenshots = false,              // Disable screenshots (default: false)
    allowedActivities = setOf(),            // Allowlist activities (empty = all)
    deniedActivities = setOf(),             // Denylist activities
    sensitiveViewIds = setOf("password"),   // View IDs to mask
    sensitiveViewTags = setOf("sensitive"), // View tags to mask
    autoDetectPasswordFields = true         // Auto-detect password fields (default: true)
)

AssistantSdk.init(app, config)
```

### Privacy and Masking

The SDK automatically masks sensitive information:

1. **Password Fields**: EditText views with password input types are automatically masked
2. **Explicit Marking**: Views can be marked as sensitive via:
   - Resource ID: Add to `sensitiveViewIds` in config
   - View Tag: Add to `sensitiveViewTags` in config

Masked text appears as `"***"` in the snapshot.

Example:

```kotlin
val config = AssistantSdkConfig(
    sensitiveViewIds = setOf("creditCardField", "ssnField"),
    sensitiveViewTags = setOf("personal_info")
)

// In your layout XML:
<EditText
    android:id="@+id/creditCardField"
    android:tag="personal_info"
    ... />
```

## Compose Integration

Compose support is available but requires additional setup due to the semantic tree structure.

### Approach 1: Test Tags (Recommended)

Add test tags to your Compose UI for stable IDs:

```kotlin
@Composable
fun MyButton() {
    Button(
        onClick = { },
        modifier = Modifier.testTag("myButton")
    ) {
        Text("Click Me")
    }
}
```

The SDK will capture these tags as node IDs, but direct action execution may require custom handlers (see Approach 2).

### Approach 2: Action Handlers

For reliable Compose interaction, register action handlers:

```kotlin
// This is a recommended pattern for Compose (implementation pending)
// You would register handlers like:
AssistantSdk.registerActionHandler("myButton") {
    // Handle click action
    onClick()
}
```

### Current Limitations

- Direct Compose node interaction may not be fully reliable in production
- Full Compose support is a work in progress
- For production use, prefer the View system or use action handlers for Compose

## Example: Capture Output

```json
{
  "screenInfo": {
    "width": 1080,
    "height": 1920,
    "density": 3.0
  },
  "nodes": [
    {
      "id": "titleText",
      "type": "TextView",
      "text": "Welcome",
      "bounds": { "left": 0, "top": 100, "right": 1080, "bottom": 200 },
      "state": {
        "enabled": true,
        "clickable": false,
        "sensitive": false
      },
      "actions": ["FOCUS"],
      "children": []
    },
    {
      "id": "passwordField",
      "type": "EditText",
      "text": "***",
      "hint": "Password",
      "bounds": { "left": 100, "top": 300, "right": 980, "bottom": 400 },
      "state": {
        "enabled": true,
        "clickable": true,
        "sensitive": true
      },
      "actions": ["CLICK", "SET_TEXT", "FOCUS"],
      "children": []
    }
  ],
  "timestamp": 1234567890
}
```

## Running the Sample App

1. Open the project in Android Studio
2. Ensure you have Android SDK configured (minSdk 24, compileSdk 34)
3. Run the `sample-app` module
4. Tap "Capture UI" to see the current UI snapshot
5. The snapshot JSON will appear in the scrollable text view below

## Testing

### Unit Tests

Run unit tests:

```bash
./gradlew :assistant-sdk:test
```

Tests cover:
- Node ID generation
- Masking utilities
- Privacy defaults

### Instrumentation Tests

Run instrumentation tests:

```bash
./gradlew :sample-app:connectedAndroidTest
```

Tests verify:
- Capture returns valid snapshot
- Nodes have stable IDs
- Click actions work correctly

## Architecture

- **AssistantSdk**: Main entry point, manages initialization and activity binding
- **ViewCaptureEngine**: Traverses view hierarchy and builds UI snapshot
- **ActionExecutor**: Executes actions on views
- **MaskingUtils**: Handles privacy masking and filtering
- **NodeIdGenerator**: Generates stable IDs for views

## Limitations

- **In-App Only**: Only works within the host app, no cross-app functionality
- **No AccessibilityService**: Does not use AccessibilityService
- **View System First**: Full support for Views, Compose support is best-effort
- **Main Thread**: UI capture and actions must run on the main thread

## License

[Add your license here]

## Contributing

[Add contribution guidelines here]

