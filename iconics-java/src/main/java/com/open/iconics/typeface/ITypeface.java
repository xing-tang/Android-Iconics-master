package com.open.iconics.typeface;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Collection;
import java.util.HashMap;

public interface ITypeface {

    IIcon getIcon(String key);

    HashMap<String, Character> getCharacters();

    /**
     * The Mapping Prefix to identify this font
     * must have a length of 3
     *
     * @return mappingPrefix (length = 3)
     */
    String getMappingPrefix();

    String getFontName();

    String getVersion();

    int getIconCount();

    Collection<String> getIcons();

    String getAuthor();

    String getUrl();

    String getDescription();

    String getLicense();

    String getLicenseUrl();

    Typeface getTypeface(Context ctx);

}
