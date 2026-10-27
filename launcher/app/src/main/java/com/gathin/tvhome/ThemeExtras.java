package com.gathin.tvhome;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Extra theme keys read straight from the pushed config.json (Config ignores keys it does not know):
 * "splash" (logo for about a second on every Home press), "wallpaper_on_home" (next wallpaper each time Home opens),
 * "brand_text" (a small label above the greeting) and "wifi_full_name" (the Wi-Fi pill shows the whole name).
 * Anything missing or unreadable leaves the feature off, so a bad file can never break Home.
 */
final class ThemeExtras {
    final boolean splash;
    final boolean wallpaperOnHome;
    final boolean wifiFullName;
    final String brandText;

    private ThemeExtras(boolean splash, boolean wallpaperOnHome, boolean wifiFullName, String brandText) {
        this.splash = splash;
        this.wallpaperOnHome = wallpaperOnHome;
        this.wifiFullName = wifiFullName;
        this.brandText = brandText;
    }

    static ThemeExtras load(Context ctx) {
        try {
            File dir = Config.dir(ctx);
            File file = dir != null ? new File(dir, Config.FILE_NAME) : null;
            if (file == null || !file.isFile() || file.length() == 0 || file.length() > 256 * 1024) return off();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (FileInputStream in = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int n;
                while ((n = in.read(buffer)) > 0) out.write(buffer, 0, n);
            }
            String text = new String(out.toByteArray(), StandardCharsets.UTF_8);
            if (text.startsWith("\uFEFF")) text = text.substring(1);
            JSONObject json = new JSONObject(text);
            return new ThemeExtras(json.optBoolean("splash", false), json.optBoolean("wallpaper_on_home", false),
                    json.optBoolean("wifi_full_name", false), json.optString("brand_text", "").trim());
        } catch (Exception | OutOfMemoryError ignored) {
            return off();
        }
    }

    private static ThemeExtras off() {
        return new ThemeExtras(false, false, false, "");
    }

    /** A small spaced-out label (UNNAMED) above the greeting, in the same column as the clock. */
    void addBrand(TextView greeting, float density) {
        if (brandText.isEmpty() || greeting == null || !(greeting.getParent() instanceof LinearLayout)) return;
        LinearLayout block = (LinearLayout) greeting.getParent();
        TextView label = new TextView(greeting.getContext());
        label.setText(brandText);
        label.setSingleLine(true);
        label.setAllCaps(true);
        label.setFocusable(false);
        label.setTextColor(0xFFFFD58A);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        label.setLetterSpacing(0.5f);
        label.setTypeface(greeting.getTypeface(), Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Math.round(4 * density), 0, 0, Math.round(6 * density));
        block.addView(label, 0, params);
    }
}
