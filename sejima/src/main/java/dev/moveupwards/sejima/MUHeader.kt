package dev.moveupwards.sejima

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.core.widget.TextViewCompat

import kotlin.math.max

/*
    Created by Antoine RICHE on march 2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUHeader : RelativeLayout, MUViewHelper {

    /**
     * The TextView for the title
     */
    private var mTVTitle: TextView? = null
    /**
     * The TextView for the detail
     */
    private var mTVDetail: TextView? = null

    /**
     * The current title
     */
    private var mTitle: String? = ""
    /**
     * The title's size
     */
    private var mTitleSize = 24
    /**
     * The default title's weight
     */
    private var mTitleWeight = Typeface.NORMAL
    /**
     * The title's font style
     */
    private var mTitleFontStyle = -1

    /**
     * The title's text color
     */
    private var mTitleColor = Color.BLACK

    /**
     * The current detail description
     */
    private var mDetail: String? = ""
    /**
     * The detail's size
     */
    private var mDetailSize = 14
    /**
     * The default detail's weight
     */
    private var mDetailWeight = Typeface.BOLD
    /**
     * The detail's text color
     */
    private var mDetailColor = Color.BLACK
    /**
     * The detail's font style
     */
    private var mDetailFontStyle = -1

    /**
     * The text's horizontal alignment
     */
    private var mAlignment = Gravity.START
    /**
     * Vertical spacing between title and detail
     */
    private var mVerticalSpacing = 8

    /**
     * Get the current title
     * @return title of the header as String
     */
    /**
     * Set the given title to the header
     * @property title the text to set as String
     */
    var title: String?
        get() = mTitle
        set(title) {
            mTitle = title
            mTVTitle?.text = title
        }

    /**
     * Get the current font size of header's title
     * @return the font size of the title
     */
    /**
     * Set the font size of header's title
     * @property titleSize the size of header's title
     */
    var titleSize: Float
        get() = mTitleSize.toFloat()
        set(titleSize) {
            mTitleSize = titleSize.toInt()
            mTVTitle?.textSize = mTitleSize.toFloat()
        }

    /**
     * Get the current title's weight
     * @return integer representing weight of the title
     */
    /**
     * Set the given font weight to the title
     * @property titleWeight the font weight to apply
     */
    var titleWeight: Int
        get() = mTitleWeight
        set(titleWeight) {
            mTitleWeight = titleWeight
            mTVTitle?.typeface = Typeface.create(Typeface.DEFAULT, mTitleWeight)
        }

    /**
     * Get the current title color
     * @return the color as ARGB integer
     */
    /**
     * Set the color of title
     * @property titleColor the color to set as ARGB integer
     */
    var titleColor: Int
        get() = mTitleColor
        set(titleColor) {
            mTitleColor = titleColor
            mTVTitle?.setTextColor(mTitleColor)
        }

    /**
     * Get the current detail
     * @return detail of the header as String
     */
    /**
     * Set the given detail to the header
     * @property detail the text to set as String
     */
    var detail: String?
        get() = mDetail
        set(detail) {
            mDetail = detail
            mTVDetail?.text = mDetail
        }

    /**
     * Get the current font size of header's detail
     * @return the font size of the detail
     */
    /**
     * Set the font size of header's detail
     * @property detailSize the size of header's detail
     */
    var detailSize: Float
        get() = mDetailSize.toFloat()
        set(detailSize) {
            mDetailSize = detailSize.toInt()
            mTVDetail?.textSize = mDetailSize.toFloat()
        }

    /**
     * Get the current detail's weight
     * @return integer representing weight of the detail
     */
    /**
     * Set the given font weight to the detail
     * @property detailWeight the font weight to apply
     */
    var detailWeight: Int
        get() = mDetailWeight
        set(detailWeight) {
            mDetailWeight = detailWeight
            mTVDetail?.typeface = Typeface.create(Typeface.DEFAULT, mDetailWeight)
        }

    /**
     * Get the current detail color
     * @return the color as ARGB integer
     */
    /**
     * Set the color of detail
     * @property detailColor the color to set as ARGB integer
     */
    var detailColor: Int
        get() = mDetailColor
        set(detailColor) {
            mDetailColor = detailColor
            mTVDetail?.setTextColor(mDetailColor)
        }

    var alignment: Int
        get() = mAlignment
        set(alignment) {
            var gravity = alignment
            if (gravity != Gravity.END && gravity != Gravity.CENTER) {
                gravity = Gravity.START
            }
            mTVDetail?.gravity = gravity
            mTVTitle?.gravity = gravity
            mAlignment = gravity
        }

    var verticalSpacing: Int
        get() = mVerticalSpacing
        set(verticalSpacing) {
            mVerticalSpacing = max(0, verticalSpacing)
            mTVDetail?.setPadding(0, mVerticalSpacing, 0, 0)
        }

    /**
     * Get the current font style for the title
     * @return the resource id of the font style
     */
    /**
     * Set the font style for the title
     * @property titleFontStyle the resource id of the font style
     */
    var titleFontStyle: Int
        get() = mTitleFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mTitleFontStyle = fontStyle
                mTVTitle?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
            }
        }

    /**
     * Get the current font style for the detail
     * @return the resource id of the font style
     */
    /**
     * Set the font style for the detail
     * @property detailFontStyle the resource id of the font style
     */
    var detailFontStyle: Int
        get() = mDetailFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mDetailFontStyle = fontStyle
                mTVDetail?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
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
        val a = context.obtainStyledAttributes(attrs, R.styleable.MUHeader)

        var s: CharSequence?

        // Deal with title's attributes
        s = a.getString(R.styleable.MUHeader_title)
        mTitle = if (TextUtils.isEmpty(s)) mTitle else s?.toString()
        mTitleColor = a.getColor(R.styleable.MUHeader_title_color, mTitleColor)
        mTitleSize = a.getDimensionPixelSize(R.styleable.MUHeader_title_size, mTitleSize)
        mTitleWeight = a.getInt(R.styleable.MUHeader_title_weight, mTitleWeight)
        mTitleFontStyle = a.getResourceId(R.styleable.MUHeader_title_font_style, mTitleFontStyle)

        // Deal with detail's attributes
        s = a.getString(R.styleable.MUHeader_detail)
        mDetail = if (TextUtils.isEmpty(s)) mDetail else s?.toString()
        mDetailColor = a.getColor(R.styleable.MUHeader_detail_color, mDetailColor)
        mDetailSize = a.getDimensionPixelSize(R.styleable.MUHeader_detail_size, mDetailSize)
        mDetailWeight = a.getInt(R.styleable.MUHeader_detail_weight, mDetailWeight)
        mDetailFontStyle = a.getResourceId(R.styleable.MUHeader_detail_font_style, mDetailFontStyle)

        mVerticalSpacing = a.getDimensionPixelSize(R.styleable.MUHeader_vertical_spacing, mVerticalSpacing)
        mAlignment = a.getInt(R.styleable.MUHeader_alignment, mAlignment)

        init()
        a.recycle()
    }

    /**
     * Constructor used when a super view contains a MUHeader
     * @param context the view context
     * @param attributes the XML attributes of the super view
     */
    constructor(context: Context, attributes: TypedArray?) : super(context) {

        if (null != attributes) {

            mTitle = if (attributes.hasValue(R.styleable.MUCard_title))
                attributes.getString(R.styleable.MUCard_title)
            else
                mTitle
            mTitleColor = if (attributes.hasValue(R.styleable.MUCard_title_color))
                attributes.getColor(R.styleable.MUCard_title_color, mTitleColor)
            else
                mTitleColor
            mTitleSize = if (attributes.hasValue(R.styleable.MUCard_title_size))
                attributes.getDimensionPixelSize(R.styleable.MUCard_title_size, mTitleSize)
            else
                mTitleSize
            mTitleFontStyle = if (attributes.hasValue(R.styleable.MUCard_title_font_style))
                attributes.getResourceId(R.styleable.MUCard_title_font_style, mTitleFontStyle)
            else
                mTitleFontStyle

            mDetail = if (attributes.hasValue(R.styleable.MUCard_detail))
                attributes.getString(R.styleable.MUCard_detail)
            else
                mDetail
            mDetailColor = if (attributes.hasValue(R.styleable.MUCard_detail_color))
                attributes.getColor(R.styleable.MUCard_detail_color, mDetailColor)
            else
                mDetailColor
            mDetailSize = if (attributes.hasValue(R.styleable.MUCard_detail_size))
                attributes.getDimensionPixelSize(R.styleable.MUCard_detail_size, mDetailSize)
            else
                mDetailSize
            mDetailFontStyle = if (attributes.hasValue(R.styleable.MUCard_detail_font_style))
                attributes.getResourceId(R.styleable.MUCard_detail_font_style, mDetailFontStyle)
            else
                mDetailFontStyle

            mAlignment = if (attributes.hasValue(R.styleable.MUCard_alignment))
                attributes.getInt(R.styleable.MUCard_alignment, mAlignment)
            else
                mAlignment
        }

        init()
    }

    private fun init() {
        mTVTitle = setUpTextView(mTitle, mTitleColor, mTitleSize.toFloat(), mTitleWeight)
        mTVTitle?.id = View.generateViewId()
        addView(mTVTitle)

        mTVDetail = setUpTextView(mDetail, mDetailColor, mDetailSize.toFloat(), mDetailWeight)
        addView(mTVDetail)

        titleFontStyle = mTitleFontStyle
        detailFontStyle = mDetailFontStyle
        alignment = mAlignment
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val lpRoot = layoutParams
        lpRoot.width = LayoutParams.MATCH_PARENT
        lpRoot.height = LayoutParams.WRAP_CONTENT
        layoutParams = lpRoot

        val lpTitle = mTVTitle?.layoutParams as LayoutParams
        lpTitle.width = LayoutParams.MATCH_PARENT
        lpTitle.height = LayoutParams.WRAP_CONTENT
        lpTitle.addRule(ALIGN_PARENT_TOP)
        mTVTitle?.layoutParams = lpTitle

        val lpDetail = mTVDetail?.layoutParams as LayoutParams
        lpDetail.width = LayoutParams.MATCH_PARENT
        lpDetail.height = LayoutParams.WRAP_CONTENT
        mTVTitle?.let { lpDetail.addRule(BELOW, it.id) }
        mTVTitle?.layoutParams = lpTitle
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mTVDetail?.setPadding(0, mVerticalSpacing, 0, 0)
    }

    private fun setUpTextView(text: String?, color: Int, size: Float, typeface: Int): TextView {
        val tv = TextView(context)
        tv.text = text
        tv.setTextColor(color)
        tv.textSize = size
        tv.typeface = Typeface.create(Typeface.DEFAULT, typeface)
        return tv
    }
}
