package com.jesusbadenas.goldenspearchallenge.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jesusbadenas.goldenspearchallenge.test.getOrAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ExtensionsTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val intList = listOf(1, 2, 3)

    @Test
    fun testLiveDataAddMoreItemsSuccess() {
        val liveDataList = MutableLiveData<MutableList<Int>>()
        liveDataList.addMoreItems(intList)

        val result = liveDataList.getOrAwaitValue()

        Assert.assertEquals(intList, result)
    }
}
