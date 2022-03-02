package com.open.iconics.animation;

public interface IconicsAnimationListener {

    /**
     * Notifies the start of the animation as well as the animation's overall play direction.
     * This method's default behavior is to call
     * {@link #onAnimationStart(IconicsAnimationProcessor)}. This method can be overridden,
     * though not required, to get the additional play direction info when an animation starts.
     * Skipping calling super when overriding this method results in
     * {@link #onAnimationStart(IconicsAnimationProcessor)} not getting called.
     *
     * @param processor The started processor.
     * @param isReverse Whether the processor is playing in reverse.
     */
    default void onAnimationStart(IconicsAnimationProcessor processor, boolean isReverse) {
        onAnimationStart(processor);
    }

    /**
     * Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     * <p>
     * This method's default behavior is to call {@link #onAnimationEnd(IconicsAnimationProcessor)}.
     * This method can be overridden, though not required, to get the additional play direction info
     * when an animation ends. Skipping calling super when overriding this method results in
     * {@link #onAnimationEnd(IconicsAnimationProcessor)} not getting called.
     *
     * @param processor The processor which reached its end.
     * @param isReverse Whether the processor is playing in reverse.
     */
    default void onAnimationEnd(IconicsAnimationProcessor processor, boolean isReverse) {
        onAnimationEnd(processor);
    }

    /**
     * Notifies the start of the animation.
     *
     * @param processor The started processor.
     */
    default void onAnimationStart(IconicsAnimationProcessor processor) {
    }

    /**
     * Notifies the end of the animation. This callback is not invoked
     * for processors with repeat count set to INFINITE.
     *
     * @param processor The processor which reached its end.
     */
    default void onAnimationEnd(IconicsAnimationProcessor processor) {
    }

    /**
     * Notifies the cancellation of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     *
     * @param processor The processor which was canceled.
     */
    default void onAnimationCancel(IconicsAnimationProcessor processor) {
    }

    /**
     * Notifies the repetition of the animation.
     *
     * @param processor The processor which was repeated.
     */
    default void onAnimationRepeat(IconicsAnimationProcessor processor) {
    }
}