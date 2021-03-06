package com.open.iconics.animation

import android.content.res.Resources
import android.graphics.Canvas
import android.view.View
import androidx.core.view.ViewCompat
import com.open.iconics.IconicsDrawable
import com.open.iconics.typeface.IIcon
import com.open.iconics.typeface.ITypeface
import java.lang.ref.WeakReference
import java.util.ArrayList

open class IconicsAnimatedDrawable : IconicsDrawable {
    private val processors = ArrayList<IconicsAnimationProcessor>()

    constructor(res: Resources, theme: Resources.Theme? = null) : super(res, theme)

    constructor(res: Resources, theme: Resources.Theme? = null, icon: Char) : super(res, theme, icon)

    constructor(res: Resources, theme: Resources.Theme? = null, icon: String) : super(res, theme, icon)

    constructor(res: Resources, theme: Resources.Theme? = null, icon: IIcon) : super(res, theme, icon)

    protected constructor(res: Resources, theme: Resources.Theme? = null, typeface: ITypeface, icon: IIcon) : super(res, theme, typeface, icon)

    // FI-LO
    override fun draw(canvas: Canvas) {
        processors.forEach {
            it.processPreDraw(
                canvas,
                iconBrush,
                contourBrush,
                backgroundBrush,
                backgroundContourBrush
            )
        }

        super.draw(canvas)

        processors.reversed().forEach { it.processPostDraw(canvas) }
    }

    /** Attach an [processor][IconicsAnimationProcessor] to this drawable */
    fun processor(processor: IconicsAnimationProcessor): IconicsAnimatedDrawable {
        processor.setDrawable(this)
        processors.add(processor)
        return this
    }

    /** Attach an [processors][IconicsAnimationProcessor] to this drawable */
    fun processors(vararg processors: IconicsAnimationProcessor): IconicsAnimatedDrawable {
        if (processors.isEmpty()) return this
        processors.forEach { processor(it) }
        return this
    }

    /**
     * @return The runner which used for animations. Animations can be easily removed by calling
     * [Runner.unset]
     */
    fun animateIn(view: View): Runner {
        return Runner().also { it.setFor(view, this) }
    }

    class Runner internal constructor() {
        private var isAttached = false
        private var view: WeakReference<View>? = null
        private var drawable: IconicsAnimatedDrawable? = null

        private val listener = object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                isAttached = true
                ViewCompat.postOnAnimation(v, object : Runnable {
                    override fun run() {
                        if (isAttached && view?.get() != null) {
                            drawable?.let {
                                v.invalidateDrawable(it)
                                ViewCompat.postOnAnimation(v, this)
                            }
                        }
                    }
                })
            }

            override fun onViewDetachedFromWindow(v: View) {
                isAttached = false
            }
        }

        /** Setup all animations to provided drawable and view */
        fun setFor(view: View, drawable: IconicsAnimatedDrawable) {
            unset()
            this.view = WeakReference(view)
            this.drawable = drawable
            if (ViewCompat.isAttachedToWindow(view)) {
                listener.onViewAttachedToWindow(view)
            }
            view.addOnAttachStateChangeListener(listener)
        }

        /** Clear all animations from previously provided drawable and view */
        fun unset() {
            drawable = null
            view?.let {
                it.get()?.removeOnAttachStateChangeListener(listener)
                it.clear()
            }
            view = null
            isAttached = false
        }
    }
}
