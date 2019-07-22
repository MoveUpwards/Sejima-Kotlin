package dev.moveupwards.sejima

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat

/*
    Created by Antoine RICHE on 20/03/2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUTopBar : RelativeLayout, MUViewHelper {

    /**
     * The default size of title
     */
    private val DEFAULT_TITLE_SIZE_IN_SP = 24f
    /**
     * The default width of button
     */
    private val DEFAULT_BUTTON_WIDTH_IN_SP = 40f

    /**
     * The TextView to display the title
     */
    private var mTVLabel: TextView? = null
    /**
     * The left image button
     */
    private var mIBLeftButton: ImageButton? = null

    /**
     * The current title
     */
    private var mTitle = ""
    /**
     * The title's font size
     */
    private var mTitleFontSize: Float = 0.toFloat()
    /**
     * The title's font weight
     */
    private var mTitleFontWeight: Int = 0

    private var mFontStyle = -1
    /**
     * The title's text color
     */
    private var mTitleColor = Color.WHITE
    /**
     * The title's horizontal alignment
     */
    private var mTitleAlignment = ALIGN_PARENT_START

    /**
     * The image for the button
     */
    private var mButtonImage = 0 //R.drawable.ic_launcher_background
    /**
     * The left padding
     */
    private var mLeftButtonLeading = 0f
    /**
     * The left button width
     */
    private var mLeftButtonWidth: Float = 0.toFloat()
    /**
     * Show button value
     */
    private var mShowButton = true
    /**
     * The listener to handle clicks on MUTopBar
     */
    /**
     * Get the interface to handle clicks
     * @return the interface if its set, null otherwise.
     */
    /**
     * Attach an interface which manages the clicks on the view
     * @property mClickListener the [View.OnClickListener] to attach.
     */
    var mClickListener: OnClickListener? = null
        set(clickListener) {
            field = clickListener
            clickListener?.let {
                mIBLeftButton?.setOnClickListener(it)
            } ?: run {
                mIBLeftButton?.setOnClickListener(null)
            }
        }

    /**
     * Get the left padding of ImageButton
     * @return the left padding of image button in dp.
     */
    /**
     * Set the left padding of ImageButton
     * @property leftButtonLeading the left padding of ImageButton in dp.
     */
    var leftButtonLeading: Float
        get() = mLeftButtonLeading
        set(leftButtonLeading) {
            mLeftButtonLeading = normalizeFloatValue(0f, leftButtonLeading, leftButtonLeading)
            mIBLeftButton?.layoutParams = getLeftBtnLayoutParams(mLeftButtonWidth)
        }

    /**
     * Get the left button width
     * @return the left button width in dp.
     */
    /**
     * Set the left button width of ImageButton
     * @property leftButtonWidth the left button width in dp.
     */
    var leftButtonWidth: Float
        get() = mLeftButtonWidth
        set(leftButtonWidth) {
            mLeftButtonWidth = leftButtonWidth
            updateImageWidth(mLeftButtonWidth)
        }

    /**
     * Get the title's text
     * @return the text of the Top Bar
     */
    /**
     * Set the text of the title
     * @property title the title's text
     */
    var title: String
        get() = mTitle
        set(title) {
            mTitle = title
            mTVLabel?.text = mTitle
        }

    /**
     * Get the text horizontal alignment
     * @return an integer representing the horizontal alignment.
     * Must be
     *
     *  * Gravity.START
     *  * Gravity.END
     *  * Gravity.CENTER
     *
     */
    /**
     * Set the text horizontal alignment
     * @property titleAlignment the integer representing the horizontal alignment.
     * Must be
     *
     *  * ALIGN_PARENT_START
     *  * ALIGN_PARENT_END
     *  * CENTER_IN_PARENT
     *
     */
    var titleAlignment: Int
        get() = mTitleAlignment
        set(newValue) {
            var titleAlignment = newValue
            if (titleAlignment != ALIGN_PARENT_END && titleAlignment != Gravity.CENTER) {
                titleAlignment = ALIGN_PARENT_START
            }

            mTVLabel?.gravity = titleAlignment
            mTitleAlignment = titleAlignment
        }

    /**
     * Get the font size in
     * @return the font size in dp.
     */
    /**
     * Set the font size
     * @property titleFontSize the title font size in dp.
     */
    var titleFontSize: Float
        get() = mTitleFontSize
        set(titleFontSize) {
            mTitleFontSize = titleFontSize
            mTVLabel?.textSize = mTitleFontSize
        }

    /**
     * Get the font weight
     * @return en integer representing the font weight
     */
    /**
     * Set the font weight
     * @property titleFontWeight an integer representing the font weight
     */
    var titleFontWeight: Int
        get() = mTitleFontWeight
        set(titleFontWeight) {
            mTitleFontWeight = titleFontWeight
            mTVLabel?.typeface = Typeface.create(Typeface.DEFAULT, mTitleFontWeight)
        }

    /**
     * Get the resource id of the drawable used by left image button
     * @return the resource id of the drawable
     */
    /**
     * Set the drawable of the image button by using its resource id
     * @property buttonImage the resource id of the left image button
     */
    var buttonImage: Int
        get() = mButtonImage
        set(buttonImage) {
            mButtonImage = buttonImage
            var drawable: Drawable? = null
            try {
                drawable = ResourcesCompat.getDrawable(resources, mButtonImage, null)
                mIBLeftButton?.setImageDrawable(drawable)
            } catch (e: Resources.NotFoundException) {
                drawable = null
            } finally {
                isButtonHidden = null == drawable
            }
        }

    /**
     * Get the code color of the title.
     * @return the code color as ARGB integer.
     */
    /**
     * Set the color of the text
     * @property titleColor the title color as ARGB integer.
     */
    var titleColor: Int
        get() = mTitleColor
        set(titleColor) {
            mTitleColor = titleColor
            mTVLabel?.setTextColor(mTitleColor)
        }

    /**
     * A boolean value to know if the button is visible
     * @return true if the button is visible, false otherwise
     */
    /**
     * Display/hide the left image button
     * @property isButtonHidden boolean value to hide or display the button
     */
    var isButtonHidden: Boolean
        get() = !mShowButton
        set(hideButton) {
            this.mShowButton = !hideButton
            mIBLeftButton?.visibility = if (this.mShowButton) View.VISIBLE else View.GONE
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
                mTVLabel?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUTopBar)
        val s: CharSequence?

        // Deal with title's attributes
        s = attributes.getString(R.styleable.MUTopBar_title) ?: ""
        mTitle = if (TextUtils.isEmpty(s)) mTitle else s.toString()
        mTitleColor = attributes.getColor(R.styleable.MUTopBar_title_color, mTitleColor)
        mTitleFontSize = attributes.getDimensionPixelSize(R.styleable.MUTopBar_title_size, 0).toFloat()
        mTitleFontWeight = attributes.getInt(R.styleable.MUTopBar_title_weight, mTitleFontWeight)
        mTitleAlignment = attributes.getInt(R.styleable.MUTopBar_topbar_title_alignment, mTitleAlignment)

        mLeftButtonWidth = attributes.getDimensionPixelSize(R.styleable.MUTopBar_topbar_img_width, 0).toFloat()
        mLeftButtonLeading = attributes.getDimensionPixelSize(R.styleable.MUTopBar_topbar_btn_leading, 0).toFloat()
        mButtonImage = attributes.getResourceId(R.styleable.MUTopBar_topbar_btn_img, mButtonImage)
        // Font Style
        mFontStyle = attributes.getResourceId(R.styleable.MUTopBar_font_style, mFontStyle)

        init(context)
        attributes.recycle()
    }

    /**
     * Method called to apply attributes to the view
     * @param context the view context
     */
    private fun init(context: Context) {
        val lpRoot = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams = lpRoot

        mLeftButtonWidth = if (mLeftButtonWidth != 0f) mLeftButtonWidth else DEFAULT_BUTTON_WIDTH_IN_SP
        mIBLeftButton = ImageButton(context)
        mIBLeftButton?.id = View.generateViewId()
        mIBLeftButton?.layoutParams = getLeftBtnLayoutParams(mLeftButtonWidth)
        buttonImage = mButtonImage
        addView(mIBLeftButton)

        val lpTVLabel = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lpTVLabel.addRule(CENTER_VERTICAL, TRUE)
        mIBLeftButton?.let { lpTVLabel.addRule(END_OF, it.id) }

        mTVLabel = TextView(context)
        mTVLabel?.layoutParams = lpTVLabel
        mTVLabel?.text = mTitle
        mTVLabel?.setTextColor(mTitleColor)
        mTitleFontSize = if (mTitleFontSize != 0f) mTitleFontSize else DEFAULT_TITLE_SIZE_IN_SP
        mTVLabel?.textSize = mTitleFontSize
        mTVLabel?.typeface = Typeface.create(Typeface.DEFAULT, mTitleFontWeight)
        titleAlignment = mTitleAlignment
        addView(mTVLabel)

        fontStyle = mFontStyle
    }

    /**
     * Apply the given width on the left image button
     * @param width the width in dp.
     */
    private fun updateImageWidth(width: Float) {
        mIBLeftButton?.layoutParams = getLeftBtnLayoutParams(width)
    }

    /**
     * Get the params to update width and padding of the left ImageButton
     * @param width the width in dp.
     * @return the layout params of the left image button.
     */
    private fun getLeftBtnLayoutParams(width: Float): LayoutParams {
        val lpImBtn = LayoutParams(width.toInt(), width.toInt())
        lpImBtn.addRule(CENTER_VERTICAL, TRUE)
        lpImBtn.addRule(ALIGN_PARENT_START, TRUE)
        lpImBtn.setMargins(mLeftButtonLeading.toInt(), mLeftButtonLeading.toInt(), mLeftButtonLeading.toInt(), mLeftButtonLeading.toInt())
        return lpImBtn
    }
}
