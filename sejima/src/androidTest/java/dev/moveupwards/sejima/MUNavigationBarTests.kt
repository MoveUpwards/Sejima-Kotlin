package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

@RunWith(AndroidJUnit4::class)
class MUNavigationBarTests {

    private var mMUNavigationBar: MUNavigationBar? = null
    private var mContext: Context? = null
    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        mContext?.setTheme(R.style.AppTheme)
        mMUNavigationBar = MUNavigationBar(mContext!!)
        assertNotNull(mMUNavigationBar)
    }

    @Test
    fun defaultValues() {

        // Alpha
        assertEquals(0.7f, mMUNavigationBar?.disabledAlpha)
        // Label
        assertEquals("", mMUNavigationBar?.label)
        assertEquals(12f, mMUNavigationBar?.labelFontSize)
        assertEquals(Typeface.NORMAL, mMUNavigationBar?.labelFontWeight)
        assertEquals(Color.BLACK, mMUNavigationBar?.labelColor)
        assertEquals(mMUNavigationBar?.labelAlignment, Gravity.CENTER)
        assertEquals(Color.BLACK, mMUNavigationBar?.labelHighLightedColor)
        assertEquals(Color.BLACK, mMUNavigationBar?.labelProgressingColor)
        // Is loading
        assertEquals(false, mMUNavigationBar?.isLoading)
        // Bkg color
        assertEquals(Color.LTGRAY, mMUNavigationBar?.bkgColor)
        // Border color
        assertEquals(Color.LTGRAY, mMUNavigationBar?.borderColor)
        assertEquals(0f, mMUNavigationBar?.borderWidth)
        assertEquals(0, mMUNavigationBar?.cornerRadius)
        // Padding
        assertEquals(convertDPToPixel(8f), mMUNavigationBar?.horizontalPadding)
        assertEquals(convertDPToPixel(8f), mMUNavigationBar?.verticalPadding)
        // Separator
        assertEquals(Color.BLACK, mMUNavigationBar?.separatorColor)
        assertEquals(0f, mMUNavigationBar?.separatorWidth)
        assertEquals(1f, mMUNavigationBar?.separatorMultiplier)
        // Listener
        assertNull(mMUNavigationBar?.listener)
        // Style
        assertEquals(-1, mMUNavigationBar?.fontStyle)
        // Drawable
        //assertNotNull(mMUNavigationBar?.imgDrawable) // TODO: Can't have image on Sejima or at least no default one
    }

    @Test
    fun customValues() {

        // Alpha
        mMUNavigationBar?.disabledAlpha = -4f
        assertEquals(0f, mMUNavigationBar?.disabledAlpha)
        // Label
        mMUNavigationBar?.label = "c"
        assertEquals("c", mMUNavigationBar?.label)
        mMUNavigationBar?.labelFontSize = 12f
        assertEquals(12f, mMUNavigationBar?.labelFontSize)
        mMUNavigationBar?.labelFontWeight = Typeface.BOLD
        assertEquals(Typeface.BOLD, mMUNavigationBar?.labelFontWeight)
        mMUNavigationBar?.labelColor = Color.BLUE
        assertEquals(Color.BLUE, mMUNavigationBar?.labelColor)
        mMUNavigationBar?.labelAlignment = Gravity.START
        assertEquals(mMUNavigationBar?.labelAlignment, (Gravity.START or Gravity.CENTER_VERTICAL))
        mMUNavigationBar?.labelHighLightedColor = Color.RED
        assertEquals(Color.RED, mMUNavigationBar?.labelHighLightedColor)
        mMUNavigationBar?.labelProgressingColor = Color.BLACK
        assertEquals(Color.BLACK, mMUNavigationBar?.labelProgressingColor)
        // Is loading
        mMUNavigationBar?.isLoading = true
        assertEquals(true, mMUNavigationBar?.isLoading)
        // Bkg color
        mMUNavigationBar?.bkgColor = Color.CYAN
        assertEquals(Color.CYAN, mMUNavigationBar?.bkgColor)
        // Border color
        mMUNavigationBar?.borderColor = Color.RED
        assertEquals(Color.RED, mMUNavigationBar?.borderColor)
        mMUNavigationBar?.borderWidth = 15f
        assertEquals(15f, mMUNavigationBar?.borderWidth)
        mMUNavigationBar?.cornerRadius = 17
        assertEquals(17, mMUNavigationBar?.cornerRadius)
        // Padding
        mMUNavigationBar?.horizontalPadding = 17f
        assertEquals(17f, mMUNavigationBar?.horizontalPadding)
        mMUNavigationBar?.verticalPadding = 18f
        assertEquals(18f, mMUNavigationBar?.verticalPadding)
        // Separator
        mMUNavigationBar?.separatorColor = Color.YELLOW
        assertEquals(Color.YELLOW, mMUNavigationBar?.separatorColor)
        mMUNavigationBar?.separatorWidth = 25f
        assertEquals(25f, mMUNavigationBar?.separatorWidth)
        mMUNavigationBar?.separatorMultiplier = 3f
        assertEquals(1f, mMUNavigationBar?.separatorMultiplier)
        // Listener
        mMUNavigationBar?.setMUNavigationBarListener(object : MUNavigationBar.MUNavigationBarListener {
            override fun clickOnLeftButton(muNavigationBar: MUNavigationBar) {

            }

            override fun clickOnRightButton(muNavigationBar: MUNavigationBar) {

            }
        })
        assertNotNull(mMUNavigationBar?.listener)
    }

    private fun convertDPToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            mContext!!.resources.displayMetrics)
    }
}
