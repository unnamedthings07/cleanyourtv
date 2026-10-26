# UNNAMED TV Launcher — Product Specification

Status: Home-screen foundation in progress. This file is the agreed product baseline, not a claim that the app has been built or tested on the TV.

## Target device
- TV model supplied by the user: **SWTV-20AE**.
- CleanYourTV's matching device entry identifies the Karbonn/Skyworth SW4H variant as Android TV 10 (API 29), 32-bit ARM with about 1.68 GB RAM. Verify the exact running firmware with ADB before installing or switching Home.

## Product goal
Build a clean, lightweight, native Android TV launcher branded **UNNAMED**, using five locally stored user-provided Prabhas / FAUZI wallpapers. Keep the existing CleanYourTV setup, debloat scripts, and restore tooling safe and unchanged.

## Home screen
- Full-screen, static wallpaper chosen randomly from the five selected images on every genuine Home entry.
- Avoid selecting the same wallpaper twice in a row when at least two wallpapers are available.
- No slideshow, video backgrounds, network content requests, recommendations, sponsored tiles, streaming-content rows, analytics, or unnecessary background services.
- Top header matching the user's reference: UNNAMED wordmark, greeting, clock/date, and three compact glass-style controls: Inputs, Wi-Fi/network, Settings.
- Six-icon main dock close to the bottom, matching the user's reference: large rounded rectangular app artwork, consistent dimensions/spacing, dark translucent rounded dock band, and a subtle crisp focus rim/lift.
- Show the focused app's name in large type above the dock. Do not put app captions under every tile.
- A small portion of the next icon row peeks into view at the bottom.
- D-pad Down scrolls rows upward into view; D-pad Up returns toward the prior row. Left/Right moves across the current six-column row. Navigation must be deterministic.
- Wallpaper stays unchanged throughout one Home visit, including row navigation and launcher-owned UI.
- Discover apps installed locally and launch them with OK. Do not fabricate app tiles for missing apps.

## Preferred app order
When present on the TV, sort these apps first in exactly this order:
1. Netflix
2. Prime Video
3. JioHotstar
4. NoTubeTV (package commonly used by the app: `com.ycngmn.notubetv`)
5. VLC
6. Airtel Xstream

Other installed launchable apps follow these preferred entries.

## Wallpapers
Use exactly the five selected images: archer battlefield, royal-costume portrait, Salaar-style action image, crouched battlefield image, and the wide FAUZI “Rebel Soldier” poster. Do not include the earlier vertical “File No. 025” medallion poster. Store locally, optimize for 16:9 display, preserve important poster content where practical, and avoid loading all wallpapers into memory at once.

## Startup logo
- Show the supplied UNNAMED eclipse wordmark full-screen on black for approximately one second when Home is entered, then reveal the launcher.
- No audio, video, network request, or heavy effects.
- Verify on the actual TV whether pressing Home while already on Home delivers a new intent/event. Some firmware may need a separate opt-in key helper; do not assume this behavior works until tested.

## Performance and storage
- Native Android app, landscape/TV layout, D-pad first.
- Keep the drawing and transitions lightweight. No network permission unless explicitly justified later.
- The user reports low TV storage; inspect free space before installing repeated builds. Do not factory-reset the TV or delete unknown directories as part of development.

## Development safety
- Keep UNNAMED in a separate app/project and package so it can be tested alongside the existing launcher.
- Do not change the default Home app or disable the stock/reference launcher until installed, tested with the physical remote, and the user chooses to switch.
- Never modify CleanYourTV safety/restore scripts as part of this launcher work.
- Confirm actual compatibility, app visibility, Inputs control support, storage, and Home-key behavior on the TV.
