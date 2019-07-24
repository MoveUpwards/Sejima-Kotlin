package dev.moveupwards.sejima

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout

import kotlin.math.max

/*
    Created by Antoine RICHE on 28/03/2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUNavigationBar : LinearLayout, MUViewHelper {

    private var mRightButton: MUButton? = null
    private var mLeftButton: ImageButton? = null
    private var mSeparator: LinearLayout? = null


    /**
     * Background color
     */
    private var mBkgColor = Color.LTGRAY

    /**
     * Vertical padding
     */
    /**
     * Get the current vertical padding value
     * @return the current vertical padding value in dp
     */

    /**
     * Set the vertical padding value
     * @property verticalPadding the vertical padding value in pixels
     */

    var verticalPadding = pixelsToDensity(resources.displayMetrics, 8f).toInt().toFloat()
        set(verticalPadding) {
            field = max(0f, verticalPadding)
            mRightButton?.verticalPadding = this.verticalPadding.toInt()
        }

    /**
     * Horizontal padding
     */
    /**
     * Get the current horizontal padding value
     * @return the current horizontal padding value in dp
     */

    /**
     * Set the horizontal padding value
     * @property horizontalPadding the horizontal padding value in pixels
     */

    var horizontalPadding = pixelsToDensity(resources.displayMetrics, 8f).toInt().toFloat()
        set(horizontalPadding) {
            field = max(0f, horizontalPadding)
        }

    /**
     * The drawable image of the ImageButton
     */
    private var mImgDrawable: Drawable? = null
    /**
     * The separator color
     */
    private var mSeparatorColor = Color.BLACK

    /**
     * The separator width
     */
    private var mSeparatorWidth: Float = 0.toFloat()

    /**
     * The separator height multiplier
     */
    private var mSeparatorMultiplier = 1f
    /**
     * The separator margins
     */
    private var mSeparatorMargins = pixelsToDensity(resources.displayMetrics, 8f).toInt().toFloat()

    /**
     * Get the listener attached to the view, null if there is not
     * @return the listener
     */
    /**
     * Set the current listener
     * @property listener the listener as MUNavigationBarListener
     */
    var listener: MUNavigationBarListener? = null

    /**
     * Get the current label
     * @return the current label as String
     */
    /**
     * Set the current label
     * @property label the label as String
     */
    var label: String?
        get() = mRightButton?.label
        set(label) {
            mRightButton?.label = label
        }


    /**
     * Get the label font size
     * @return the font size in dp
     */
    /**
     * Set the label font size
     * @property labelFontSize the label font size in pixels.
     */
    var labelFontSize: Float
        get() = mRightButton?.labelFontSize ?: 0f
        set(labelFontSize) {
            mRightButton?.labelFontSize = labelFontSize
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
        get() = mRightButton?.labelFontWeight ?: 0
        set(labelFontWeight) {
            mRightButton?.labelFontWeight = labelFontWeight
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
        get() = mRightButton?.labelColor ?: Color.BLACK
        set(labelColor) {
            mRightButton?.labelColor = labelColor
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
     *  * Gravity.LEFT
     *  * Gravity.CENTER
     *  * Gravity.RIGHT
     *
     */

    var labelAlignment: Int
        get() = mRightButton?.labelAlignment ?: View.TEXT_ALIGNMENT_CENTER
        set(labelAlignment) {
            mRightButton?.labelAlignment = labelAlignment
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
        get() = mRightButton?.disabledAlpha ?: 0f
        set(disabledAlpha) {
            mRightButton?.disabledAlpha = disabledAlpha
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
        get() = mRightButton?.labelHighLightedColor ?: Color.BLACK
        set(labelHighLightedColor) {
            mRightButton?.labelHighLightedColor = labelHighLightedColor
        }


    /**
     * Get the current progressing color
     * @return the color as RGBA integer
     */

    /**
     * Set the progressing color
     * @property labelProgressingColor the progressing color as RGBA integer
     */
    var labelProgressingColor: Int
        get() = mRightButton?.progressingColor ?: Color.BLACK
        set(labelProgressingColor) {
            mRightButton?.progressingColor = labelProgressingColor
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
        get() = mRightButton?.isLoading ?: false
        set(loading) {
            mRightButton?.isLoading = loading
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
        get() = mRightButton?.bkgColor ?: Color.BLACK
        set(bkgColor) {
            setBackgroundColor(bkgColor)
            mRightButton?.bkgColor = bkgColor
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
        get() = mRightButton?.borderColor ?: Color.BLACK
        set(borderColor) {
            mRightButton?.borderColor = borderColor
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
        get() = mRightButton?.borderWidth ?: 0f
        set(borderWidth) {
            mRightButton?.borderWidth = borderWidth
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
        get() = mRightButton?.cornerRadius ?: 0
        set(cornerRadius) {
            mRightButton?.cornerRadius = cornerRadius
        }


    /**
     * Get the drawable attached to the button
     * @return the drawable image
     */

    /**
     * Set a drawable image to the button
     * @property imgDrawable the image drawable
     */
    var imgDrawable: Drawable?
        get() = mImgDrawable
        set(imgDrawable) {
            mImgDrawable = imgDrawable
            mLeftButton?.setImageDrawable(imgDrawable)
            invalidate()
        }


    /**
     * Get the color of the separator
     * @return the separator color as ARGB integer
     */

    /**
     * Set the color for separator
     * @property separatorColor the separator color as ARGB integer
     */
    var separatorColor: Int
        get() = mSeparatorColor
        set(separatorColor) {
            mSeparatorColor = separatorColor
            mSeparator?.setBackgroundColor(separatorColor)
        }


    /**
     * Get the separator width
     * @return the separator width as pixels
     */

    /**
     * Set the separator width
     * @property separatorWidth the separator width as pixels.
     */
    var separatorWidth: Float
        get() = mSeparatorWidth
        set(separatorWidth) {
            mSeparatorWidth = max(separatorWidth, 0f)
            requestLayout()
        }


    /**
     * Get the separator height multiplier
     * @return a float between 0 and 1 representing the height ratio
     */

    /**
     * Set the separator height multiplier
     * @property separatorMultiplier the separator height-multiplier, must be between 0 and 1
     */
    var separatorMultiplier: Float
        get() = mSeparatorMultiplier
        set(separatorMultiplier) {
            mSeparatorMultiplier = normalizeMultiplierValue(separatorMultiplier)
            requestLayout()
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
        get() = mRightButton?.fontStyle ?: -1
        set(fontStyle) {
            mRightButton?.fontStyle = fontStyle
        }

    /**
     * Default constructor
     * @param context the view context
     */
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    /**
     * Constructor with attributes
     * @param context the view context
     * @param attrs the XML attributes for the view
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUNavigationBar)
        mImgDrawable = attributes.getDrawable(R.styleable.MUNavigationBar_android_src)
        mSeparatorColor = attributes.getColor(R.styleable.MUNavigationBar_separator_color, mSeparatorColor)
        mSeparatorWidth = attributes.getDimensionPixelSize(R.styleable.MUNavigationBar_separator_width, mSeparatorWidth.toInt()).toFloat()
        mSeparatorMargins = attributes.getDimensionPixelSize(R.styleable.MUNavigationBar_separator_margins, mSeparatorMargins.toInt()).toFloat()
        mSeparatorMultiplier = normalizeMultiplierValue(attributes.getFloat(R.styleable.MUNavigationBar_separator_height_multiplier, mSeparatorMultiplier))
        mBkgColor = attributes.getColor(R.styleable.MUNavigationBar_bkg_color, mBkgColor)

        init(context, attributes)
        attributes.recycle()
    }

    private fun init(context: Context, attributes: TypedArray?) {

        orientation = HORIZONTAL
        setBackgroundColor(mBkgColor)
        gravity = Gravity.CENTER_VERTICAL

        mLeftButton = ImageButton(context)
        mLeftButton?.setPadding(0, 0, 0, 0)
        //mImgDrawable = if (mImgDrawable != null) mImgDrawable else ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher, null) // TODO: Put back?
        mLeftButton?.setImageDrawable(mImgDrawable)
        mLeftButton?.scaleType = ImageView.ScaleType.CENTER_CROP
        mLeftButton?.setOnClickListener { _ ->
            if (listener != null) {
                listener?.clickOnLeftButton(this)
            }
        }
        addView(mLeftButton)

        mSeparator = LinearLayout(context)
        mSeparator?.setBackgroundColor(mSeparatorColor)
        addView(mSeparator)

        mRightButton = MUButton(context, attributes)
        mRightButton?.setOnClickListener { _ ->
            if (listener != null) {
                listener?.clickOnRightButton(this)
            }
        }

        addView(mRightButton)

        horizontalPadding = horizontalPadding
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val right = mRightButton?.layoutParams as LayoutParams
        right.width = LayoutParams.MATCH_PARENT
        right.height = LayoutParams.WRAP_CONTENT
        mRightButton?.layoutParams = right
        mRightButton?.setHorizontalGravity(Gravity.CENTER)

        val left = mSeparator?.layoutParams as LayoutParams
        left.width = mSeparatorWidth.toInt()
        left.height = (height * mSeparatorMultiplier).toInt()
        left.marginStart = (mSeparatorMargins / 2).toInt()
        left.marginEnd = (mSeparatorMargins / 2).toInt()
        mSeparator?.layoutParams = left
    }

    override fun setTextAlignment(textAlignment: Int) {
        super.setTextAlignment(textAlignment)
        labelAlignment = textAlignment
    }

    /**
     * Interface to handle user clicks
     */
    interface MUNavigationBarListener {

        /**
         * Handle left clicks
         * @param muNavigationBar the current view
         */
        fun clickOnLeftButton(muNavigationBar: MUNavigationBar)


        /**
         * Handle right clicks
         * @param muNavigationBar the current view
         */
        fun clickOnRightButton(muNavigationBar: MUNavigationBar)
    }
}
