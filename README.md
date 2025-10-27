# TurboLite Optimizer

A complete Android mobile optimization app with a modern neon/glass-morphism UI theme. This app simulates device optimization features with live monitoring and animated effects.

## 🎨 Features

### 💾 RAM Booster
- Live memory monitoring with circular progress indicator
- Real-time memory usage statistics (Used/Free/Total)
- Animated RAM cleaning simulation
- Neon cyan theme with pulsing animations

### ⚡ CPU Monitor
- Live CPU usage display with circular gauge
- Multi-core CPU information
- CPU temperature monitoring
- Frequency display for each core
- Electric purple theme

### 📦 Storage Cleaner
- Storage usage monitoring with progress bar
- Cache size calculation and display
- Animated scanning progress
- One-tap cache cleaning
- Neon green theme

### 🔋 Battery Saver
- Real-time battery level monitoring
- Battery status, health, temperature, and voltage display
- Circular battery indicator
- Electric orange theme

## 🎨 UI Design

- **Color Scheme**: Neon cyan (#00D4FF), Electric purple (#7B68EE), Deep black (#0A0A0A), Neon green (#00FF88), Electric orange (#FFB800)
- **Design Style**: Dark glass-morphism with glowing borders and neon accents
- **Fonts**: Orbitron (headlines), Rajdhani (body text)
- **Animations**: Pulse effects, circular progress rings, smooth transitions

## 📱 Android Compatibility

- **Minimum SDK**: Android 4.4.2 (API 19)
- **Target SDK**: Android 15 (API 35)
- **Compatible with**: Android 4.4+ up to Android 15

## 🛠️ Setup Instructions

### Prerequisites

1. **Android Studio**: Download from [developer.android.com](https://developer.android.com/studio)
2. **JDK 17**: Required for Android Gradle Plugin 8.x
3. **Android SDK**: API 35 (Android 15) must be installed

### Import Project

1. Open Android Studio
2. Select `File` → `Open`
3. Navigate to the `TurboLiteOptimizer` folder
4. Click `OK`
5. Wait for Gradle sync to complete

### Download Required Fonts

The app uses custom fonts that need to be downloaded separately:

1. **Orbitron**: Download from [Google Fonts](https://fonts.google.com/specimen/Orbitron)
   - Download both Bold (700) and Medium (500) weights
   - Rename to: `orbitron_bold.ttf`, `orbitron_medium.ttf`

2. **Rajdhani**: Download from [Google Fonts](https://fonts.google.com/specimen/Rajdhani)
   - Download both Bold (700) and Medium (500) weights
   - Rename to: `rajdhani_bold.ttf`, `rajdhani_medium.ttf`

3. Place all `.ttf` files in: `app/src/main/res/font/`
4. Delete the `font_placeholder.xml` file

**Note**: The app will use system default fonts if custom fonts are not provided.

### Add App Icon

Create or download an app icon and place it in the appropriate mipmap folders:
- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48x48 px)
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72x72 px)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96x96 px)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144x144 px)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192x192 px)

Use the same icon naming for round icons: `ic_launcher_round.png`

## 🚀 Build & Run

### Using Android Studio

1. Connect an Android device via USB or start an Android emulator
2. Click the green `Run` button (or press `Shift + F10`)
3. Select your device/emulator
4. Wait for the app to build and install

### Using Command Line

**On Linux/Mac:**
```bash
cd TurboLiteOptimizer
./gradlew assembleDebug
```

**On Windows:**
```cmd
cd TurboLiteOptimizer
gradlew.bat assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Install APK on Device

**Using ADB:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📋 Project Structure

```
TurboLiteOptimizer/
├── app/
│   ├── build.gradle                    # App-level Gradle configuration
│   ├── proguard-rules.pro              # ProGuard rules for release builds
│   └── src/main/
│       ├── AndroidManifest.xml         # App manifest
│       ├── java/com/turbolite/optimizer/
│       │   ├── OptimizerApp.kt         # Application class
│       │   ├── MainActivity.kt         # Main activity with tab navigation
│       │   ├── fragments/              # UI fragments
│       │   │   ├── RamBoosterFragment.kt
│       │   │   ├── CpuMonitorFragment.kt
│       │   │   ├── StorageCleanerFragment.kt
│       │   │   └── BatterySaverFragment.kt
│       │   ├── utils/                  # Utility classes
│       │   │   ├── MemoryUtils.kt
│       │   │   ├── CpuUtils.kt
│       │   │   └── StorageUtils.kt
│       │   └── views/                  # Custom views
│       │       └── CircularProgressView.kt
│       └── res/                        # Resources
│           ├── layout/                 # XML layouts
│           ├── values/                 # Colors, strings, themes
│           ├── drawable/               # Drawable resources
│           ├── anim/                   # Animations
│           ├── font/                   # Fonts (add .ttf files here)
│           └── mipmap-*/              # App icons
├── build.gradle                        # Project-level Gradle configuration
├── settings.gradle                     # Gradle settings
├── gradle.properties                   # Gradle properties
└── README.md                          # This file
```

## 🔧 Customization

### Change Theme Colors

Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="neon_cyan">#00D4FF</color>
<color name="electric_purple">#7B68EE</color>
<color name="neon_green">#00FF88</color>
<color name="electric_orange">#FFB800</color>
```

### Modify Update Intervals

In fragment files, adjust the monitoring update interval:
```kotlin
private val updateInterval = 2000L  // milliseconds
```

### Adjust Animations

Modify animation files in `app/src/main/res/anim/`:
- `pulse_animation.xml` - Pulsing effect
- `rotate_animation.xml` - Rotation effect

## 🐛 Troubleshooting

### Gradle Sync Failed

- Ensure JDK 17 is installed and configured
- Check internet connection (Gradle needs to download dependencies)
- Try `File` → `Invalidate Caches / Restart`

### SDK Not Found

- Open SDK Manager: `Tools` → `SDK Manager`
- Install Android SDK Platform 35 (Android 15)
- Install Android SDK Build-Tools 35.0.0

### App Crashes on Older Devices

- The app uses MultiDex for Android 4.4 compatibility
- Ensure `multiDexEnabled true` is in `build.gradle`
- Check that `OptimizerApp` extends `MultiDexApplication`

### Custom Fonts Not Working

- Verify font files are in `app/src/main/res/font/`
- Check file naming matches references in `themes.xml`
- Clean and rebuild: `Build` → `Clean Project` → `Rebuild Project`

## 📄 License

This project is provided as-is for educational and personal use.

## 🙏 Acknowledgments

- Inspired by modern Android optimization apps (Clean Master, CCleaner)
- UI design based on glass-morphism and neon aesthetic trends
- Uses Material Design 3 components

## 📞 Support

For issues or questions about compiling and running this app:
1. Check the Troubleshooting section above
2. Verify all prerequisites are installed
3. Ensure Android Studio is up to date

---

**Note**: This is a demonstration app that simulates optimization features. Actual system optimization capabilities are limited by Android's security model and permissions.
