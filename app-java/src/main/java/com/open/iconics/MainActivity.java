package com.open.iconics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.open.iconics.view.IconicsButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IconicsButton iconicsButton;
    private IconicsButton iconicsImageButton;
    private IconicsButton iconicsImageView;
    private IconicsButton iconicsTextView;
    private IconicsButton iconicsCompoundButton;
    private IconicsButton iconicsCheckBox;
    private IconicsButton iconicsCheckableTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iconicsButton = (IconicsButton) findViewById(R.id.iconics_button);
        iconicsImageButton = (IconicsButton) findViewById(R.id.iconics_image_button);
        iconicsImageView = (IconicsButton) findViewById(R.id.iconics_image_view);
        iconicsTextView = (IconicsButton) findViewById(R.id.iconics_text_view);
        iconicsCompoundButton = (IconicsButton) findViewById(R.id.iconics_compound_button);
        iconicsCheckBox = (IconicsButton) findViewById(R.id.iconics_check_box);
        iconicsCheckableTextView = (IconicsButton) findViewById(R.id.iconics_checkable_text_view);

        iconicsButton.setOnClickListener(this);
        iconicsImageButton.setOnClickListener(this);
        iconicsImageView.setOnClickListener(this);
        iconicsTextView.setOnClickListener(this);
        iconicsCompoundButton.setOnClickListener(this);
        iconicsCheckBox.setOnClickListener(this);
        iconicsCheckableTextView.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iconics_button:
                startActivity(new Intent(this, IconicsButtonActivity.class));
                break;
            case R.id.iconics_image_button:
                startActivity(new Intent(this, IconicsImageButtonActivity.class));
                break;
            case R.id.iconics_image_view:
                startActivity(new Intent(this, IconicsImageViewActivity.class));
                break;
            case R.id.iconics_text_view:
                startActivity(new Intent(this, IconicsTextViewActivity.class));
                break;
            case R.id.iconics_compound_button:
                startActivity(new Intent(this, IconicsCompoundButtonActivity.class));
                break;
            case R.id.iconics_check_box:
                startActivity(new Intent(this, IconicsCheckBoxActivity.class));
                break;
            case R.id.iconics_checkable_text_view:
                startActivity(new Intent(this, IconicsCheckableTextViewActivity.class));
                break;
            default:
                break;
        }
    }
}
