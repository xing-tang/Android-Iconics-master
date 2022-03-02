package com.open.iconics.typeface;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Collection;
import java.util.HashMap;

public class GenericFont implements ITypeface {
    private String mFontName;
    private String mAuthor;
    private String mMappingPrefix;
    private String mFontFile;

    private Typeface typeface = null;
    private HashMap<String, Character> mChars = new HashMap<>();

    protected GenericFont() {
    }

    public GenericFont(String mappingPrefix, String fontFile) {
        this("GenericFont", "GenericAuthor", mappingPrefix, fontFile);
    }

    public GenericFont(String fontName, String author, String mappingPrefix, String fontFile) {
//        if (mappingPrefix.length() != 3) {
//            throw new IllegalArgumentException("MappingPrefix must be 3 char long");
//        }
        this.mFontName = fontName;
        this.mAuthor = author;
        this.mMappingPrefix = mappingPrefix;
        this.mFontFile = fontFile;
    }

    public void registerIcon(String name, char aChar) {
        mChars.put(mMappingPrefix + "_" + name, aChar);
    }

    @Override
    public IIcon getIcon(String key) {
        return new Icon(mChars.get(key)).withTypeface(this);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        return new HashMap<>();
    }

    @Override
    public String getMappingPrefix() {
        return mMappingPrefix;
    }

    @Override
    public String getFontName() {
        return mFontName;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        return mChars.keySet();
    }

    @Override
    public String getAuthor() {
        return mAuthor;
    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLicense() {
        return "";
    }

    @Override
    public String getLicenseUrl() {
        return "";
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), mFontFile);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public class Icon implements IIcon {

        private String mName;
        private char aChar;
        private ITypeface mTypeface;

        public Icon(char c) {
            this.aChar = c;
        }

        public Icon(String name, char c) {
            this.mName = name;
            this.aChar = c;
        }

        public Icon withTypeface(ITypeface typeface) {
            this.mTypeface = typeface;
            return this;
        }

        @Override
        public String getFormattedName() {
            return "{" + getName() + "}";
        }

        @Override
        public String getName() {
            if (mName != null) {
                return mName;
            }
            return String.valueOf(aChar);
        }

        @Override
        public char getCharacter() {
            return aChar;
        }

        @Override
        public ITypeface getTypeface() {
            if (mTypeface != null) {
                return mTypeface;
            } else {
                return GenericFont.this;
            }
        }
    }
}
