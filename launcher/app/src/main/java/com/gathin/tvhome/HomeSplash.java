package com.gathin.tvhome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * The logo (res/drawable-nodpi/prabhas_splash.jpg) over Home for about a second each time Home is pressed.
 * The picture is decoded only when it is shown, at screen size in RGB_565, and released again when it fades,
 * so nothing stays in memory between presses.
 */
final class HomeSplash {
    private static final long SHOW_MS = 1000;
    private static final long FADE_MS = 160;

    private final Context context;
    private final FrameLayout root;
    private final Runnable hide = this::fadeOut;
    private ImageView view;
    private Bitmap bitmap;

    HomeSplash(Context context, FrameLayout root) {
        this.context = context;
        this.root = root;
    }

    void show() {
        if (view == null) {
            bitmap = decode();
            if (bitmap == null) return;
            view = new ImageView(context);
            view.setBackgroundColor(Color.BLACK);
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view.setImageBitmap(bitmap);
            view.setFocusable(false);
            view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
            root.addView(view, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            view.animate().cancel();
            view.removeCallbacks(hide);
        }
        view.setAlpha(1f);
        view.bringToFront();
        view.postDelayed(hide, SHOW_MS);
    }

    private void fadeOut() {
        if (view == null) return;
        view.animate().alpha(0f).setDuration(FADE_MS).withEndAction(this::remove).start();
    }

    private void remove() {
        if (view != null) {
            root.removeView(view);
            view.setImageDrawable(null);
            view = null;
        }
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    private Bitmap decode() {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), R.drawable.prabhas_splash, options);
            int sample = 1;
            while (options.outHeight / (sample * 2) >= metrics.heightPixels) sample *= 2;
            options.inJustDecodeBounds = false;
            options.inSampleSize = sample;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.prabhas_splash, options);
        } catch (Exception | OutOfMemoryError failure) {
            return null;
        }
    }
}
