package com.jesusbadenas.goldenspearchallenge.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<MutableList<T>>.addMoreItems(items: List<T>?) {
    val oldValue = value ?: mutableListOf()
    items?.let { oldValue.addAll(it) }
    value = oldValue
}
