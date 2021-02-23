@file:JvmName("SharedPreferencesHelper")

package com.junpu.utils

import android.content.Context
import android.content.SharedPreferences
import com.junpu.utils.encrypt.AesUtils

/**
 * SharedPreferences 扩展类
 * @author junpu
 * @date 2020-01-03
 */

/**
 * 基础SharedPreferences
 */
fun getSharedPreferences(name: String): SharedPreferences =
    app.getSharedPreferences(name, Context.MODE_PRIVATE)

/**
 * 数据加密存储
 */
fun SharedPreferences.putStringEncrypt(key: String, value: String?) {
    val text = AesUtils.encrypt(value)
    edit().putString(key, text).apply()
}

/**
 * 读取数据
 */
fun SharedPreferences.getStringDecrypt(key: String): String {
    val text = getString(key, "")
    return AesUtils.decrypt(text) ?: ""
}