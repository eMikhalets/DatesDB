package com.emikhalets.datesdb

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emikhalets.datesdb.utils.computeDaysLeft
import com.emikhalets.datesdb.viewmodel.DateAddViewModel
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class DateAddTest {

    @Test
    @Throws(Exception::class)
    fun testComputeDaysLeft() {
        val viewModel = DateAddViewModel()
        val selected = LocalDateTime.of(1993, 12, 24, 0, 0)
        assertThat(computeDaysLeft(selected), equalTo(9904))
    }
}