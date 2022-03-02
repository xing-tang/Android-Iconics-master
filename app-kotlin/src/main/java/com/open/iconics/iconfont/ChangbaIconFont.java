package com.open.iconics.iconfont;

import android.graphics.Typeface;

import androidx.annotation.NonNull;

import com.open.iconics.application.BaseApplication;
import com.open.iconics.typeface.IIcon;
import com.open.iconics.typeface.ITypeface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ChangbaIconFont
 */
public class ChangbaIconFont implements ITypeface {

    private static final String TTF_FILE = "iconfont.ttf";
    private static HashMap<String, Character> mChars;
    private static Typeface typeface = null;

    @NonNull
    @Override
    public Typeface getRawTypeface() {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(BaseApplication.Companion.getInstance()
                        .getApplicationContext().getAssets(), "iconfont/" + TTF_FILE);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return typeface;
    }

    @Override
    public int getFontRes() {
        return 0;
    }

    @NonNull
    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<>();
            for (ChangbaFont icon : ChangbaFont.values()) {
                aChars.put(icon.name(), icon.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @NonNull
    @Override
    public String getMappingPrefix() {
        return "fon";
    }

    @NonNull
    @Override
    public String getFontName() {
        return "CbIconFont";
    }


    @NonNull
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @NotNull
    @Override
    public List<String> getIcons() {
        List<String> icons = new ArrayList<>();
        for (ChangbaFont value : ChangbaFont.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "ChangBa";
    }

    @NonNull
    @Override
    public String getUrl() {
        return "";
    }

    @NonNull
    @Override
    public String getDescription() {
        return "";
    }

    @NonNull
    @Override
    public String getLicense() {
        return "";
    }

    @NonNull
    @Override
    public String getLicenseUrl() {
        return "";
    }

    @NonNull
    @Override
    public IIcon getIcon(@NonNull String key) {
        return ChangbaFont.valueOf(key);
    }
}
