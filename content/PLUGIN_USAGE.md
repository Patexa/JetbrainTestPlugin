# Search Popup Plugin - Usage Guide

## Overview

This Rider plugin provides a searchable popup window that can be opened with a keyboard shortcut. The popup contains a search bar and displays filterable results.

## Features

- **Keyboard Shortcut**: Press `Ctrl+Shift+S` (Windows/Linux) or `Cmd+Shift+S` (macOS) to open the popup
- **Search Bar**: Type to filter the results in real-time
- **Interactive List**: Double-click or press Enter to select an item
- **Notifications**: Selected items trigger a notification

## How to Use

1. **Open the Popup**: Press `Ctrl+Shift+S` (or `Cmd+Shift+S` on macOS)
2. **Search**: Start typing in the search field to filter results
3. **Select**: 
   - Double-click on an item, or
   - Use arrow keys to navigate and press Enter
4. **Close**: Press Escape or click outside the popup

## Customization

### Changing the Keyboard Shortcut

Edit `content/src/rider/main/resources/META-INF/plugin.xml`:

```xml
<keyboard-shortcut first-keystroke="control shift S" keymap="$default"/>
```

Common shortcuts:
- `control shift S` - Ctrl+Shift+S
- `alt S` - Alt+S
- `control alt S` - Ctrl+Alt+S

### Customizing the Search Data

Edit the `SearchPopupAction.kt` file and modify the `allItems` list:

```kotlin
val allItems = listOf(
    "Your Item 1",
    "Your Item 2",
    // Add more items...
)
```

### Handling Selection Events

Modify the `handleSelection()` method in `SearchPopupAction.kt` to implement your custom logic:

```kotlin
private fun handleSelection(selectedValue: String, popup: JBPopup) {
    // Your custom logic here
    when (selectedValue) {
        "Action 1: Open File" -> {
            // Open file dialog
        }
        "Action 2: Navigate to Class" -> {
            // Navigate to class
        }
        // Add more cases...
    }
    popup.closeOk(null)
}
```

## Building and Running

### Run in Development Mode

```bash
cd content
./gradlew runIde
```

On Windows:
```cmd
cd content
gradlew.bat runIde
```

### Build Plugin Distribution

```bash
cd content
./gradlew buildPlugin
```

The plugin will be created in `content/build/distributions/`

### Install in Rider

1. Build the plugin (see above)
2. Open Rider
3. Go to `File → Settings → Plugins`
4. Click the gear icon → `Install Plugin from Disk...`
5. Select the `.zip` file from `content/build/distributions/`
6. Restart Rider

## Project Structure

```
content/
├── src/
│   ├── rider/
│   │   └── main/
│   │       ├── kotlin/
│   │       │   └── com/jetbrains/rider/plugins/sampleplugin/
│   │       │       └── SearchPopupAction.kt    # Main action class
│   │       └── resources/
│   │           └── META-INF/
│   │               └── plugin.xml              # Plugin configuration
│   └── dotnet/                                 # .NET ReSharper components
└── build.gradle.kts                            # Build configuration
```

## Advanced Integration Ideas

### 1. File Search
Integrate with Rider's file system to search and open files:
```kotlin
// Search for files in project
val files = FilenameIndex.getAllFilesByExt(project, "cs")
```

### 2. Symbol Search
Search for classes, methods, and symbols:
```kotlin
// Search for classes
val classes = AllClassesSearch.search(scope, project)
```

### 3. Recent Files
Show recently opened files for quick access:
```kotlin
// Get recent files
val recentFiles = EditorHistoryManager.getInstance(project).fileList
```

### 4. Custom Commands
Create custom commands or shortcuts for common tasks in your workflow.

## Troubleshooting

### Plugin Doesn't Load
- Check the `build/` directory for build errors
- Verify the plugin.xml is valid
- Ensure the Kotlin code compiles without errors

### Shortcut Doesn't Work
- Check for conflicting shortcuts in `Settings → Keymap`
- Try a different key combination
- Verify the shortcut is correctly defined in plugin.xml

### Popup Doesn't Appear
- Check the IDE log: `Help → Show Log in Explorer`
- Ensure the action is properly registered in plugin.xml
- Verify the project reference is not null

## Resources

- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Rider Plugin Development](https://plugins.jetbrains.com/docs/intellij/rider.html)
- [UI Components Guide](https://plugins.jetbrains.com/docs/intellij/user-interface-components.html)

