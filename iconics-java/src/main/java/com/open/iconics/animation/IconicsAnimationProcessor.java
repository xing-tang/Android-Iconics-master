package com.open.iconics.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.view.animation.LinearInterpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;

import com.open.iconics.IconicsBrush;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class IconicsAnimationProcessor {
    @NonNull
    private static final TimeInterpolator sDefaultInterpolator = new LinearInterpolator();

    @NonNull
    private final ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 100);

    @Nullable
    private IconicsAnimatedDrawable mDrawable;
    private boolean mIsStartRequested = false;

    /**
     * When the animation reaches the end and {@code repeatCount} is INFINITE
     * or a positive value, the animation restarts from the beginning.
     */
    public static final int RESTART = ValueAnimator.RESTART;
    /**
     * When the animation reaches the end and {@code repeatCount} is INFINITE
     * or a positive value, the animation reverses direction on every iteration.
     */
    public static final int REVERSE = ValueAnimator.REVERSE;
    /**
     * This value used used with the {@link #repeatCount(int)} property to repeat
     * the animation indefinitely.
     */
    public static final int INFINITE = ValueAnimator.INFINITE;

    @NonNull
    protected TimeInterpolator mInterpolator = sDefaultInterpolator;
    protected long mDuration = 300;
    protected int mRepeatCount = INFINITE;
    protected int mRepeatMode = RESTART;
    protected boolean mIsStartImmediately = true;

    @IntDef({RESTART, REVERSE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface RepeatMode {
    }

    /**
     * The set of listeners to be sent events through the life of an animation.
     */
    @Nullable
    private List<IconicsAnimationListener> mListeners = null;

    /**
     * The set of listeners to be sent pause/resume events through the life
     * of an animation.
     */
    @Nullable
    private List<IconicsAnimationPauseListener> mPauseListeners = null;

    private final Animator.AnimatorListener mProxyListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation, boolean isReverse) {
            forEachListeners(l -> l.onAnimationStart(IconicsAnimationProcessor.this, isReverse));
        }

        @Override
        public void onAnimationEnd(Animator animation, boolean isReverse) {
            forEachListeners(l -> l.onAnimationEnd(IconicsAnimationProcessor.this, isReverse));
        }

        @Override
        public void onAnimationStart(Animator animation) {
            forEachListeners(l -> l.onAnimationStart(IconicsAnimationProcessor.this));
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            forEachListeners(l -> l.onAnimationEnd(IconicsAnimationProcessor.this));
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            forEachListeners(l -> l.onAnimationCancel(IconicsAnimationProcessor.this));
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            forEachListeners(l -> l.onAnimationRepeat(IconicsAnimationProcessor.this));
        }

        private void forEachListeners(Consumer<IconicsAnimationListener> consumer) {
            if (mListeners == null) return;
            for (IconicsAnimationListener l : mListeners) {
                consumer.accept(l);
            }
        }
    };

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private Object mProxyPauseListener;

    /**
     * @return Tag which will be used to apply this processor via xml
     */
    @NonNull
    public abstract String animationTag();

    /**
     * Sets the length of the animation. The default duration is 300 milliseconds.
     *
     * @param duration The length of the animation. This value cannot be negative.
     */
    @NonNull
    public IconicsAnimationProcessor duration(long duration, @NonNull TimeUnit timeUnit) {
        mDuration = timeUnit.toMillis(duration);
        return this;
    }

    /**
     * Defines what this animation should do when it reaches the end. This
     * setting is applied only when the repeat count is either greater than
     * 0 or {@link #INFINITE}. Defaults to {@link #RESTART}.
     *
     * @param value {@link #RESTART} or {@link #REVERSE}
     */
    @NonNull
    public IconicsAnimationProcessor repeatMode(@RepeatMode int value) {
        mRepeatMode = value;
        return this;
    }

    /**
     * Sets how many times the animation should be repeated. If the repeat
     * count is 0, the animation is never repeated. If the repeat count is
     * greater than 0 or {@link #INFINITE}, the repeat mode will be taken
     * into account. The repeat count is {@link #INFINITE} by default.
     *
     * @param repeatCount the number of times the animation should be repeated
     */
    @NonNull
    public IconicsAnimationProcessor repeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
        return this;
    }

    /**
     * The time interpolator used in calculating the elapsed fraction of this animation. The
     * interpolator determines whether the animation runs with linear or non-linear motion,
     * such as acceleration and deceleration. The default value is
     * {@link LinearInterpolator}
     *
     * @param interpolator the interpolator to be used by this processor. A value of {@code null}
     *                     will result in linear interpolation.
     */
    @NonNull
    public IconicsAnimationProcessor interpolator(@NonNull TimeInterpolator interpolator) {
        if (interpolator != null) {
            mInterpolator = interpolator;
        } else {
            mInterpolator = sDefaultInterpolator;
        }
        return this;
    }

    /**
     * Set the flag for starting animation immediately after attach to drawable and after drawable
     * with the view is attached to window. Default value is {@code true}.
     *
     * @param startImmediately the flag
     */
    @NonNull
    public IconicsAnimationProcessor startImmediately(boolean startImmediately) {
        mIsStartImmediately = startImmediately;
        return this;
    }

    /**
     * Starts the animation, if processor is attached to drawable, otherwise sets flag to start
     * animation immediately after attaching
     */
    @NonNull
    public IconicsAnimationProcessor start() {
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.setDuration(mDuration);
        mAnimator.setRepeatCount(mRepeatCount);
        mAnimator.setRepeatMode(mRepeatMode);

        if (mDrawable != null) {
            mIsStartRequested = false;
            mAnimator.start();
        } else {
            mIsStartRequested = true;
        }
        return this;
    }

    /**
     * Adds a listener to the set of listeners that are sent events through the life of an
     * processor, such as start, repeat, and end.
     *
     * @param listener the listener to be added to the current set of listeners for this processor.
     */
    @NonNull
    public IconicsAnimationProcessor addListener(@NonNull IconicsAnimationListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
            mAnimator.addListener(mProxyListener);
        }
        mListeners.add(listener);
        return this;
    }

    /**
     * Removes a listener from the set listening to this processor.
     *
     * @param listener the listener to be removed from the current set of listeners for this
     *                 processor.
     */
    public void removeListener(@NonNull IconicsAnimationListener listener) {
        if (mListeners == null) {
            return;
        }
        mListeners.remove(listener);
        if (mListeners.size() == 0) {
            mListeners = null;
            mAnimator.removeListener(mProxyListener);
        }
    }

    /**
     * Adds a pause listener to this processor.
     *
     * @param listener the listener to be added to the current set of pause listeners
     *                 for this processor.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @NonNull
    public IconicsAnimationProcessor addPauseListener(@NonNull IconicsAnimationPauseListener listener) {
        if (mPauseListeners == null) {
            mPauseListeners = new ArrayList<>();
            if (mProxyPauseListener == null) {
                initProxyPauseListener();
            }
            mAnimator.addPauseListener((Animator.AnimatorPauseListener) mProxyPauseListener);
        }
        mPauseListeners.add(listener);
        return this;
    }

    /**
     * Removes a pause listener from the set listening to this processor.
     *
     * @param listener the listener to be removed from the current set of pause
     *                 listeners for this processor.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public void removePauseListener(@NonNull IconicsAnimationPauseListener listener) {
        if (mPauseListeners == null) {
            return;
        }
        mPauseListeners.remove(listener);
        if (mPauseListeners.size() == 0) {
            mPauseListeners = null;
            mAnimator.removePauseListener((Animator.AnimatorPauseListener) mProxyPauseListener);
        }
    }

    /**
     * Removes all {@link #addListener(IconicsAnimationListener) listeners} and
     * {@link #addPauseListener(IconicsAnimationPauseListener) pauseListeners} from this processor.
     */
    public void removeAllListeners() {
        if (mListeners != null) {
            mListeners.clear();
            mListeners = null;
            mAnimator.removeListener(mProxyListener);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mPauseListeners != null) {
                mPauseListeners.clear();
                mPauseListeners = null;
                mAnimator.removePauseListener((Animator.AnimatorPauseListener) mProxyPauseListener);
            }
        }
    }

    /**
     * Cancels the animation. Unlike {@link #end()}, {@code cancel()} causes the animation to
     * stop in its tracks, sending an
     * {@link IconicsAnimationListener#onAnimationCancel(IconicsAnimationProcessor)} to
     * its listeners, followed by an
     * {@link IconicsAnimationListener#onAnimationEnd(IconicsAnimationProcessor)} message.
     * <p>
     * This method must be called on the thread that is running the processor.
     */
    public void cancel() {
        mAnimator.cancel();
    }

    /**
     * Ends the animation. This causes the processor to assign the end value of the property being
     * animated, then calling the
     * {@link IconicsAnimationListener#onAnimationEnd(IconicsAnimationProcessor)} method on
     * its listeners.
     * <p>
     * This method must be called on the thread that is running the processor.
     */
    public void end() {
        mAnimator.end();
    }

    /**
     * @return Whether the processor has been started and not yet ended.
     */
    public boolean isStarted() {
        return mAnimator.isStarted();
    }

    /**
     * @return Whether the processor is running.
     */
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    /**
     * Plays the processor in reverse. If the processor is already running,
     * it will stop itself and play backwards from the point reached when reverse was called.
     * If the processor is not currently running, then it will start from the end and
     * play backwards.
     */
    public void reverse() {
        mAnimator.reverse();
    }

    /**
     * Pauses a running processor. This method should only be called on the same thread on
     * which the animation was started. If the animation has not yet been
     * {@link #isStarted() started} or has since ended, then the call is ignored. Paused
     * processors can be resumed by calling {@link #resume()}.
     *
     * @see #resume()
     * @see #isPaused()
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public void pause() {
        mAnimator.pause();
    }

    /**
     * Resumes a paused processor, causing the processor to pick up where it left off
     * when it was paused. This method should only be called on the same thread on
     * which the processor was started. Calls to {@code resume()} on an processor that is
     * not currently paused will be ignored.
     *
     * @see #pause()
     * @see #isPaused()
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public void resume() {
        mAnimator.resume();
    }

    /**
     * Returns whether this processor is currently in a paused state.
     *
     * @return True if the processor is currently paused, false otherwise.
     * @see #pause()
     * @see #resume()
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public boolean isPaused() {
        return mAnimator.isPaused();
    }

    /**
     * Will be called before {@link android.graphics.drawable.Drawable#draw(Canvas) .draw(Canvas)}.
     * Useful for some changes, based on {@link Paint Paint}
     */
    protected void processPreDraw(
            @NonNull Canvas canvas,
            @NonNull IconicsBrush<TextPaint> iconBrush,
            @NonNull IconicsBrush<Paint> iconContourBrush,
            @NonNull IconicsBrush<Paint> backgroundBrush,
            @NonNull IconicsBrush<Paint> backgroundContourBrush) {
    }

    /**
     * Will be called after {@link android.graphics.drawable.Drawable#draw(Canvas) .draw(Canvas)}.
     * Useful for some changes, based on canvas and need to restore canvas after drawing the icon
     * (scale, rotate etc.).
     */
    protected void processPostDraw(@NonNull Canvas canvas) {
    }

    /**
     * Return the drawable's current state
     * <p>
     * Nullability contract: calling this into
     * {@link #processPreDraw(Canvas, IconicsBrush, IconicsBrush, IconicsBrush, IconicsBrush)} or
     * {@link #processPostDraw(Canvas)} is guaranteed return {@code @NonNull} value, otherwise it
     * can be {@code @Nullable}.
     *
     * @return The current state of the drawable
     */
    @Nullable
    protected int[] getDrawableState() {
        return mDrawable == null ? null : mDrawable.getState();
    }

    /**
     * Return the drawable's bounds Rect. Note: for efficiency, the returned object may be the same
     * object stored in the drawable (though this is not guaranteed).
     * <p>
     * Nullability contract: calling this into
     * {@link #processPreDraw(Canvas, IconicsBrush, IconicsBrush, IconicsBrush, IconicsBrush)} or
     * {@link #processPostDraw(Canvas)} is guaranteed return {@code @NonNull} value, otherwise it
     * can be {@code @Nullable}.
     *
     * @return The bounds of the drawable (which may change later, so caller beware). DO NOT ALTER
     * the returned object as it may change the stored bounds of this drawable.
     */
    @Nullable
    protected Rect getDrawableBounds() {
        return mDrawable == null ? null : mDrawable.getBounds();
    }

    /**
     * @return completed percent of animation
     */
    @FloatRange(from = 0, to = 100)
    protected float getAnimatedPercent() {
        return (float) mAnimator.getAnimatedValue();
    }

    /**
     * Called when a drawable was attached and now {@link #getDrawableBounds()} and
     * {@link #getDrawableState()} will return valid values. Good place to set some
     * drawable-dependent fields
     */
    protected void onDrawableAttached() {
    }

    /**
     * Called when a drawable was detached and now {@link #getDrawableBounds()} and
     * {@link #getDrawableState()} will return {@code null}. Good place to reset some
     * drawable-dependent fields
     */
    protected void onDrawableDetached() {
    }

    /**
     * Internal set an drawable to this processor
     */
    void setDrawable(@Nullable IconicsAnimatedDrawable drawable) {
        mDrawable = null;
        onDrawableDetached();

        if (drawable != null) {
            mDrawable = drawable;
            onDrawableAttached();

            if (mIsStartImmediately || mIsStartRequested) {
                start();
            }
        } else {
            mAnimator.cancel();
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private void initProxyPauseListener() {
        mProxyPauseListener = new Animator.AnimatorPauseListener() {

            @Override
            public void onAnimationPause(Animator animation) {
                forEachListeners(l -> l.onAnimationPause(IconicsAnimationProcessor.this));
            }

            @Override
            public void onAnimationResume(Animator animation) {
                forEachListeners(l -> l.onAnimationResume(IconicsAnimationProcessor.this));
            }

            private void forEachListeners(Consumer<IconicsAnimationPauseListener> consumer) {
                if (mPauseListeners == null) return;
                for (IconicsAnimationPauseListener l : mPauseListeners) {
                    consumer.accept(l);
                }
            }
        };
    }
}
