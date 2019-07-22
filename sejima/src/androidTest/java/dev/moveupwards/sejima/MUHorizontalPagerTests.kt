package dev.moveupwards.sejima

import android.content.Context
import android.widget.TextView

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

@RunWith(AndroidJUnit4::class)
class MUHorizontalPagerTests {

    private var mMUHorizontalPager: MUHorizontalPager? = null

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mMUHorizontalPager = MUHorizontalPager(context)
        assertNotNull(mMUHorizontalPager)
    }

    @Test
    fun defaultValues() {
        assertEquals(mMUHorizontalPager?.horizontalMargins, 0f)
        assertEquals(mMUHorizontalPager?.currentIndex, 0)
        assertEquals(mMUHorizontalPager?.pageCount, 0)
        assertNull(mMUHorizontalPager?.slideListener)
        assertNotNull(mMUHorizontalPager?.adapter)
        assertNull(mMUHorizontalPager?.muPageControl)
    }

    @Test
    fun customValues() {
        mMUHorizontalPager?.horizontalMargins = 12f
        assertEquals(mMUHorizontalPager?.horizontalMargins, 12f)

        mMUHorizontalPager?.currentIndex = 5
        assertEquals(mMUHorizontalPager?.currentIndex, 0) // Test with 0 page

        mMUHorizontalPager?.slideListener = { _, _ -> } //{ horizontalPager, toIndex -> }
        assertNotNull(mMUHorizontalPager?.slideListener)

        mMUHorizontalPager?.setAdapter(null)
        assertNull(mMUHorizontalPager?.adapter)
    }

    @Test
    fun navigate() {
        val c = ApplicationProvider.getApplicationContext<Context>()
        mMUHorizontalPager?.addViews(arrayOf(TextView(c), TextView(c)), 14f)
        assertEquals(mMUHorizontalPager?.pageCount, 2)

        mMUHorizontalPager?.currentIndex = 1
        assertEquals(mMUHorizontalPager?.currentIndex, 1)

        mMUHorizontalPager?.setCurrentIndex(0, true)
        assertEquals(mMUHorizontalPager?.currentIndex, 0)

        mMUHorizontalPager?.currentIndex = 2
        assertEquals(mMUHorizontalPager?.currentIndex, 0) // No change if out of bounds
    }
}
