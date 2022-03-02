package com.open.iconics.animation

import android.os.Build

import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.KITKAT)
interface IconicsAnimationPauseListener {
    /**
     * Notifies that the processor was paused.
     *
     * @param processor The processor being paused.
     * @see IconicsAnimationProcessor.pause
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun onAnimationPause(processor: IconicsAnimationProcessor) {
    }

    /**
     * Notifies that the processor was resumed, after being
     * previously paused.
     *
     * @param processor The processor being resumed.
     * @see IconicsAnimationProcessor.resume
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun onAnimationResume(processor: IconicsAnimationProcessor) {
    }
}