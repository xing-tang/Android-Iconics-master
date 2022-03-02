package com.open.iconics.context;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.view.LayoutInflater;

/**
 * https://github.com/chrisjenx/Calligraphy
 */
public class IconicsContextWrapper extends ContextWrapper {

    private LayoutInflater mInflater;

    private IconicsContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context base) {
        return new IconicsContextWrapper(base);
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = new InternalLayoutInflater(LayoutInflater.from(getBaseContext()), this, false);
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }
}