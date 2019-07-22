package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout

import androidx.core.widget.TextViewCompat

import kotlin.math.max


/*
    Created by Antoine RICHE on march 2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUPinCode : LinearLayout, MUViewHelper {
    private val defaultSize = pixelsToDensity(resources.displayMetrics, 30f).toInt()
    /**
     * Number of character for an EditText
     */
    private val MAX_LENGTH = 1

    // ***
    // Feature: Should button be cleared when user click on a filled cell ?
    // ***

    /**
     * Number of characters
     */
    private var mCount = 4
    /**
     * Contains the different EditText
     */
    private var mEditTexts: Array<MUAppCompatEditText> = Array(0) {
        MUAppCompatEditText(context, this, it)
    }
    /**
     * The default character
     */
    private var mDefaultChar: String? = "•"
    /**
     * The font style for EditTexts
     */
    private var mFontStyle = -1
    /**
     * Space between the pin code cells
     */
    private var mCellSpacing = pixelsToDensity(resources.displayMetrics, 8f).toInt().toFloat()
    /**
     * Background color for pin code cells
     */
    private var mCellColor = Color.WHITE
    /**
     * Corner radius for pin code cells
     */
    private var mCellCornerRadius: Float = 0.toFloat()
    /**
     * Keyboard type
     */
    private var mKeyboardType = InputType.TYPE_CLASS_TEXT
    /**
     * The listener for code updates
     */
    /**
     * Get the listener for text updates
     * @return the listener attached to the pin code, null if there is not
     */
    /**
     * Set the listener for text updates
     * @propriety the listener to call on update
     */
    var updateListener: ((MUPinCode, String) -> Unit)? = null

    /**
     * Get the number of characters composing the code
     * @return the pin code count
     */
    /**
     * Set the number of characters needed for the code
     * @propriety count the code length
     */
    // If current array exists, keep old EditTexts
    // if current array exists
    // get old EditTexts
    // add new ones
    // else, create a new EditText array
    var count: Int
        get() = mEditTexts.size
        set(newValue) {
            var count = newValue
            count = normalizeIntValue(count, 0, count)
            val ets = Array(count) {
                MUAppCompatEditText(context, this, it)
            }
            for (i in 0 until count) {
                if (i < mEditTexts.size) {
                    ets[i] = mEditTexts[i]
                } else {
                    ets[i] = setUpEditText(MUAppCompatEditText(context, this, i))
                    addView(ets[i], i)
                }
            }

            for (i in count until mEditTexts.size) {
                removeViewAt(i)
            }

            mEditTexts = ets
            mCount = count
            requestLayout()
        }


    /**
     * Get the default character
     * @return the default character
     */
    /**
     * Set the default character
     * @propriety defaultChar the default character
     */
    var defaultChar: String?
        get() = mDefaultChar
        set(defaultChar) {
            if (!TextUtils.isEmpty(defaultChar)) {
                defaultChar?.let { mDefaultChar = it[0].toString() }
            }
        }

    /**+
     * Get the font style of pin code
     * @return the font style resource id
     */
    /**
     * Set the font style
     * @propriety fontStyle the resource id of the font style
     */
    var fontStyle: Int
        get() = mFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mFontStyle = fontStyle
                for (editText in mEditTexts) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editText.setTextAppearance(fontStyle)
                    } else {
                        TextViewCompat.setTextAppearance(editText, fontStyle)
                    }
                }
            }
        }

    /**
     * Get the space between pin code cells
     * @return the space in dp
     */
    /**
     * Set the space between pin code cells
     * @propriety cellSpacing the space to apply in dp
     */
    var cellSpacing: Float
        get() = mCellSpacing
        set(cellSpacing) {
            mCellSpacing = cellSpacing
            requestLayout()
        }

    /**
     * Get the background color of pin code cells
     * @return the color as ARGB integer
     */
    /**
     * Set the background color of pin code cells
     * @propriety cellColor the background color of pin code cells as ARGB integer
     */
    var cellColor: Int
        get() = mCellColor
        set(cellColor) {
            mCellColor = cellColor
            invalidate()
        }

    /**
     * Get the radius corner applied to pin code cells
     * @return the corner radius
     */
    /**
     * Set the corner radius of pin code cells
     * @propriety cellCornerRadius the radius of pin code cells
     */
    var cellCornerRadius: Float
        get() = mCellCornerRadius
        set(cellCornerRadius) {
            mCellCornerRadius = normalizeFloatValue(cellCornerRadius, 0f, cellCornerRadius)
            invalidate()
        }

    /**
     * Get the keyboard type (number, text, mail)
     * @return the type as integer
     */
    /**
     * Switch between different mode of input
     * @propriety keyboardType the input mode (number, text, mail) as integer
     */
    var keyboardType: Int
        get() = mKeyboardType
        set(keyboardType) {
            mKeyboardType = keyboardType
            for (editText in mEditTexts) {
                editText.inputType = mKeyboardType
            }
        }

    /**
     * Get the current code
     * @return the current code as string
     */
    /**
     * Insert the given code in the pin code
     * @propriety code the code to set
     */
    var code: String
        get() {
            val strB = StringBuilder()
            for (editText in mEditTexts) {
                strB.append(
                    if (!TextUtils.isEmpty(editText.text.toString()))
                        editText.text.toString()
                    else
                        ""
                )
            }

            return strB.toString()
        }
        set(code) {
            for (i in 0 until mCount) {
                if (i < code.length) {
                    mEditTexts[i].setText(code[i].toString())
                }
            }
        }

    /**
     * Default constructor
     * @param context the view context
     */
    constructor(context: Context) : super(context) {
        init()
    }

    /**
     * Constructor with attributes
     * @param context the view context
     * @param attrs the XML attributes for the view
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUPinCode)
        mCount = attributes.getInteger(R.styleable.MUPinCode_count, mCount)
        mCellCornerRadius = attributes.getDimensionPixelSize(R.styleable.MUPinCode_corner_radius, mCellCornerRadius.toInt()).toFloat()
        mFontStyle = attributes.getResourceId(R.styleable.MUPinCode_font_style, mFontStyle)
        mDefaultChar = attributes.getString(R.styleable.MUPinCode_default_char)
        mDefaultChar = if (!TextUtils.isEmpty(mDefaultChar)) mDefaultChar else "•"
        mCellSpacing = attributes.getDimensionPixelSize(R.styleable.MUPinCode_cell_spacing, mCellSpacing.toInt()).toFloat()
        mCellColor = attributes.getColor(R.styleable.MUPinCode_cell_color, mCellColor)
        mKeyboardType = attributes.getInt(R.styleable.MUPinCode_android_inputType, mKeyboardType)

        init()
        attributes.recycle()
    }

    private fun init() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        defaultChar = mDefaultChar
        count = mCount

        fontStyle = mFontStyle
        setFontColor(Color.BLACK)
        keyboardType = mKeyboardType
        isFocusable = true
        isFocusableInTouchMode = true
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (editText in mEditTexts) {
            applyRoundCornerToView(mCellCornerRadius, mCellColor, editText)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val freeWidthSpace = measuredWidth - (count * mCellSpacing).toInt()
        val maxWidth = freeWidthSpace / count
        val cellDim = max(maxWidth, defaultSize)

        for (editText in mEditTexts) {
            editText.width = cellDim
            editText.height = cellDim
            val lp = editText.layoutParams as LayoutParams
            lp.marginEnd = (mCellSpacing / 2).toInt()
            lp.marginStart = (mCellSpacing / 2).toInt()
            editText.layoutParams = lp
        }

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    fun setFontSize(size: Float) {
        for (mEditText in mEditTexts) {
            mEditText.textSize = size
        }
    }

    fun setFontColor(color: Int) {
        for (mEditText in mEditTexts) {
            mEditText.setTextColor(color)
        }
    }

    private fun setUpEditText(editText: MUAppCompatEditText): MUAppCompatEditText {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_LENGTH))
        editText.hint = mDefaultChar
        editText.setHintTextColor(Color.BLACK)
        editText.setTextColor(Color.BLACK)
        editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        editText.setPadding(0, 0, 0, 0)
        editText.gravity = Gravity.CENTER
        editText.isCursorVisible = false
        return editText
    }

    private inner class MUAppCompatEditText(context: Context, private val mPinCode: MUPinCode, private val mPosition: Int) : androidx.appcompat.widget.AppCompatEditText(context) {

        override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
            super.onTextChanged(text, start, lengthBefore, lengthAfter)

            if (lengthAfter == MAX_LENGTH) {
                mEditTexts[mPosition].clearFocus()
                if (mPosition < mCount - 1) {
                    mEditTexts[mPosition + 1].requestFocus()
                } else {
                    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard.hideSoftInputFromWindow(mEditTexts[mPosition].windowToken, 0)
                }
            }

            updateListener?.invoke(mPinCode, code)
        }
    }
}
