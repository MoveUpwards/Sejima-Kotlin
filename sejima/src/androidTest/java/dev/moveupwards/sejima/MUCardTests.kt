package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MUCardTests {

    private var mMUCard: MUCard? = null
    private var mContext: Context? = null

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.AppTheme)
        mContext = context
        mMUCard = MUCard(context)
        assertNotNull(mMUCard)
    }

    @Test
    fun defaultValues() {
        assertEquals(Color.GRAY, mMUCard?.bkgColor)
        assertEquals(convertDPToPixel(24f), mMUCard?.topPadding)
        assertEquals(convertDPToPixel(24f), mMUCard?.contentLeftPadding)
        assertEquals(convertDPToPixel(24f), mMUCard?.contentRightPadding)
        assertEquals(convertDPToPixel(24f), mMUCard?.contentTopPadding)
        assertEquals(convertDPToPixel(24f), mMUCard?.contentBottomPadding)
        assertEquals(convertDPToPixel(4f), mMUCard?.borderWidth)
        assertEquals(Color.TRANSPARENT, mMUCard?.borderColor)
        assertEquals(-1, mMUCard?.styleResId)
        assertEquals(5f, mMUCard?.cornerRadius)
        assertEquals(convertDPToPixel(24f), mMUCard?.headerHorizontalPadding)

        assertEquals("", mMUCard?.title)
        assertEquals(Color.BLACK, mMUCard?.titleFontColor)
        assertEquals(-1, mMUCard?.titleFontStyle)

        assertEquals("", mMUCard?.detail)
        assertEquals(Color.BLACK, mMUCard?.detailFontColor)
        assertEquals(-1, mMUCard?.detailFontStyle)

        assertEquals(0, mMUCard?.contentView?.childCount)
    }

    @Test
    fun customValues() {
        mMUCard?.bkgColor = Color.RED
        assertEquals(Color.RED, mMUCard?.bkgColor)
        mMUCard?.topPadding = 2f
        assertEquals(2f, mMUCard?.topPadding)
        mMUCard?.contentLeftPadding = 3f
        assertEquals(3f, mMUCard?.contentLeftPadding)
        mMUCard?.contentRightPadding = 4f
        assertEquals(4f, mMUCard?.contentRightPadding)
        mMUCard?.contentTopPadding = 5f
        assertEquals(5f, mMUCard?.contentTopPadding)
        mMUCard?.contentBottomPadding = 6f
        assertEquals(6f, mMUCard?.contentBottomPadding)
        mMUCard?.borderWidth = 14f
        assertEquals(14f, mMUCard?.borderWidth)
        mMUCard?.borderColor = Color.YELLOW
        assertEquals(Color.YELLOW, mMUCard?.borderColor)
        mMUCard?.styleResId = 18
        assertEquals(-1, mMUCard?.styleResId)
        mMUCard?.cornerRadius = 27f
        assertEquals(27f, mMUCard?.cornerRadius)
        mMUCard?.headerHorizontalPadding = 19f
        assertEquals(19f, mMUCard?.headerHorizontalPadding)

        mMUCard?.title = "Title"
        assertEquals("Title", mMUCard?.title)
        mMUCard?.titleFontColor = Color.RED
        assertEquals(Color.RED, mMUCard?.titleFontColor)
        mMUCard?.titleFontStyle = 2
        assertEquals(-1, mMUCard?.titleFontStyle)

        mMUCard?.detail = "My long description"
        assertEquals("My long description", mMUCard?.detail)
        mMUCard?.detailFontColor = Color.YELLOW
        assertEquals(Color.YELLOW, mMUCard?.detailFontColor)
        mMUCard?.detailFontStyle = 0
        assertEquals(-1, mMUCard?.detailFontStyle)

        val myView = View(mContext)
        mMUCard?.addContentView(myView)
        assertEquals(1, mMUCard?.contentView?.childCount)
        assertEquals(myView, mMUCard?.contentView?.getChildAt(0))
    }

    private fun convertDPToPixel(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            mContext!!.resources.displayMetrics)
    }
}
