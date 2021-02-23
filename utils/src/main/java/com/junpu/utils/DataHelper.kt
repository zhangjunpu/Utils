@file:JvmName("DataHelper")

package com.junpu.utils

import org.json.JSONArray
import org.json.JSONObject

/**
 * 数据结构扩展类
 * @author junpu
 * @date 2019-06-29
 */

fun CharSequence?.isNotNullOrBlank(): Boolean = !isNullOrBlank()

fun CharSequence?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun <T> List<T>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun <K, V> HashMap<K, V>.append(key: K, value: V) = apply {
    put(key, value)
}

fun <K, V> MutableMap<K, V>.append(key: K, value: V) = apply {
    put(key, value)
}

fun <K, V> HashMap<K, V>.appendAll(map: Map<K, V>) = apply {
    putAll(map)
}

fun <K, V> HashMap<K, V>.appendNotNull(key: K, value: V?) = apply {
    value?.let { put(key, value) }
}

fun JSONArray?.isNullOrEmpty(): Boolean = this == null || length() <= 0
fun JSONArray?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun JSONArray.forEach(each: (JSONObject) -> Unit) {
    for (i in 0 until length()) {
        each(getJSONObject(i))
    }
}

fun <R, C : MutableCollection<in R>> JSONArray.mapTo(
    destination: C,
    transform: (JSONObject) -> R
): C {
    forEach { destination.add(transform(it)) }
    return destination
}

fun <R> JSONArray.map(transform: (JSONObject) -> R) = mapTo(arrayListOf(), transform)
