package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.RelativeLayout

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MUHeaderTests {

    private var mMUHeader: MUHeader? = null

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mMUHeader = MUHeader(context)
        assertNotNull(mMUHeader)
    }

    @Test
    fun defaultValues() {
        // Title
        assertEquals(mMUHeader?.title, "")
        // Title's color
        assertEquals(mMUHeader?.titleColor, Color.BLACK)
        // Title's size
        assertEquals(mMUHeader?.titleSize, 24f)
        // Title's font
        assertEquals(mMUHeader?.titleWeight, Typeface.NORMAL)
        // Detail
        assertEquals(mMUHeader?.detail, "")
        // Detail's color
        assertEquals(mMUHeader?.detailColor, Color.BLACK)
        // Detail's size
        assertEquals(mMUHeader?.detailSize, 14f)
        // Detail's font
        assertEquals(mMUHeader?.detailWeight, Typeface.BOLD)
        // Horizontal alignment
        assertEquals(mMUHeader?.alignment, Gravity.START)
        // Vertical spacing
        assertEquals(mMUHeader?.verticalSpacing, 8)
        // Font style
        assertEquals(mMUHeader?.titleFontStyle, -1)
        assertEquals(mMUHeader?.detailFontStyle, -1)
    }

    @Test
    fun customValues() {
        // Title
        mMUHeader?.title = "Custom Title"
        assertEquals(mMUHeader?.title, "Custom Title")
        // Title's color
        mMUHeader?.titleColor = Color.BLUE
        assertEquals(mMUHeader?.titleColor, Color.BLUE)
        // Title's size
        mMUHeader?.titleSize = 58f
        assertEquals(mMUHeader?.titleSize, 58f)
        // Title's font
        mMUHeader?.titleWeight = Typeface.BOLD
        assertEquals(mMUHeader?.titleWeight, Typeface.BOLD)
        // Detail
        mMUHeader?.detail = "Custom Detail"
        assertEquals(mMUHeader?.detail, "Custom Detail")
        // Detail's color
        mMUHeader?.detailColor = Color.RED
        assertEquals(mMUHeader?.detailColor, Color.RED)
        // Detail's size
        mMUHeader?.detailSize = 18f
        assertEquals(mMUHeader?.detailSize, 18f)
        // Detail's font
        mMUHeader?.detailWeight = Typeface.ITALIC
        assertEquals(mMUHeader?.detailWeight, Typeface.ITALIC)
        // Horizontal alignment
        mMUHeader?.alignment = Gravity.END
        assertEquals(mMUHeader?.alignment, Gravity.END)
        // Vertical spacing
        mMUHeader?.verticalSpacing = 55
        assertEquals(mMUHeader?.verticalSpacing, 55)
        // Font style
        mMUHeader?.titleFontStyle = 1
        mMUHeader?.detailFontStyle = 2
        assertEquals(mMUHeader?.titleFontStyle, -1)
        assertEquals(mMUHeader?.detailFontStyle, -1)
    }

    @Test
    fun impossibleAlignment() {
        mMUHeader?.alignment = RelativeLayout.CENTER_IN_PARENT
        assertEquals(mMUHeader?.alignment, Gravity.START)
    }
}
