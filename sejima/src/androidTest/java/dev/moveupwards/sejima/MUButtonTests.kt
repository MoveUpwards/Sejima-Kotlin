package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MUButtonTests {

    private var mMUButton: MUButton? = null
    private var mContext: Context? = null

    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        mContext!!.setTheme(R.style.AppTheme)
        mMUButton = MUButton(mContext!!)
        assertNotNull(mMUButton)
    }

    @Test
    fun defaultValues() {

        // Alpha
        assertEquals(1f, mMUButton?.alpha)
        assertEquals(0.7f, mMUButton?.disabledAlpha)
        assertEquals(1f, mMUButton?.borderAlpha)
        // Label
        assertEquals("", mMUButton?.label)
        assertEquals(12f, mMUButton?.labelFontSize)
        assertEquals(Typeface.NORMAL, mMUButton?.labelFontWeight)
        assertEquals(Color.BLACK, mMUButton?.labelColor)
        assertEquals(Gravity.CENTER, mMUButton?.labelAlignment)
        assertEquals(Color.BLACK, mMUButton?.labelHighLightedColor)
        assertEquals(Color.BLACK, mMUButton?.progressingColor)
        // Is loading
        assertEquals(false, mMUButton?.isLoading)
        // Bkg color
        assertEquals(Color.LTGRAY, mMUButton?.bkgColor)
        // Border color
        assertEquals(Color.LTGRAY, mMUButton?.borderColor)
        assertEquals(0f, mMUButton?.borderWidth)
        assertEquals(0f, mMUButton?.cornerRadius?.toFloat())
        // Padding
        assertEquals(convertDPToPixel(8f), mMUButton?.horizontalPadding?.toFloat())
        assertEquals(convertDPToPixel(8f), mMUButton?.verticalPadding?.toFloat())
        // Listener
        assertEquals(null, mMUButton?.listener)
        // Style
        assertEquals(-1, mMUButton?.fontStyle)
    }

    @Test
    fun customValues() {
        // Alpha
        mMUButton?.alpha = 2f
        assertEquals(1f, mMUButton?.alpha)
        mMUButton?.disabledAlpha = -4f
        assertEquals(0f, mMUButton?.disabledAlpha)
        mMUButton?.borderAlpha = 0.5f
        assertEquals(0.5f, mMUButton?.borderAlpha)
        // Label
        mMUButton?.label = "c"
        assertEquals("c", mMUButton?.label)
        mMUButton?.labelFontSize = 12f
        assertEquals(12f, mMUButton?.labelFontSize)
        mMUButton?.labelFontWeight = Typeface.BOLD
        assertEquals(Typeface.BOLD, mMUButton?.labelFontWeight)
        mMUButton?.labelColor = Color.BLUE
        assertEquals(Color.BLUE, mMUButton?.labelColor)
        mMUButton?.labelAlignment = Gravity.START
        assertEquals(Gravity.START or Gravity.CENTER_VERTICAL, mMUButton?.labelAlignment)
        mMUButton?.labelHighLightedColor = Color.RED
        assertEquals(Color.RED, mMUButton?.labelHighLightedColor)
        mMUButton?.progressingColor = Color.BLACK
        assertEquals(Color.BLACK, mMUButton?.progressingColor)
        // Is loading
        mMUButton?.isLoading = true
        assertEquals(true, mMUButton?.isLoading)
        assertEquals(Color.TRANSPARENT, mMUButton?.button?.currentTextColor)
        // Bkg color
        mMUButton?.bkgColor = Color.CYAN
        assertEquals(Color.CYAN, mMUButton?.bkgColor)
        // Border color
        mMUButton?.borderColor = Color.CYAN
        assertEquals(Color.CYAN, mMUButton?.borderColor)
        mMUButton?.borderWidth = 15f
        assertEquals(15f, mMUButton?.borderWidth)
        mMUButton?.cornerRadius = 17
        assertEquals(17f, mMUButton?.cornerRadius?.toFloat())
        // Padding
        mMUButton?.horizontalPadding = 17
        assertEquals(17f, mMUButton?.horizontalPadding?.toFloat())
        mMUButton?.verticalPadding = 18
        assertEquals(18f, mMUButton?.verticalPadding?.toFloat())
        // Listener
        mMUButton?.listener = View.OnClickListener { }
        assertNotNull(mMUButton?.listener)
        // Style
        mMUButton?.fontStyle = -4 // Invalid
        assertEquals(-1, mMUButton?.fontStyle)
        mMUButton?.fontStyle = 0 // Valid
        assertEquals(-1, mMUButton?.fontStyle) // TODO: Can set a font style
    }

    private fun convertDPToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            mContext!!.resources.displayMetrics)
    }
}
