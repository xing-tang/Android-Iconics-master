package com.open.iconics.context

import android.util.Log.ERROR
import com.open.iconics.Iconics
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * https://github.com/chrisjenx/Calligraphy
 */
internal object ReflectionUtils {

    fun getField(clazz: Class<*>, fieldName: String): Field? {
        return runCatching { clazz.getDeclaredField(fieldName) }
                .getOrNull()?.also { it.isAccessible = true }
    }

    fun getValue(field: Field, obj: Any): Any? {
        return runCatching { field.get(obj) }.getOrNull()
    }

    fun setValue(field: Field, obj: Any, value: Any) {
        runCatching { field.set(obj, value) }
    }

    fun getMethod(clazz: Class<*>, methodName: String): Method? {
        return clazz.methods.firstOrNull { it.name == methodName }?.also { it.isAccessible = true }
    }

    fun invokeMethod(obj: Any, method: Method?, vararg args: Any) {
        try {
            method?.invoke(obj, *args)
        } catch (e: IllegalAccessException) {
            Iconics.logger.log(ERROR, Iconics.TAG, "Can't invoke method using reflection", e)
        } catch (e: InvocationTargetException) {
            Iconics.logger.log(ERROR, Iconics.TAG, "Can't invoke method using reflection", e)
        }
    }

    /** A helper method to instantiate class by name */
    inline fun <reified T : Any> getInstanceForName(name: String): T {
        return getInstanceOf(Class.forName(name)) as T
    }

    /**
     * A helper method to instantiate a class or Kotlin object. This could be easier by using
     * kotlin-reflection, but is done with JVM reflection to avoid the large dependency.
     */
    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    inline fun <T : Any> getInstanceOf(cls: Class<T>): T {
        val instanceField = runCatching { cls.getField("INSTANCE") }.getOrNull()

        return if (
            instanceField != null &&
            Modifier.isFinal(instanceField.modifiers) &&
            Modifier.isStatic(instanceField.modifiers)
        ) {
            // This is a Kotlin object.
            instanceField.get(null) as T
        } else {
            // This is a regular class.
            cls.newInstance()
        }
    }
}