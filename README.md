<div align="center">
  <img src="app/src/main/res/drawable/cts_extended_logo.png" width="160" height="160" alt="CTS-Extended Logo">
  <h1>CTS-Extended</h1>
  <p>Circle to Search for Android with multiple search engines, offline OCR, and custom gesture support.</p>
</div>

---

**CTS-Extended** is an open-source Android utility that lets you search, copy text, or scan codes from any screen by drawing a selection over it. It is a fork of [AKS-Labs/CircleToSearch](https://github.com/AKS-Labs/CircleToSearch) with added support for background broadcast triggers, release build crash fixes, and Xiaomi HyperOS / MIUI gesture bar integration.

Unlike Google's proprietary Circle to Search, this app runs without Google Play Services, works on any Android 10+ device, keeps text recognition and QR parsing 100% on-device, and supports multiple search providers (Google Lens, Bing, Yandex, TinEye, ChatGPT, and Perplexity).

---

## Changes in this Fork

- **Instant Gesture & Automation Triggers**: Added background broadcast support so gesture apps, Tasker, MacroDroid, and companion bridges can trigger search smoothly without delay or screen flicker.
- **Xiaomi HyperOS & MIUI Support**: Works with [HyperCTS Bridge](https://github.com/codingsenpi/HyperCTS-Bridge) to restore the navigation pill long-press gesture on Xiaomi devices.
- **Release Stability**: Fixed startup crashes affecting minified release builds.
- **Linux & CI Build Support**: Fixed native build scripts so the project compiles cleanly on Linux and automated CI workflows.

---

## Xiaomi HyperOS / MIUI Setup

Xiaomi's launcher (`com.miui.home`) ignores Android's default assistant setting and hardcodes long-pressing the navigation pill to XiaoAI (`com.miui.voiceassist/com.xiaomi.voiceassistant.VoiceService`).

To use CTS-Extended with the navigation pill gesture on HyperOS:

1. Install **CTS-Extended**.
2. Install [**HyperCTS Bridge**](https://github.com/codingsenpi/HyperCTS-Bridge).
3. Open HyperCTS Bridge and grant the **Display pop-up windows while running in the background** permission.
4. Long-press the navigation pill—it will now trigger CTS-Extended directly.

---

## Features

### Search Engines
- Reverse image search via **Google Lens**, **Bing Visual Search**, **Yandex**, **TinEye**, **ChatGPT**, and **Perplexity**.
- Quick toggle between engines directly from the bottom sheet.
- Optional desktop user-agent mode for search results.

### Text Recognition & Tools
- **On-device OCR**: Extract text locally using Tesseract OCR. No images are sent to remote servers unless you explicitly perform an image search.
- **Custom OCR models**: Support for importing custom `.traineddata` language files for specialized languages or scripts.
- **Offline QR & Barcode reader**: Scans and parses QR codes and barcodes directly from the screenshot.
- **Quick actions**: Copy text, save cropped images, or share them directly to other apps.

### Trigger Methods
- **Assist gesture**: Set CTS-Extended as your device's default digital assistant to launch via home button long-press or diagonal corner swipe.
- **Broadcast action**: Fire `com.codingsenpi.ctsextended.action.TRIGGER_SEARCH` from any automation tool or script.
- **Navigation pill on HyperOS**: Long-press the gesture bar using [HyperCTS Bridge](https://github.com/codingsenpi/HyperCTS-Bridge).
- **In-app triggers**: Floating action button, volume key combination, or double-tap status bar.

---

## Download

Download the latest universal APK from the [Releases](https://github.com/codingsenpi/CTS-Extended/releases) page:
- `CTS-Extended-v0.6.apk`

---

## Support

If you find this project useful, you can support its development on [GitHub Sponsors](https://github.com/sponsors/codingsenpi).

---

## Upstream & License

This project is a fork of [**AKS-Labs/CircleToSearch**](https://github.com/AKS-Labs/CircleToSearch) by AKS-Labs.  
Licensed under the [GNU General Public License v3.0](LICENSE).
