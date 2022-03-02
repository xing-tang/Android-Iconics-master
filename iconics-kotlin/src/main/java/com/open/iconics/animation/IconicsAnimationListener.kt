package com.open.iconics.animation

interface IconicsAnimationListener {

    /**
     * Notifies the start of the animation as well as the animation's overall play direction.
     * This method's default behavior is to call
     * [onAnimationStart]. This method can be overridden,
     * though not required, to get the additional play direction info when an animation starts.
     * Skipping calling super when overriding this method results in
     * [onAnimationStart] not getting called.
     *
     * @param processor The started processor.
     * @param isReverse Whether the processor is playing in reverse.
     */
    fun onAnimationStart(processor: IconicsAnimationProcessor, isReverse: Boolean) {
        onAnimationStart(processor)
    }

    /**
     * Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to [IconicsAnimationProcessor.INFINITE].
     *
     * This method's default behavior is to call [onAnimationEnd].
     * This method can be overridden, though not required, to get the additional play direction info
     * when an animation ends. Skipping calling super when overriding this method results in
     * [onAnimationEnd] not getting called.
     *
     * @param processor The processor which reached its end.
     * @param isReverse Whether the processor is playing in reverse.
     */
    fun onAnimationEnd(processor: IconicsAnimationProcessor, isReverse: Boolean) {
        onAnimationEnd(processor)
    }

    /**
     * Notifies the start of the animation.
     *
     * @param processor The started processor.
     */
    fun onAnimationStart(processor: IconicsAnimationProcessor) {}

    /**
     * Notifies the end of the animation. This callback is not invoked
     * for processors with repeat count set to [IconicsAnimationProcessor.INFINITE].
     *
     * @param processor The processor which reached its end.
     */
    fun onAnimationEnd(processor: IconicsAnimationProcessor) {}

    /**
     * Notifies the cancellation of the animation. This callback is not invoked
     * for animations with repeat count set to [IconicsAnimationProcessor.INFINITE].
     *
     * @param processor The processor which was canceled.
     */
    fun onAnimationCancel(processor: IconicsAnimationProcessor) {}

    /**
     * Notifies the repetition of the animation.
     *
     * @param processor The processor which was repeated.
     */
    fun onAnimationRepeat(processor: IconicsAnimationProcessor) {}
}