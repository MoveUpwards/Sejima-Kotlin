package dev.moveupwards.sejima

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

import kotlin.math.max

/*
    Created by Antoine RICHE on march 2019.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUCard : CardView, MUViewHelper {

    /**
     * Container for the child view
     */
    private var mContentView: LinearLayout? = null
    /**
     * The main Card container
     */
    private var mRootView: LinearLayout? = null
    /**
     * MUHeader attached to Card
     */
    private var mMUHeader: MUHeader? = null


    /**
     * The top container padding
     */
    private var mTopPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()
    /**
     * The content left padding
     */
    private var mContentLeftPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()
    /**
     * The content right padding
     */
    private var mContentRightPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()
    /**
     * The content top padding
     */
    private var mContentTopPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()
    /**
     * The content bottom padding
     */
    private var mContentBottomPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()
    /**
     * The background color id
     */
    private var mBkgColor = Color.GRAY
    /**
     * The style of the card resource id
     */
    /**
     * Get the current style resource id attached to the card
     * @return the style resource id
     */
    /**
     * Apply the given style to the card
     * @property styleResId the resource id of the style to be applied
     */
    var styleResId = -1
        set(styleResId) {
            if (checkResource(resources, styleResId)) {
                field = styleResId
                invalidate()
            }
        }
    /**
     * ,The current border width
     */
    private var mBorderWidth = pixelsToDensity(resources.displayMetrics, 4f).toInt().toFloat()
    /**
     * The current border color
     */
    private var mBorderColor: Int = Color.TRANSPARENT
    /**
     * The current corner radius
     */
    private var mCornerRadius = 5f
    /**
     * The horizontal padding for the header
     */
    private var mHeaderHorizontalPadding = pixelsToDensity(resources.displayMetrics, 24f).toInt().toFloat()

    /**
     * Get the current view attached to the card
     * @return the view attached
     */
    val contentView: LinearLayout?
        get() = mContentView

    /**
     * Get the current top padding of the card
     * @return the top padding in dp
     */
    /**
     * Set the top padding
     * @property topPadding the top padding to apply in dp
     */
    var topPadding: Float
        get() = mTopPadding
        set(topPadding) {
            mTopPadding = topPadding
            invalidate()
        }

    /**
     * Get the current left padding for the content
     * @return the current left padding in dp
     */
    /**
     * Set the left padding for content
     * @property contentLeftPadding the left padding for content to apply in dp
     */
    var contentLeftPadding: Float
        get() = mContentLeftPadding
        set(contentLeftPadding) {
            mContentLeftPadding = contentLeftPadding
            invalidate()
        }

    /**
     * Get the current right padding for the content
     * @return the current right padding in dp
     */
    /**
     * Set the right padding for content
     * @property contentRightPadding the right padding for content to apply in dp
     */
    var contentRightPadding: Float
        get() = mContentRightPadding
        set(contentRightPadding) {
            mContentRightPadding = contentRightPadding
            invalidate()
        }

    /**
     * Get the current top padding for the content
     * @return the current top padding in dp
     */
    /**
     * Set the top padding for content
     * @property contentTopPadding the top padding for content to apply in dp
     */
    var contentTopPadding: Float
        get() = mContentTopPadding
        set(contentTopPadding) {
            mContentTopPadding = contentTopPadding
            invalidate()
        }

    /**
     * Get the current bottom padding for the content
     * @return the current bottom padding in dp
     */
    /**
     * Set the bottom padding for content
     * @property contentBottomPadding the bottom padding for content to apply in dp
     */
    var contentBottomPadding: Float
        get() = mContentBottomPadding
        set(contentBottomPadding) {
            mContentBottomPadding = contentBottomPadding
            invalidate()
        }

    /**
     * Get the current background color for the card
     * @return the background color as ARGB integer
     */
    /**
     * Set the background color
     * @property bkgColor the background color as ARGB integer
     */
    var bkgColor: Int
        get() = mBkgColor
        set(bkgColor) {
            mBkgColor = bkgColor
            mRootView?.setBackgroundColor(mBkgColor)
        }

    /**
     * Get the current border width value
     * @return the border width in dp
     */
    /**
     * Set the border width to apply
     * @property borderWidth the border width in dp
     */
    var borderWidth: Float
        get() = mBorderWidth
        set(borderWidth) {
            mBorderWidth = normalizeFloatValue(borderWidth, 0f, 100f)
            invalidate()
        }

    /**
     * Get the current border color
     * @return the border color as ARGB integer
     */
    /**
     * Set the given border color
     * @property borderColor the color as ARGB integer
     */
    var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            mBorderColor = borderColor
            setBackgroundColor(mBorderColor)
        }

    /**
     * Get the current corner radius of the card
     * @return the current corner radius
     */
    /**
     * Set the giver corner radius to the card
     * @property cornerRadius the corner radius to be applied
     */
    var cornerRadius: Float
        get() = mCornerRadius
        set(cornerRadius) {
            mCornerRadius = max(cornerRadius, 0f)
            radius = mCornerRadius
        }

    /**
     * Get the current horizontal padding for the header
     * @return the current horizontal padding for the header in dp
     */
    /**
     * Set the given horizontal padding for the header
     * @property headerHorizontalPadding the horizontal padding to apply in dp
     */
    var headerHorizontalPadding: Float
        get() = mHeaderHorizontalPadding
        set(headerHorizontalPadding) {
            mHeaderHorizontalPadding = max(headerHorizontalPadding, 0f)
            invalidate()
        }

    /**
     * Current title of the header
     * @return the title of the header
     */
    /**
     * Set the given title to the header
     * @property title the title to set
     */
    var title: String?
        get() = mMUHeader?.title
        set(title) {
            mMUHeader?.title = title
        }

    /**
     * Get the current font style of header's title
     * @return the font style resource id of header's title
     */
    /**
     * Set the given font style to header's title
     * @property titleFontStyle the font style resource id
     */
    var titleFontStyle: Int
        get() = mMUHeader?.titleFontStyle ?: -1
        set(titleFontStyle) {
            mMUHeader?.titleFontStyle = titleFontStyle
        }

    /**
     * Get the current header's title color
     * @return the color as ARGB integer
     */
    /**
     * Set the color of header's title
     * @property titleFontColor the color to set as ARGB integer
     */
    var titleFontColor: Int
        get() = mMUHeader?.titleColor ?: Color.BLACK
        set(titleFontColor) {
            mMUHeader?.titleColor = titleFontColor
        }

    /**
     * Current detail of the header
     * @return the detail of the header
     */
    /**
     * Set the given detail to the header
     * @property detail the detail to set
     */
    var detail: String?
        get() = mMUHeader?.detail
        set(detail) {
            mMUHeader?.detail = detail
        }

    /**
     * Get the current font style of header's detail
     * @return the font style resource id of header's title
     */
    /**
     * Set the given font style to header's detail
     * @property detailFontStyle the font style resource id
     */
    var detailFontStyle: Int
        get() = mMUHeader?.detailFontStyle ?: -1
        set(detailFontStyle) {
            mMUHeader?.detailFontStyle = detailFontStyle
        }

    /**
     * Get the current header's detail color
     * @return the color as ARGB integer
     */
    /**
     * Set the color of header's detail
     * @property detailFontColor the color to set as ARGB integer
     */
    var detailFontColor: Int
        get() = mMUHeader?.detailColor ?: Color.BLACK
        set(detailFontColor) {
            mMUHeader?.detailColor = detailFontColor
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
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUCard)
        mTopPadding = attributes.getDimension(R.styleable.MUCard_top_padding, mTopPadding)
        mContentLeftPadding = attributes.getDimension(R.styleable.MUCard_content_left_padding, mContentLeftPadding)
        mContentRightPadding = attributes.getDimension(R.styleable.MUCard_content_right_padding, mContentRightPadding)
        mContentTopPadding = attributes.getDimension(R.styleable.MUCard_content_top_padding, mContentTopPadding)
        mContentBottomPadding = attributes.getDimension(R.styleable.MUCard_content_bottom_padding, mContentBottomPadding)

        mBorderWidth = attributes.getDimension(R.styleable.MUCard_border_width, mBorderWidth)
        mBorderColor = attributes.getColor(R.styleable.MUCard_border_color, mBorderColor)
        mCornerRadius = attributes.getDimension(R.styleable.MUCard_corner_radius, mCornerRadius)
        mBkgColor = attributes.getInt(R.styleable.MUCard_android_background, mBkgColor)
        mHeaderHorizontalPadding = attributes.getDimension(R.styleable.MUCard_android_paddingHorizontal, mHeaderHorizontalPadding)

        init(context, attributes)
        attributes.recycle()
    }

    private fun init(context: Context, attributeSet: TypedArray?) {
        mRootView = LinearLayout(context)
        mRootView?.orientation = LinearLayout.VERTICAL
        addView(mRootView)

        mMUHeader = MUHeader(context, attributeSet)
        mRootView?.addView(mMUHeader)

        mContentView = LinearLayout(context)
        mRootView?.addView(mContentView)

        if (isInEditMode) {
            val tv = TextView(context)
            tv.text = context.resources.getString(R.string.app_name)
            //tv.setBackgroundColor(ContextCompat.getColor(context, R.color.primary)) // TODO: Use the appropriate color.
            addContentView(tv)
        }

        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mMUHeader?.setPadding((mHeaderHorizontalPadding / 2).toInt(), 0, (mHeaderHorizontalPadding / 2).toInt(), 0)

        val rootLp = mRootView?.layoutParams as LayoutParams
        rootLp.width = ViewGroup.LayoutParams.MATCH_PARENT
        rootLp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        rootLp.setMargins(mBorderWidth.toInt(), mBorderWidth.toInt(), mBorderWidth.toInt(), mBorderWidth.toInt())
        mRootView?.layoutParams = rootLp
        mRootView?.setPadding((mHeaderHorizontalPadding / 2).toInt(), mTopPadding.toInt(), (mHeaderHorizontalPadding / 2).toInt(), 0)

        val contentLp = mContentView?.layoutParams
        contentLp?.width = ViewGroup.LayoutParams.MATCH_PARENT
        contentLp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mContentView?.setPadding(mContentLeftPadding.toInt(), mContentTopPadding.toInt(), mContentRightPadding.toInt(), mContentBottomPadding.toInt())
        mContentView?.layoutParams = contentLp

        radius = mCornerRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setCardBackgroundColor(mBorderColor)
        mRootView?.let { applyRoundCornerToView(mCornerRadius, mBkgColor, it) }
    }

    override fun getTextAlignment(): Int {
        return mMUHeader?.alignment ?: View.TEXT_ALIGNMENT_CENTER
    }

    override fun setTextAlignment(textAlignment: Int) {
        mMUHeader?.alignment = textAlignment
    }

    /**
     * Add a view into the card
     * @param view the view to add to the card
     */
    fun addContentView(view: View) {
        mContentView?.removeAllViews()
        mContentView?.addView(view, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }
}
