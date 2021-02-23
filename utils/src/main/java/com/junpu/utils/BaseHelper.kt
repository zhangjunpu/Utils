@file:JvmName("BaseHelper")

package com.junpu.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 基础扩展类
 * @author junpu
 * @time 2017/11/7 23:09
 */

fun Context.launch(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) {
    val intent = Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    }
    startActivity(intent)
}

fun Fragment.launch(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) {
    context?.launch(cls, bundle, *flags)
}

fun Activity.launchForResult(
    cls: Class<*>,
    requestCode: Int,
    args: Bundle? = null,
    vararg flags: Int
) {
    val intent = Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.launchForResult(
    cls: Class<*>,
    requestCode: Int,
    args: Bundle? = null,
    vararg flags: Int
) {
    val intent = Intent(context, cls).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.isPortrait(): Boolean =
    activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    LayoutInflater.from(this).inflate(resource, root, attachToRoot)

fun Fragment.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    LayoutInflater.from(context).inflate(resource, root, attachToRoot)

fun View.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    LayoutInflater.from(context).inflate(resource, root, attachToRoot)

fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resource, this, attachToRoot)

inline fun Fragment.runOnUiThread(crossinline f: () -> Unit) {
    requireActivity().runOnUiThread { f() }
}

/**
 * 获取Intent实例，此Intent清掉目标之上的Activity，并走目标的onNewIntent()方法
 */
fun Context.getNewIntent(cls: Class<*>) = Intent(this, cls).newIntent

val Intent.newIntent: Intent
    get() = apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 清掉目标Activity和其之上的Activity，并重新创建目标Activity
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) // 不会清掉目标Activity，而是走onNewIntent()方法
    }

fun postDelay(delayMillis: Long = 100, looper: Looper = Looper.getMainLooper(), r: () -> Unit) =
    Handler(looper).postDelayed(r, delayMillis)

/**
 * 重试
 */
fun postRetry(retryTimes: Int, delayMillis: Long = 100, retry: (retryTimes: Int) -> Unit) {
    var retryIndex = retryTimes
    retryIndex--
    if (retryIndex > 0) postDelay(delayMillis) { retry(retryIndex) }
}
