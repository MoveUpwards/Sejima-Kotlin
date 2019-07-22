package dev.moveupwards.sejima

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

import androidx.core.graphics.ColorUtils
import androidx.core.widget.TextViewCompat

import com.google.android.material.button.MaterialButton

import kotlin.math.max

/*
    Created by Antoine RICHE on 27/03/2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUButton : RelativeLayout, MUViewHelper {

    /**
     * Static fields representing states; used for convenience only
     */
    private val STATE_PRESSED = intArrayOf(android.R.attr.state_pressed)
    private val STATE_DISABLED = intArrayOf(-android.R.attr.state_enabled)
    private val STATE_DEFAULT = intArrayOf()
    private val STATES = arrayOf(STATE_PRESSED, STATE_DISABLED, STATE_DEFAULT)

    /**
     * OnCLickListener to handle clicks
     */
    private var mListener: OnClickListener? = null

    /**
     * The current background alpha
     */
    private var mAlpha = 1f

    /**
     * The alpha value for disabled state
     */
    private var mDisabledAlpha = 0.7f

    /**
     * The current border alpha
     */
    private var mBorderAlpha = 1f

    /**
     * Label of the button
     */
    private var mLabel: String? = ""

    /**
     * The label font size
     */
    private var mLabelFontSize = 12f

    /**
     * The label font weight
     */
    private var mLabelFontWeight = Typeface.NORMAL

    /**
     * The label font color
     */
    private var mLabelColor = Color.BLACK

    /**
     * The label alignment
     */
    private var mLabelAlignment = Gravity.CENTER

    /**
     * The label highlighted color
     */
    private var mLabelHighLightedColor = Color.BLACK

    /**
     * The progressing color
     */
    private var mProgressingColor = Color.BLACK

    /**
     * Show or hide the progress indicator
     */
    private var mIsLoading = false

    /**
     * Background color
     */
    private var mBkgColor = Color.LTGRAY

    /**
     * Border color
     */
    private var mBorderColor = Color.LTGRAY

    /**
     * Border width
     */
    private var mBorderWidth = 0f

    /**
     * Corner radius
     */
    private var mCornerRadius = 0

    /**
     * Vertical padding
     */
    private var mVerticalPadding = pixelsToDensity(resources.displayMetrics, 8f).toInt()

    /**
     * Horizontal padding
     */
    private var mHorizontalPadding = pixelsToDensity(resources.displayMetrics, 8f).toInt()

    private var mFontStyle = -1


    /**
     * The main button
     */
    /**
     * Get the MaterialButton
     * @return the main button
     */
    var button: MaterialButton? = null
        private set

    /**
     * ProgressBar showing loading
     */
    /**
     * Return the progressbar used to show loading
     * @return the ProgressBar
     */
    private var progressBar: ProgressBar? = null

    /**
     * Get the listener for clicks
     * @return the current listener attached to the view, null if there is no listener.
     */
    /**
     * Attach a listener to handle clicks
     * @property listener the listener to attach
     */
    var listener: OnClickListener?
        get() = mListener
        set(listener) = setOnClickListener(listener)


    /**
     * Get the border alpha
     * @return the border alpha as float
     */
    /**
     * Set the border alpha
     * @property borderAlpha the border alpha as float
     */
    var borderAlpha: Float
        get() = mBorderAlpha
        set(borderAlpha) {
            mBorderAlpha = normalizeAlphaValue(borderAlpha)
            updateColorWithAlphaValues()
            setBackgroundColors()
        }

    /**
     * Get the current label
     * @return the current label as String
     */
    /**
     * Set the current label
     * @property label the label as String
     */
    var label: String?
        get() = mLabel
        set(label) {
            mLabel = if (!TextUtils.isEmpty(label)) label else ""
            button?.text = label
        }

    /**
     * Get the label font size
     * @return the font size in dp
     */
    /**
     * Set the label font size
     * @property labelFontSize the label font size in dp.
     */
    var labelFontSize: Float
        get() = mLabelFontSize
        set(labelFontSize) {
            mLabelFontSize = max(labelFontSize, 0f)
            button?.textSize = mLabelFontSize
        }

    /**
     * Get the current label font weight
     * @return the current label font weight as integer
     */
    /**
     * Set the label font weight
     * @property labelFontWeight the label font weight as integer
     */
    var labelFontWeight: Int
        get() = mLabelFontWeight
        set(labelFontWeight) {
            mLabelFontWeight = labelFontWeight
            button?.typeface = Typeface.create(Typeface.DEFAULT, mLabelFontWeight)
        }

    /**
     * Get the current label color as integer
     * @return the label color as RGBA integer
     */
    /**
     * Set the label color
     * @property labelColor the label color as RGBA integer
     */
    var labelColor: Int
        get() = mLabelColor
        set(labelColor) {
            mLabelColor = labelColor
            setFontColors()
        }

    /**
     * Get the current label alignment
     * @return the integer representing the current horizontal alignment
     */
    /**
     * Set the label alignment
     * @property labelAlignment the label alignment as integer.
     * Must be one of those
     *
     *  * Gravity.START
     *  * Gravity.CENTER
     *  * Gravity.END
     *
     */
    var labelAlignment: Int
        get() = mLabelAlignment
        set(labelAlignment) {
            var alignment = labelAlignment
            if (alignment != Gravity.END && alignment != Gravity.CENTER) {
                alignment = Gravity.START
            }
            mLabelAlignment = alignment or Gravity.CENTER_VERTICAL
            button?.gravity = mLabelAlignment
        }

    /**
     * Get the current alpha value of disabled state
     * @return the current alpha value as float
     */
    /**
     * Set the alpha value for disabled state
     * @property disabledAlpha the alpha value to apply
     * Alpha must be between 0 and 1
     */
    var disabledAlpha: Float
        get() = mDisabledAlpha
        set(disabledAlpha) {
            mDisabledAlpha = normalizeAlphaValue(disabledAlpha)
            setBackgroundColors()
        }

    /**
     * Get the current color for highlighted (=pressed) state
     * @return the current pressed state color as RGBA integer
     */
    /**
     * Set the color for pressed state
     * @property labelHighLightedColor the color as RGBA integer
     */
    var labelHighLightedColor: Int
        get() = mLabelHighLightedColor
        set(labelHighLightedColor) {
            mLabelHighLightedColor = labelHighLightedColor
            setFontColors()
        }

    /**
     * Get the current progressing color
     * @return the color as RGBA integer
     */
    /**
     * Set the progressing color
     * @property progressingColor the progressing color as RGBA integer
     */
    var progressingColor: Int
        get() = mProgressingColor
        set(labelProgressingColor) {
            mProgressingColor = labelProgressingColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar?.indeterminateTintList = ColorStateList.valueOf(mProgressingColor)
            }
        }

    /**
     * Get the value of the loading state
     * @return the boolean value of the loading state
     */
    /**
     * Set the loading value
     * @property isLoading the loading state value as boolean
     */
    var isLoading: Boolean
        get() = mIsLoading
        set(loading) {
            mIsLoading = loading
            Handler(Looper.getMainLooper()).post { progressBar?.visibility = if (mIsLoading) View.VISIBLE else View.GONE }
            button?.setTextColor(if (mIsLoading) Color.TRANSPARENT else mLabelColor)
        }

    /**
     * Get the current background color
     * @return the background color as RGBA integer
     */
    /**
     * Set the background color
     * @property bkgColor the color as RGBA integer
     */
    var bkgColor: Int
        get() = mBkgColor
        set(bkgColor) {
            mBkgColor = bkgColor
            updateColorWithAlphaValues()
            setBackgroundColors()
        }

    /**
     * Get the current border color
     * @return the border color as RGBA integer
     */
    /**
     * Set the border color
     * @property borderColor the border color as ARGB integer
     */
    var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            mBorderColor = borderColor
            button?.strokeColor = ColorStateList.valueOf(borderColor)
            setBackgroundColors()
        }

    /**
     * Get the current border width
     * @return the current border width in pixels.
     */
    /**
     * Set the border width in pixels
     * @property borderWidth the border width in pixels
     */
    var borderWidth: Float
        get() = mBorderWidth
        set(borderWidth) {
            mBorderWidth = max(borderWidth, 0f)
            button?.strokeWidth = mBorderWidth.toInt()
        }

    /**
     * Get the current corner radius value
     * @return the current corner radius
     */
    /**
     * Set the radius value
     * @property cornerRadius the radius value
     */
    var cornerRadius: Int
        get() = mCornerRadius
        set(cornerRadius) {
            mCornerRadius = max(cornerRadius, 0)
            button?.cornerRadius = mCornerRadius
        }

    /**
     * Get the current vertical padding value
     * @return the current vertical padding value in dp
     */
    /**
     * Set the vertical padding value
     * @property verticalPadding the vertical padding value in pixels
     */
    var verticalPadding: Int
        get() = mVerticalPadding
        set(verticalPadding) {
            mVerticalPadding = max(verticalPadding, 0)
            button?.let {
                it.setPadding(it.paddingLeft, verticalPadding / 2, it.paddingRight, verticalPadding / 2)
            }
        }

    /**
     * Get the current horizontal padding value
     * @return the current horizontal padding value in dp
     */
    /**
     * Set the horizontal padding value
     * @property horizontalPadding the horizontal padding value in pixels
     */
    var horizontalPadding: Int
        get() = mHorizontalPadding
        set(horizontalPadding) {
            mHorizontalPadding = max(horizontalPadding, 0)
            button?.let {
                it.setPadding(horizontalPadding / 2, it.paddingTop, horizontalPadding / 2, it.paddingBottom)
            }
        }

    /**
     * Get the current font style
     * @return the resource id of the font style
     */
    /**
     * Set the font style
     * @property fontStyle the resource id of the font style
     */
    var fontStyle: Int
        get() = mFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mFontStyle = fontStyle
                button?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
            }
        }


    /**
     * Default constructor
     * @param context the view context
     */
    constructor(context: Context) : super(context) {
        init(context)
    }


    /**
     * Constructor with attributes
     * @param context the view context
     * @param attrs the XML attributes for the view
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUButton)
        // Background
        mBkgColor = attributes.getColor(R.styleable.MUButton_bkg_color, mBkgColor)
        // Alphas
        mAlpha = attributes.getFloat(R.styleable.MUButton_android_alpha, mAlpha)
        mBorderAlpha = attributes.getFloat(R.styleable.MUButton_border_alpha, mBorderAlpha)
        mDisabledAlpha = attributes.getFloat(R.styleable.MUButton_disable_alpha, mDisabledAlpha)
        // Label
        mLabel = attributes.getString(R.styleable.MUButton_android_text)
        mLabelColor = attributes.getColor(R.styleable.MUButton_android_textColor, mLabelColor)
        //        mLabelFontSize = attributes.getDimensionPixelSize(R.styleable.MUButton_android_textSize, (int) mLabelFontSize);
        mLabelFontWeight = attributes.getInt(R.styleable.MUButton_android_textStyle, mLabelFontWeight)
        mLabelAlignment = attributes.getInt(R.styleable.MUButton_alignment, mLabelAlignment)
        mLabelHighLightedColor = attributes.getColor(R.styleable.MUButton_pressed_color, mLabelHighLightedColor)
        mProgressingColor = attributes.getColor(R.styleable.MUButton_progressing_color, mProgressingColor)
        // Border
        mBorderWidth = attributes.getDimensionPixelSize(R.styleable.MUButton_strokeWidth, mBorderWidth.toInt()).toFloat()
        mBorderColor = attributes.getColor(R.styleable.MUButton_strokeColor, mBkgColor)
        mCornerRadius = attributes.getDimensionPixelSize(R.styleable.MUButton_cornerRadius, mCornerRadius)
        // Padding
        mVerticalPadding = attributes.getDimensionPixelSize(R.styleable.MUButton_android_paddingVertical, mVerticalPadding)
        mHorizontalPadding = attributes.getDimensionPixelSize(R.styleable.MUButton_android_paddingHorizontal, mVerticalPadding)
        // IsLoading
        mIsLoading = attributes.getBoolean(R.styleable.MUButton_is_loading, mIsLoading)
        // Font Style
        mFontStyle = attributes.getResourceId(R.styleable.MUButton_font_style, mFontStyle)

        init(context)
        attributes.recycle()
    }

    /**
     * Constructor used when a super view contains a MUButton
     * @param context the view context
     * @param attributes the XML attributes of the super view
     */
    constructor(context: Context, attributes: TypedArray?) : super(context) {

        if (null != attributes) {

            mDisabledAlpha = if (attributes.hasValue(R.styleable.MUNavigationBar_disable_alpha)) {
                attributes.getFloat(R.styleable.MUNavigationBar_disable_alpha, mDisabledAlpha)
            } else {
                mDisabledAlpha
            }
            mBkgColor = if (attributes.hasValue(R.styleable.MUNavigationBar_bkg_color)) {
                attributes.getColor(R.styleable.MUNavigationBar_bkg_color, mBkgColor)
            } else {
                mBkgColor
            }
            mLabel = if (attributes.hasValue(R.styleable.MUNavigationBar_android_text)) {
                attributes.getString(R.styleable.MUNavigationBar_android_text)
            } else {
                mLabel
            }
            mLabelColor = if (attributes.hasValue(R.styleable.MUNavigationBar_android_textColor)) {
                attributes.getColor(R.styleable.MUNavigationBar_android_textColor, mLabelColor)
            } else {
                mLabelColor
            }
            mLabelFontSize = if (attributes.hasValue(R.styleable.MUNavigationBar_android_textSize)) {
                attributes.getDimension(R.styleable.MUNavigationBar_android_textSize, mLabelFontSize) // was getDimensionPixelSize
            } else {
                mLabelFontSize
            }
            mLabelFontWeight = if (attributes.hasValue(R.styleable.MUNavigationBar_android_textStyle)) {
                attributes.getColor(R.styleable.MUNavigationBar_android_textStyle, mLabelFontWeight)
            } else {
                mLabelFontWeight
            }
            mLabelAlignment = if (attributes.hasValue(R.styleable.MUNavigationBar_alignment)) {
                attributes.getInt(R.styleable.MUNavigationBar_alignment, mLabelAlignment)
            } else {
                mLabelAlignment
            }
            mLabelHighLightedColor = if (attributes.hasValue(R.styleable.MUNavigationBar_pressed_color)) {
                attributes.getInt(R.styleable.MUNavigationBar_pressed_color, mLabelHighLightedColor)
            } else {
                mLabelHighLightedColor
            }
            mProgressingColor = if (attributes.hasValue(R.styleable.MUNavigationBar_progressing_color)) {
                attributes.getInt(R.styleable.MUNavigationBar_progressing_color, mProgressingColor)
            } else {
                mProgressingColor
            }
            mBorderWidth = if (attributes.hasValue(R.styleable.MUNavigationBar_border_width)) {
                attributes.getDimension(R.styleable.MUNavigationBar_border_width, 0f) // was getDimensionPixelSize
            } else {
                mBorderWidth
            }
            mBorderColor = if (attributes.hasValue(R.styleable.MUNavigationBar_border_color))
                attributes.getColor(R.styleable.MUNavigationBar_border_color, mBorderColor)
            else
                mBorderColor
            mCornerRadius = if (attributes.hasValue(R.styleable.MUNavigationBar_corner_radius))
                attributes.getDimensionPixelSize(R.styleable.MUNavigationBar_corner_radius, mCornerRadius)
            else
                mBorderColor
            mIsLoading = if (attributes.hasValue(R.styleable.MUNavigationBar_is_loading))
                attributes.getBoolean(R.styleable.MUNavigationBar_is_loading, false)
            else
                mIsLoading
            mHorizontalPadding = if (attributes.hasValue(R.styleable.MUNavigationBar_android_paddingHorizontal))
                attributes.getDimensionPixelSize(R.styleable.MUNavigationBar_android_paddingHorizontal, mHorizontalPadding)
            else
                mHorizontalPadding
            mVerticalPadding = if (attributes.hasValue(R.styleable.MUNavigationBar_android_paddingVertical))
                attributes.getDimensionPixelSize(R.styleable.MUNavigationBar_android_paddingVertical, mVerticalPadding)
            else
                mVerticalPadding
            mFontStyle = if (attributes.hasValue(R.styleable.MUNavigationBar_font_style))
                attributes.getResourceId(R.styleable.MUNavigationBar_font_style, mFontStyle)
            else
                mFontStyle
        }

        init(context)
    }

    private fun init(context: Context) {

        button = MaterialButton(context)
        button?.gravity = Gravity.CENTER
        button?.isAllCaps = false
        button?.id = View.generateViewId()
        addView(button)

        progressBar = ProgressBar(context)
        progressBar?.isIndeterminate = true

        val lp2 = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button?.let {
            lp2.addRule(ALIGN_END, it.id)
            lp2.addRule(ALIGN_START, it.id)
            lp2.addRule(ALIGN_TOP, it.id)
            lp2.addRule(ALIGN_BOTTOM, it.id)
        }
        addView(progressBar, lp2)

        // Background
        bkgColor = mBkgColor
        // Alphas
        alpha = mAlpha
        borderAlpha = mBorderAlpha
        disabledAlpha = mDisabledAlpha
        //Label
        label = mLabel
        labelFontWeight = mLabelFontWeight
        labelAlignment = mLabelAlignment
        progressingColor = mProgressingColor
        // Border
        borderWidth = mBorderWidth
        cornerRadius = mCornerRadius
        borderColor = mBorderColor
        // Is loading
        isLoading = false
        // Listener
        setOnClickListener(mListener)
        // Colors
        setFontColors()
        setBackgroundColors()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        button?.width = measuredWidth
        button?.height = measuredHeight

        button?.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding)
        progressBar?.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mListener = l
        button?.setOnClickListener(l)
    }

    /**
     * Get the current background alpha
     * @return the current alpha as float
     */
    override fun getAlpha(): Float {
        return mAlpha
    }


    /**
     * Set the background alpha
     * @param alpha the background alpha as float
     * Alpha must be between 0 and 1
     */
    override fun setAlpha(alpha: Float) {
        mAlpha = normalizeAlphaValue(alpha)
        updateColorWithAlphaValues()
        setBackgroundColors()
    }

    override fun setTextAlignment(textAlignment: Int) {
        super.setTextAlignment(textAlignment)
        labelAlignment = textAlignment
    }


    private fun updateColorWithAlphaValues() {
        mBkgColor = ColorUtils.setAlphaComponent(mBkgColor, (mAlpha * 255).toInt())
        mBorderColor = ColorUtils.setAlphaComponent(mBorderColor, (mBorderAlpha * 255).toInt())
    }

    /**
     * Set the background color for different states of the button
     */
    private fun setBackgroundColors() {
        val colors = intArrayOf(mBorderColor, // pressed color
            ColorUtils.setAlphaComponent(mBorderColor, (mAlpha * mDisabledAlpha * 255f).toInt()), // disabled color
            mBkgColor                                                                               // default color
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button?.backgroundTintList = ColorStateList(STATES, colors)
        }
    }

    /**
     * Set the font color for different states
     */
    private fun setFontColors() {
        val colors = intArrayOf(mLabelHighLightedColor, // pressed color
            mLabelHighLightedColor, // disabled color
            mLabelColor                                                                             // default color
        )

        button?.setTextColor(ColorStateList(STATES, colors))
    }
}
