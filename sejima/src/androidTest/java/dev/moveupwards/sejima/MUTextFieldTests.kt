package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.InputType
import android.util.DisplayMetrics
import android.widget.RelativeLayout
import android.widget.TextView

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.appcompat.widget.AppCompatEditText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

@RunWith(AndroidJUnit4::class)
class MUTextFieldTests {

    private var mMUTextField: MUTextField? = null
    private var mScale: Float = 0f
    private var defaultTextViewSize: Float = 0f
    private var defaultEditTextSize: Float = 0f
    private var defaultEditTextFontColor: Int = 0
    private var defaultEditTextHintColor: Int = 0

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        mMUTextField = MUTextField(context)
        assertNotNull(mMUTextField)
        mScale = context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

        defaultTextViewSize = TextView(context).textSize
        val et = AppCompatEditText(context)
        defaultEditTextSize = et.textSize
        defaultEditTextFontColor = et.currentTextColor
        defaultEditTextHintColor = et.currentHintTextColor
    }

    @Test
    fun defaultValues() {

        // Label
        assertEquals("", mMUTextField?.label)
        // Label's size
        assertEquals(defaultTextViewSize, mMUTextField?.labelFontSize)
        // Label's font weight
        assertEquals(Typeface.NORMAL, mMUTextField?.labelFontWeight)
        // Label's color
        assertEquals(Color.WHITE, mMUTextField?.labelColor)

        // Field
        assertEquals("", mMUTextField?.field)
        // Field's size
        assertEquals(defaultEditTextSize, mMUTextField?.fieldFontSize)
        // Field's font weight
        assertEquals(Typeface.NORMAL, mMUTextField?.fieldFontWeight)
        // Field's color
        assertEquals(defaultEditTextFontColor, mMUTextField?.fieldColor)

        // Placeholder
        assertEquals("", mMUTextField?.placeHolderText)
        // Placeholder color
        assertEquals(defaultEditTextHintColor, mMUTextField?.placeHolderFontColor)
        // Underline
        assertEquals(Color.TRANSPARENT, mMUTextField?.underlineColor)

        // Field's comportment
        assertEquals(false, mMUTextField?.isSecure)
        assertEquals(true, mMUTextField?.isEditable)
        assertEquals(false, mMUTextField?.isActive)
        assertEquals(true, mMUTextField?.isAutoCorrection)
        assertEquals(true, mMUTextField?.isReturnKeyAvailable)

        assertEquals(InputType.TYPE_NULL, mMUTextField?.keyboardType)
        assertNull(mMUTextField?.tfListener)

        //assertEquals(mMUTextField?.alignment, RelativeLayout.ALIGN_PARENT_START)

        assertEquals(-1, mMUTextField?.labelFontStyle)
        assertEquals(-1, mMUTextField?.fieldFontStyle)
    }

    @Test
    fun customValues() {

        // Label
        mMUTextField?.label = "Custom"
        assertEquals(mMUTextField?.label, "Custom")
        // Label's size
        mMUTextField?.labelFontSize = 124f
        assertEquals(mMUTextField?.labelFontSize, 124 * mScale)
        // Label's font weight
        mMUTextField?.labelFontWeight = Typeface.BOLD
        assertEquals(mMUTextField?.labelFontWeight, Typeface.BOLD)
        // Label's color
        mMUTextField?.labelColor = Color.RED
        assertEquals(mMUTextField?.labelColor, Color.RED)

        // Field
        mMUTextField?.field = "CUSTOM FIELD"
        assertEquals(mMUTextField?.field, "CUSTOM FIELD")
        // Field's size
        mMUTextField?.fieldFontSize = 35f
        assertEquals(mMUTextField?.fieldFontSize, 35 * mScale)
        // Field's font weight
        mMUTextField?.fieldFontWeight = Typeface.BOLD_ITALIC
        assertEquals(mMUTextField?.fieldFontWeight, Typeface.BOLD_ITALIC)
        // Field's color
        mMUTextField?.fieldColor = Color.BLUE
        assertEquals(mMUTextField?.fieldColor, Color.BLUE)

        // Placeholder
        mMUTextField?.placeHolderText = "Custom placeholder"
        assertEquals(mMUTextField?.placeHolderText, "Custom placeholder")
        // Placeholder color
        mMUTextField?.placeHolderFontColor = Color.TRANSPARENT
        assertEquals(mMUTextField?.placeHolderFontColor, Color.TRANSPARENT)
        // Underline
        // Commented to avoid heavy mocks
        //        mMUTextField.setUnderlineColor(Color.RED);
        //        assertEquals(mMUTextField.getUnderlineColor(), Color.RED);

        // Field's comportment
        mMUTextField?.isSecure = true
        assertEquals(true, mMUTextField?.isSecure)
        mMUTextField?.isEditable = false
        assertEquals(false, mMUTextField?.isEditable)
        mMUTextField?.isAutoCorrection = false
        assertEquals(false, mMUTextField?.isAutoCorrection)
        mMUTextField?.isReturnKeyAvailable = false
        assertEquals(false, mMUTextField?.isReturnKeyAvailable)
        mMUTextField?.isActive = true
        assertEquals(true, mMUTextField?.isActive)

        mMUTextField?.keyboardType = InputType.TYPE_CLASS_NUMBER
        assertEquals(mMUTextField?.keyboardType, InputType.TYPE_CLASS_NUMBER)
        mMUTextField?.tfListener = object : MUTextField.MUTextFieldListener {
            override fun focusLost(textField: AppCompatEditText) {

            }

            override fun isSelecting(textField: AppCompatEditText) {

            }

            override fun textUpdated(textField: AppCompatEditText) {

            }
        }
        assertNotNull(mMUTextField?.tfListener)

        mMUTextField?.alignment = RelativeLayout.ALIGN_PARENT_END
        assertEquals(mMUTextField?.alignment, RelativeLayout.ALIGN_PARENT_END)
    }
}
