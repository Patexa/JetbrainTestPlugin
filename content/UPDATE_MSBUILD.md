# MSBuild Update Guide

## Problem

Your system has:
- ❌ **Visual Studio 2019** with MSBuild 16.11.2
- ✅ **.NET SDK 9.0.306** (latest)

But .NET SDK 9.0.306 requires:
- ✅ **MSBuild 17.12.0+** (from Visual Studio 2022)

## Solution: Install Visual Studio 2022 Build Tools

### Method 1: Automated Installation (Easiest)

Run the PowerShell script I created:

```powershell
cd content
powershell -ExecutionPolicy Bypass -File install-vs2022-buildtools.ps1
```

This will:
1. Download VS 2022 Build Tools (~2GB)
2. Install MSBuild 17.12+ automatically
3. Install .NET Framework 4.7.2 targeting pack
4. Take about 10-15 minutes

### Method 2: Manual Installation

1. **Download Visual Studio 2022 Build Tools:**
   - Go to: https://visualstudio.microsoft.com/downloads/
   - Scroll to "All Downloads"
   - Expand "Tools for Visual Studio"
   - Download "Build Tools for Visual Studio 2022"

2. **Run the installer:**
   - Launch `vs_buildtools.exe`
   - Select the **".NET desktop build tools"** workload
   - Make sure these are checked:
     - MSBuild
     - .NET Framework 4.7.2 targeting pack
     - NuGet targets and build tasks
   - Click Install

3. **Wait for installation** (10-15 minutes)

### Method 3: Install Full Visual Studio 2022 (Alternative)

If you prefer the full IDE:
- Download Visual Studio 2022 Community (free): https://visualstudio.microsoft.com/vs/
- During installation, select the ".NET desktop development" workload

## After Installation

Once installed, MSBuild 17.12+ will be at:
```
C:\Program Files\Microsoft Visual Studio\2022\BuildTools\MSBuild\Current\Bin\MSBuild.exe
```

The Gradle build will automatically find and use it!

## Verify Installation

Run these commands to verify:

```bash
# Check MSBuild version (should be 17.12+)
"C:\Program Files\Microsoft Visual Studio\2022\BuildTools\MSBuild\Current\Bin\MSBuild.exe" -version

# Or use vswhere to find it
content\tools\vswhere.exe -latest -property installationVersion
```

## Build Your Plugin

After MSBuild is updated, run:

```bash
cd content
gradlew.bat runIde
```

This should now work without errors!

## Alternative: Use Only dotnet CLI (Skip MSBuild Update)

If you don't want to install VS 2022, you can modify the build process to use `dotnet` CLI instead of MSBuild. However, this requires more configuration changes.

Let me know if you want to go this route instead!

## Troubleshooting

### Installation Fails

Try running as Administrator:
```powershell
# Right-click PowerShell -> Run as Administrator
cd C:\Projects\resharper-rider-plugin\content
.\install-vs2022-buildtools.ps1
```

### Build Still Fails After Installation

1. Close all terminals
2. Restart your IDE
3. Try the build again

The Gradle build caches VS location, so a restart may be needed.

### Prefer Minimal Installation

If disk space is a concern, you can install just MSBuild manually:
1. Download the Build Tools installer
2. Run with: `vs_buildtools.exe --add Microsoft.Component.MSBuild --quiet`
3. Add .NET Framework: `--add Microsoft.Net.Component.4.7.2.TargetingPack`

## Why This Is Needed

- ReSharper plugins are .NET projects that target .NET Framework 4.7.2
- The Gradle build uses MSBuild to compile the .NET portion
- .NET SDK 9.0 requires a newer MSBuild that matches its tooling
- VS 2022 Build Tools provides the compatible MSBuild version

## Size Requirements

- **Download**: ~2 GB
- **Installation**: ~4-5 GB on disk

If disk space is very limited, let me know and I can help configure an alternative approach using dotnet CLI only.

