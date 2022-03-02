package com.open.iconics;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.open.iconics.iconfont.DevIcon;
import com.open.iconics.utils.IconicsUtils;
import com.open.iconics.view.IconicsImageButton;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class IconicsButtonActivity extends AppCompatActivity {

    private AppCompatButton button;
    private IconicsImageButton imageIconicsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconics_button);
        initView();
    }

    private void initView() {
        button = (AppCompatButton) findViewById(R.id.button);
        imageIconicsButton = (IconicsImageButton) findViewById(R.id.image_iconics_button);

        new Iconics.Builder()
                .style(new BackgroundColorSpan(ContextCompat.getColor(this, R.color.transparent)))
                .style(new RelativeSizeSpan(1f))
                .style(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)))
                .styleFor(DevIcon.Icon.dev_ssh_plain
                        , new BackgroundColorSpan(Color.RED)
                        , new ForegroundColorSpan(Color.parseColor("#ee000000"))
                        , new RelativeSizeSpan(2f))
                .on(button)
                .build();
        imageIconicsButton.setOnClickListener(view -> {
            IconicsDrawable drawable = new IconicsDrawable(view.getContext(), DevIcon.Icon.dev_ssh_plain);
            drawable.setSizeXPx(IconicsUtils.convertDpToPx(view.getContext(), 150));
            drawable.setSizeYPx(IconicsUtils.convertDpToPx(view.getContext(), 150));
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
            // imageIconicsButton.setIcon(drawable);
            imageIconicsButton.setImageBitmap(drawable.toBitmap());
        });
    }
}