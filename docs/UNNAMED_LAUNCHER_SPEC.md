# UNNAMED TV Launcher — Product Specification

Status: Phase 1 / requirements baseline. This file records the agreed direction; it is not an implementation.

## Product goal
Build a clean, lightweight, native Android TV launcher branded UNNAMED, using a locally stored set of five user-provided Prabhas/FAUZI wallpapers. Preserve the existing CleanYourTV setup and recovery tools.

## Home screen
- Full-screen static wallpaper selected randomly each time Home is entered.
- Avoid selecting the same wallpaper two Home entries in a row when at least two images are available.
- No slideshow, video backgrounds, remote content requests, recommendations, sponsored tiles, or streaming-content rows.
- Small top header: UNNAMED wordmark, time, date, and compact essential controls.
- Essential controls requested: Inputs, network status/name, Settings. Each should use Android/TV APIs where available and degrade safely when unsupported.
- Six-icon main dock near the bottom, matching the user's reference: large rounded rectangular app artwork, consistent dimensions and spacing, soft dark rounded dock band, recognizable icons/artwork, selected app name shown in large type above the dock, subtle lift/outline focus treatment.
- A small portion of the next app row remains visible at the bottom as an affordance.
- D-pad Down scrolls the app rows upward so the next row comes into view; D-pad Up returns toward the previous row. Keep navigation deterministic and restore/remember focus as sensible.
- Discover installed TV apps locally. Press OK to launch. No hard-coded assumption that every service app is installed.
- Wallpaper remains unchanged during a single Home visit, including while navigating rows or opening a launcher-owned settings overlay.

## Startup logo
- Show the user's supplied UNNAMED eclipse wordmark full-screen on black for approximately 1 second when Home is entered, then show Home.
- No audio, video, network requests, or heavy effects for the intro.
- Confirm whether target TV firmware delivers Home as a relaunch/resume event or handles it internally; implement and test against the actual TV before relying on the intro firing on every Home press.

## Performance and storage
- Native Android app, landscape/TV layout, remote-first.
- Keep UI and transitions lightweight. No background polling, analytics, ad SDKs, or unnecessary permissions.
- Store wallpaper assets locally; optimize/compress for TV resolution without needlessly duplicating large files. Load only the selected wallpaper into memory.
- User reports TV storage is low; inspect available space before repeated installs and avoid destructive cleanup commands.
- Do not factory reset or uninstall/disable system packages as part of launcher development.

## Development safety
- Do not modify CleanYourTV safety/restore scripts or the existing reference launcher's behavior as part of this specification phase.
- Build UNNAMED in a separate project/package/module and keep it installable alongside the existing launcher during testing.
- Do not set it as the default Home or disable the stock/reference launcher until it has been installed, tested with the physical remote, and the user explicitly chooses to switch.
- Verify compatibility using the actual TV model, Android SDK level, HOME behavior, and app visibility restrictions.
- Use the CleanYourTV test/build guidance as a reference; verify any changes on the real TV rather than relying solely on a mockup.

## Decisions still to confirm
- Exact order/names of the five wallpapers, and verify which of the uploaded image files constitute the chosen five.
- Exact six apps in the main dock; use installed-app discovery and offer a way to configure ordering.
- Whether the top-right network control should display the current Wi-Fi network name or a generic network indicator if firmware restricts reading it.
- Location/appearance for the compact Inputs, network and Settings controls, consistent with the supplied reference header.
- Exact TV make/model/Android version, storage free space, and supported build/install route.
