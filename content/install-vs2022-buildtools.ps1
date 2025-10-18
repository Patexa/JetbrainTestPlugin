# Install Visual Studio 2022 Build Tools
# This script downloads and installs VS 2022 Build Tools with MSBuild 17.12+

Write-Host "Visual Studio 2022 Build Tools Installer" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

$installerUrl = "https://aka.ms/vs/17/release/vs_buildtools.exe"
$installerPath = "$env:TEMP\vs_buildtools.exe"

Write-Host "Downloading Visual Studio 2022 Build Tools..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $installerUrl -OutFile $installerPath
    Write-Host "Download complete!" -ForegroundColor Green
} catch {
    Write-Host "Error downloading installer: $_" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Starting installation..." -ForegroundColor Yellow
Write-Host "This will install:" -ForegroundColor Cyan
Write-Host "  - MSBuild 17.12+" -ForegroundColor White
Write-Host "  - .NET Framework 4.7.2 targeting pack" -ForegroundColor White
Write-Host "  - .NET desktop build tools" -ForegroundColor White
Write-Host ""

# Start the installer with required workloads
# --add Microsoft.VisualStudio.Workload.ManagedDesktopBuildTools: .NET desktop build tools
# --add Microsoft.Net.Component.4.7.2.TargetingPack: .NET Framework 4.7.2
# --includeRecommended: Include recommended components

$arguments = @(
    "--quiet",
    "--wait",
    "--norestart",
    "--add", "Microsoft.VisualStudio.Workload.ManagedDesktopBuildTools",
    "--add", "Microsoft.Net.Component.4.7.2.TargetingPack",
    "--includeRecommended"
)

Write-Host "Running installer (this may take 10-15 minutes)..." -ForegroundColor Yellow
Write-Host "Please be patient..." -ForegroundColor Yellow
Write-Host ""

try {
    $process = Start-Process -FilePath $installerPath -ArgumentList $arguments -Wait -PassThru
    
    if ($process.ExitCode -eq 0 -or $process.ExitCode -eq 3010) {
        Write-Host ""
        Write-Host "Installation completed successfully!" -ForegroundColor Green
        Write-Host ""
        Write-Host "MSBuild is now available at:" -ForegroundColor Cyan
        Write-Host "C:\Program Files\Microsoft Visual Studio\2022\BuildTools\MSBuild\Current\Bin\MSBuild.exe" -ForegroundColor White
        Write-Host ""
        Write-Host "You can now run: gradlew.bat runIde" -ForegroundColor Yellow
        
        if ($process.ExitCode -eq 3010) {
            Write-Host ""
            Write-Host "NOTE: A restart may be required for all changes to take effect." -ForegroundColor Yellow
        }
    } else {
        Write-Host ""
        Write-Host "Installation failed with exit code: $($process.ExitCode)" -ForegroundColor Red
        Write-Host "Please try running the installer manually." -ForegroundColor Yellow
    }
} catch {
    Write-Host "Error running installer: $_" -ForegroundColor Red
    exit 1
} finally {
    # Clean up
    if (Test-Path $installerPath) {
        Remove-Item $installerPath -Force
    }
}

Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Cyan
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

