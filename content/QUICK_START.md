# Quick Start Guide - Search Popup Plugin

## What I Created

✅ **SearchPopupAction.kt** - Main plugin action with searchable popup window  
✅ **Updated plugin.xml** - Registered action with keyboard shortcut `Ctrl+Shift+S`  
✅ **Notification system** - Shows feedback when items are selected  

## Test the Plugin Now

### Option 1: Run in Development Mode (Recommended)

1. Open terminal in the `content` directory:
   ```bash
   cd content
   ```

2. Run the plugin in a test Rider instance:
   ```bash
   # Windows
   gradlew.bat runIde
   
   # Linux/Mac
   ./gradlew runIde
   ```

3. Wait for Rider to launch (this may take a few minutes)

4. In the test Rider instance, press **`Ctrl+Shift+S`** (or **`Cmd+Shift+S`** on macOS)

5. The popup should appear with a search bar!

### Option 2: Build and Install

1. Build the plugin:
   ```bash
   cd content
   gradlew.bat buildPlugin   # Windows
   ./gradlew buildPlugin     # Linux/Mac
   ```

2. Find the plugin at: `content/build/distributions/ReSharperPlugin.SamplePlugin-9999.0.0.zip`

3. Install in Rider:
   - Open Rider
   - Go to `File → Settings → Plugins`
   - Click gear icon → `Install Plugin from Disk...`
   - Select the `.zip` file
   - Restart Rider

## Using the Plugin

1. **Open**: Press `Ctrl+Shift+S` (Windows/Linux) or `Cmd+Shift+S` (macOS)
2. **Search**: Type to filter the list
3. **Select**: Double-click or press Enter on an item
4. **Close**: Press Escape

## Key Features

### Popup Window
- **500x400 pixels** - Resizable and movable
- **Centered** - Opens in center of current window
- **Focus on search** - Search field is auto-focused

### Search Functionality
- **Real-time filtering** - Results update as you type
- **Case-insensitive** - Search works regardless of case
- **Shows all items** - When search is empty, displays all items

### Selection Handling
- **Double-click** - Select an item
- **Keyboard** - Use arrow keys + Enter
- **Notification** - Shows what was selected

## Customization

### Change Keyboard Shortcut

Edit `content/src/rider/main/resources/META-INF/plugin.xml`:

```xml
<!-- Change "control shift S" to your preferred shortcut -->
<keyboard-shortcut first-keystroke="control shift S" keymap="$default"/>
```

Examples:
- `alt P` → Alt+P
- `control alt F` → Ctrl+Alt+F
- `meta shift P` → Cmd+Shift+P (macOS)

### Add Your Own Items

Edit `SearchPopupAction.kt`, find the `allItems` list (around line 38):

```kotlin
val allItems = listOf(
    "Your Custom Action 1",
    "Your Custom Action 2",
    "Another Action",
    // Add more items...
)
```

### Handle Selection Events

Modify the `handleSelection()` method (around line 104) to implement your logic:

```kotlin
private fun handleSelection(selectedValue: String, popup: JBPopup) {
    when (selectedValue) {
        "Action 1: Open File" -> {
            // Your code here
        }
        "Action 2: Navigate to Class" -> {
            // Your code here
        }
    }
    popup.closeOk(null)
}
```

## Project Structure

```
content/
├── src/rider/main/
│   ├── kotlin/com/jetbrains/rider/plugins/sampleplugin/
│   │   └── SearchPopupAction.kt          ← Main plugin code
│   └── resources/META-INF/
│       └── plugin.xml                    ← Plugin configuration
├── build.gradle.kts                      ← Build settings
└── gradle.properties                     ← Version configuration
```

## Troubleshooting

### Build Fails
```bash
# Clean and rebuild
cd content
gradlew.bat clean build   # Windows
./gradlew clean build     # Linux/Mac
```

### Shortcut Conflicts
If `Ctrl+Shift+S` is already in use:
1. Go to `Settings → Keymap` in Rider
2. Search for "Open Search Popup"
3. Right-click → Add Keyboard Shortcut
4. Choose a different key combination

### Plugin Not Showing
1. Verify plugin is enabled: `Settings → Plugins`
2. Check logs: `Help → Show Log in Explorer`
3. Look for errors related to "sampleplugin"

## Next Steps

### Enhance the Search
- Connect to Rider's file index
- Search for classes, methods, files
- Add icons to list items
- Group results by category

### Add More Actions
- Navigate to files
- Run custom commands
- Open external tools
- Generate code snippets

### Improve UI
- Add preview pane
- Show keyboard hints
- Add action buttons
- Theme support

## Need Help?

Check out:
- `PLUGIN_USAGE.md` - Detailed usage guide
- [IntelliJ Platform SDK Docs](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Rider Plugin Development](https://plugins.jetbrains.com/docs/intellij/rider.html)

