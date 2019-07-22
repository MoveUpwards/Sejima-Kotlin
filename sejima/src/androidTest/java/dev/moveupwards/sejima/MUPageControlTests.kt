package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.util.TypedValue

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MUPageControlTests {

    private var mMUPageControl: MUPageControl? = null
    private var mContext: Context? = null
    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        mContext?.setTheme(R.style.AppTheme)
        mMUPageControl = MUPageControl(mContext!!)
        assertNotNull(mMUPageControl)
    }

    @Test
    fun defaultValues() {
        assertEquals(0, mMUPageControl?.numberPages)
        assertEquals(null, mMUPageControl?.listener)
        assertEquals(convertDPToPixel(10f).toInt(), mMUPageControl?.elementWidth)
        assertEquals(Color.LTGRAY, mMUPageControl?.elementColor)
        assertEquals(convertDPToPixel(10f).toInt(), mMUPageControl?.activeElementWidth)
        assertEquals(Color.BLACK, mMUPageControl?.activeElementColor)
        assertEquals(-1, mMUPageControl?.currentPosition)
        assertEquals(0, mMUPageControl?.activeElementRadius)
        assertEquals(Color.TRANSPARENT, mMUPageControl?.borderColor)
        assertEquals(0, mMUPageControl?.borderWidth)
        assertEquals(6, mMUPageControl?.elementPadding)
        assertEquals(false, mMUPageControl?.isHideForSingleElementValue)
    }

    @Test
    fun customValues() {
        mMUPageControl?.numberPages = 5 // TODO: Should not be possible
        assertEquals(5, mMUPageControl?.numberPages)

        mMUPageControl?.listener = { muPageControl, i -> }
        assertNotNull(mMUPageControl?.listener)

        //mMUPageControl?.elementWidth = 6 // TODO: Looks like 10f is hard coded :(
        //assertEquals(convertDPToPixel(6f).toInt(), mMUPageControl?.elementWidth)

        mMUPageControl?.elementColor = Color.BLACK
        assertEquals(Color.BLACK, mMUPageControl?.elementColor)

        //mMUPageControl?.activeElementWidth = 12 // TODO: Looks like 10f is hard coded :(
        //assertEquals(convertDPToPixel(12f).toInt(), mMUPageControl?.activeElementWidth)

        mMUPageControl?.activeElementColor = Color.YELLOW
        assertEquals(Color.YELLOW, mMUPageControl?.activeElementColor)

        mMUPageControl?.currentPosition = 2
        assertEquals(2, mMUPageControl?.currentPosition)

        mMUPageControl?.activeElementRadius = 3
        assertEquals(3, mMUPageControl?.activeElementRadius)

        mMUPageControl?.borderColor = Color.WHITE
        assertEquals(Color.WHITE, mMUPageControl?.borderColor)

        mMUPageControl?.borderWidth = 1
        assertEquals(1, mMUPageControl?.borderWidth)

        //mMUPageControl?.elementPadding = 4 // TODO: Wrong use of autolayout
        //assertEquals(4, mMUPageControl?.elementPadding)

        mMUPageControl?.isHideForSingleElementValue = true
        assertEquals(true, mMUPageControl?.isHideForSingleElementValue)
    }

    private fun convertDPToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            mContext!!.resources.displayMetrics)
    }

}
