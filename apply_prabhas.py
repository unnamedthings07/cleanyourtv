# Run from the cleanyourtv folder:  python apply_prabhas.py
# Edits MainActivity.java and WallpaperPlayer.java by matching text, so it works even if line numbers differ.
import os, sys
J = os.path.join("launcher", "app", "src", "main", "java", "com", "gathin", "tvhome")
if not os.path.isdir(J):
    sys.exit("Run this from the cleanyourtv folder (the one containing launcher, themes and tools).")

def edit(name, steps):
    p = os.path.join(J, name)
    s = open(p, encoding="utf-8", newline="").read()
    crlf = "\r\n" in s
    s = s.replace("\r\n", "\n")
    bad = 0
    for label, marker, old, new in steps:
        if marker in s:
            print(f"  skip (already done): {label}"); continue
        if old not in s:
            print(f"  FAILED: {label}"); bad += 1; continue
        s = s.replace(old, new, 1); print(f"  ok: {label}")
    if bad:
        print(f"{name}: NOT written because {bad} step(s) failed. Send me this output."); return False
    if crlf: s = s.replace("\n", "\r\n")
    open(p, "w", encoding="utf-8", newline="").write(s); return True

ok1 = edit("WallpaperPlayer.java", [
 ("fields", "changeOnHome;", "    private boolean loading;\n",
  "    private boolean loading;\n    private final boolean changeOnHome;\n    private boolean started;\n    private boolean advance;\n"),
 ("read key", "ThemeExtras.load(context).wallpaperOnHome", "        order = config.wallpaperOrder;\n",
  "        order = config.wallpaperOrder;\n        changeOnHome = ThemeExtras.load(context).wallpaperOnHome;\n"),
 ("start()", "if (changeOnHome && started)", "    void start() {\n        active = true;",
  "    void start() {\n        if (changeOnHome && started) advance = true;\n        started = true;\n        active = true;"),
 ("update()", "advance = false;",
  "        if (selected == null || WallpaperRotation.due(changed, System.currentTimeMillis(), interval)) {\n",
  "        if (selected == null || advance || WallpaperRotation.due(changed, System.currentTimeMillis(), interval)) {\n            advance = false;\n"),
])
ok2 = edit("MainActivity.java", [
 ("field", "private HomeSplash homeSplash;", "    private WallpaperPlayer wallpaperPlayer;\n",
  "    private WallpaperPlayer wallpaperPlayer;\n    private HomeSplash homeSplash;\n"),
 ("create splash", "new HomeSplash(this, root)", "        wallpaperPlayer = new WallpaperPlayer(this, root, cfg);\n",
  "        wallpaperPlayer = new WallpaperPlayer(this, root, cfg);\n        ThemeExtras extras = ThemeExtras.load(this);\n        if (extras.splash) {\n            homeSplash = new HomeSplash(this, root);\n            if (savedInstanceState == null) root.post(homeSplash::show);\n        }\n"),
 ("brand label", ".addBrand(greeting", "        greeting = findViewById(R.id.greeting);\n",
  "        greeting = findViewById(R.id.greeting);\n        ThemeExtras.load(this).addBrand(greeting, getResources().getDisplayMetrics().density);\n"),
 ("splash on Home press", "if (homeSplash != null) homeSplash.show();", "        grid.scrollToPosition(0);\n        lastFocused = 0;\n",
  "        if (homeSplash != null) homeSplash.show();\n        grid.scrollToPosition(0);\n        lastFocused = 0;\n"),
 ("full Wi-Fi name", ".wifiFullName", "        wifiIconOnly = iconOnlyButtons && cfg.wifiLabelWidth <= 0;\n",
  "        wifiIconOnly = iconOnlyButtons && cfg.wifiLabelWidth <= 0;\n        if (!iconOnlyButtons && ThemeExtras.load(this).wifiFullName) btnWifi.setMaxWidth(Math.round(520 * getResources().getDisplayMetrics().density));\n"),
])
print("\nDONE" if ok1 and ok2 else "\nSOME FILES WERE NOT CHANGED")
