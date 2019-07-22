package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.TypedValue

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MUPinCodeTests {

    private var mMUPinCode: MUPinCode? = null
    private var mContext: Context? = null

    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        mMUPinCode = MUPinCode(mContext!!)
        assertNotNull(mMUPinCode)
    }

    @Test
    fun defaultValues() {

        val converted = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, mContext?.resources?.displayMetrics)
        mMUPinCode?.count = 4
        assertEquals(4, mMUPinCode?.count)
        assertEquals(InputType.TYPE_CLASS_TEXT, mMUPinCode?.keyboardType)
        assertEquals("•", mMUPinCode?.defaultChar)
        assertEquals(-1, mMUPinCode?.fontStyle)
        assertEquals(converted, mMUPinCode?.cellSpacing)
        assertEquals(Color.WHITE, mMUPinCode?.cellColor)
        assertEquals(0f, mMUPinCode?.cellCornerRadius)
    }

    @Test
    fun customValues() {

        mMUPinCode?.count = 12
        assertEquals(12, mMUPinCode?.count)
        mMUPinCode?.keyboardType = InputType.TYPE_CLASS_DATETIME
        assertEquals(InputType.TYPE_CLASS_DATETIME, mMUPinCode?.keyboardType)
        mMUPinCode?.defaultChar = "55"
        assertEquals("5", mMUPinCode?.defaultChar)
        mMUPinCode?.fontStyle = -4
        assertEquals(-1, mMUPinCode?.fontStyle)
        mMUPinCode?.cellSpacing = 15f
        assertEquals(15f, mMUPinCode?.cellSpacing)
        mMUPinCode?.cellColor = Color.BLACK
        assertEquals(Color.BLACK, mMUPinCode?.cellColor)
        mMUPinCode?.cellCornerRadius = 18f
        assertEquals(18f, mMUPinCode?.cellCornerRadius)
    }

    @Test
    fun setUI() {
        mMUPinCode?.setFontColor(Color.RED)
        mMUPinCode?.setFontSize(12f)
        mMUPinCode?.updateListener = { _, _ ->
            //
        }
    }

    @Test
    fun setCode() {
        mMUPinCode?.count = 6
        assertEquals(6, mMUPinCode?.count)
        mMUPinCode?.code = "12345"
        assertEquals("12345", mMUPinCode?.code)
    }

    @Test
    fun tooLongCode() {
        mMUPinCode?.count = 3
        assertEquals(3, mMUPinCode?.count)
        mMUPinCode?.code = "12345"
        assertEquals("123", mMUPinCode?.code)
    }
}
