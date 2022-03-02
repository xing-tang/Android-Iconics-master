package com.open.iconics;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.open.iconics.animation.BlinkAlphaProcessor;
import com.open.iconics.animation.BlinkScaleProcessor;
import com.open.iconics.animation.IconicsAnimatedDrawable;
import com.open.iconics.animation.SpinProcessor;
import com.open.iconics.iconfont.ChangbaFont;
import com.open.iconics.utils.IconicsUtils;
import com.open.iconics.view.IconicsImageView;
import com.open.iconics.view.IconicsTextView;

public class IconicsTextViewActivity extends AppCompatActivity {

    private IconicsTextView textIconicsView;
    private IconicsImageView imageIconicsView;
    private Button buttonLoad;
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonResume;
    private Button buttonStop;

    private IconicsDrawable drawable = null;
    private IconicsAnimatedDrawable animatedDrawable = null;
    private BlinkAlphaProcessor blinkAlphaProcessor = null;
    private BlinkScaleProcessor blinkScaleProcessor = null;
    private SpinProcessor spinProcessor = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconics_text_view);
        initView();
    }

    private void initView() {
        textIconicsView = (IconicsTextView) findViewById(R.id.text_iconics_view);
        imageIconicsView = (IconicsImageView) findViewById(R.id.image_iconics_view);
        buttonLoad = (Button) findViewById(R.id.button1);
        buttonStart = (Button) findViewById(R.id.button2);
        buttonPause = (Button) findViewById(R.id.button3);
        buttonResume = (Button) findViewById(R.id.button4);
        buttonStop = (Button) findViewById(R.id.button5);

        blinkAlphaProcessor = new BlinkAlphaProcessor();
        blinkScaleProcessor = new BlinkScaleProcessor();
        spinProcessor = new SpinProcessor();

        buttonLoad.setOnClickListener(view -> {
            drawable = new IconicsDrawable(view.getContext(), ChangbaFont.font_rightarrows_gray);
            drawable.setSizeXPx(IconicsUtils.convertDpToPx(view.getContext(), 35));
            drawable.setSizeYPx(IconicsUtils.convertDpToPx(view.getContext(), 35));
            // drawable.setPaddingPx(IconicsUtils.convertDpToPx(view.getContext(), 4));
            // drawable.setRoundedCornerRxPx(IconicsUtils.convertDpToPx(view.getContext(), 8));
            // drawable.setRoundedCornerRyPx(IconicsUtils.convertDpToPx(view.getContext(), 8));
            // drawable.setColorList(ContextCompat.getColorStateList(view.getContext(), R.color.red));
            drawable.apply(iconicsDrawable -> {
                iconicsDrawable.setPaddingPx(IconicsUtils.convertDpToPx(view.getContext(), 4));
                iconicsDrawable.setRoundedCornerRxPx(IconicsUtils.convertDpToPx(view.getContext(), 8));
                iconicsDrawable.setRoundedCornerRyPx(IconicsUtils.convertDpToPx(view.getContext(), 8));
                iconicsDrawable.setColorList(ContextCompat.getColorStateList(view.getContext(), R.color.red));
                return null;
            });
            textIconicsView.setDrawableForAll(drawable);
        });
        buttonStart.setOnClickListener(view -> {
            if (drawable != null) {
                animatedDrawable = drawable.toAnimatedDrawable();
                // animatedDrawable.processor(blinkAlphaProcessor);
                animatedDrawable.processor(blinkScaleProcessor);
                // animatedDrawable.processor(spinProcessor);
                // animatedDrawable.animateIn(textIconicsView);
                textIconicsView.setDrawableForAll(animatedDrawable);
            }
        });
        buttonPause.setOnClickListener(view -> {
            if (blinkAlphaProcessor != null) blinkAlphaProcessor.pause();
            if (blinkScaleProcessor != null) blinkScaleProcessor.pause();
            if (spinProcessor != null) spinProcessor.pause();
        });
        buttonResume.setOnClickListener(view -> {
            if (blinkAlphaProcessor != null) blinkAlphaProcessor.resume();
            if (blinkScaleProcessor != null) blinkScaleProcessor.resume();
            if (spinProcessor != null) spinProcessor.resume();
        });
        buttonStop.setOnClickListener(view -> {
            if (blinkAlphaProcessor != null) blinkAlphaProcessor.end();
            if (blinkScaleProcessor != null) blinkScaleProcessor.end();
            if (spinProcessor != null) spinProcessor.end();
        });
    }
}