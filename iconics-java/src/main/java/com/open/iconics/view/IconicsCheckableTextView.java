package com.open.iconics.view;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.widget.Checkable;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.widget.TextViewCompat;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.internal.CheckedCompoundIconicsDrawables;
import com.open.iconics.internal.CompoundIconsBundle;
import com.open.iconics.internal.IconicsViewsAttrsApplier;
import com.open.iconics.internal.IconicsViewsUtils;
import com.open.iconics.utils.Utils;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class IconicsCheckableTextView extends IconicsTextView implements Checkable, CheckedCompoundIconicsDrawables {
    protected CompoundIconsBundle mCheckedIconsBundle;
    private boolean mAnimateChanges;

    private boolean mChecked;
    private boolean mBroadcasting;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public IconicsCheckableTextView(Context context) {
        this(context, null);
    }

    public IconicsCheckableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public IconicsCheckableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void initialize(Context context, AttributeSet attrs, int defStyle) {
        mCheckedIconsBundle = new CompoundIconsBundle();
        setFocusable(true);
        setClickable(true);
        //taking normal state attrs
        super.applyAttr(context, attrs, defStyle);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mBottomIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mTopIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mEndIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mStartIcon, this);

        //taking checked state attrs
        applyAttr(context, attrs, defStyle);

        IconicsViewsUtils.checkAnimation(mCheckedIconsBundle.mBottomIcon, this);
        IconicsViewsUtils.checkAnimation(mCheckedIconsBundle.mTopIcon, this);
        IconicsViewsUtils.checkAnimation(mCheckedIconsBundle.mEndIcon, this);
        IconicsViewsUtils.checkAnimation(mCheckedIconsBundle.mStartIcon, this);
        //setting created icons
        setIcons();
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void applyAttr(Context context, AttributeSet attrs, int defStyle) {
        IconicsViewsAttrsApplier.readIconicsCheckableTextView(context, attrs, mCheckedIconsBundle);
        mAnimateChanges = IconicsViewsAttrsApplier.isIconicsAnimateChanges(context, attrs);
    }

    private void setIcons() {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this,
                createStatesStart(),
                createStatesTop(),
                createStatesEnd(),
                createStatesBottom()
        );
    }

    private StateListDrawable createStatesStart() {
        return Utils.getCheckableIconStateList(getContext(), mIconsBundle.mStartIcon,
                mCheckedIconsBundle.mStartIcon, mAnimateChanges);
    }

    private StateListDrawable createStatesTop() {
        return Utils.getCheckableIconStateList(getContext(), mIconsBundle.mTopIcon,
                mCheckedIconsBundle.mTopIcon, mAnimateChanges);
    }

    private StateListDrawable createStatesEnd() {
        return Utils.getCheckableIconStateList(getContext(), mIconsBundle.mEndIcon,
                mCheckedIconsBundle.mEndIcon, mAnimateChanges);
    }

    private StateListDrawable createStatesBottom() {
        return Utils.getCheckableIconStateList(getContext(), mIconsBundle.mBottomIcon,
                mCheckedIconsBundle.mBottomIcon, mAnimateChanges);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IconicsCheckableTextView.class.getName();
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean performClick() {
        toggle();

        final boolean handled = super.performClick();
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK);
        }

        return handled;
    }

    //region CheckedCompoundIconicsDrawablesImpl
    @Nullable
    @Override
    public IconicsDrawable getCheckedIconicsDrawableStart() {
        return mCheckedIconsBundle.mStartIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getCheckedIconicsDrawableTop() {
        return mCheckedIconsBundle.mTopIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getCheckedIconicsDrawableEnd() {
        return mCheckedIconsBundle.mEndIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getCheckedIconicsDrawableBottom() {
        return mCheckedIconsBundle.mBottomIcon;
    }

    @Override
    public void setCheckedDrawableStart(@Nullable IconicsDrawable drawable) {
        mCheckedIconsBundle.mStartIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setCheckedDrawableTop(@Nullable IconicsDrawable drawable) {
        mCheckedIconsBundle.mTopIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setCheckedDrawableEnd(@Nullable IconicsDrawable drawable) {
        mCheckedIconsBundle.mEndIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setCheckedDrawableBottom(@Nullable IconicsDrawable drawable) {
        mCheckedIconsBundle.mBottomIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setCheckedDrawableForAll(@Nullable IconicsDrawable drawable) {
        mCheckedIconsBundle.mStartIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mCheckedIconsBundle.mTopIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mCheckedIconsBundle.mEndIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mCheckedIconsBundle.mBottomIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }
    //endregion

    public interface OnCheckedChangeListener {
        void onCheckedChanged(IconicsCheckableTextView buttonView, boolean isChecked);
    }
}