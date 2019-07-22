package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

import java.lang.Math.pow

import kotlin.math.min
import kotlin.math.sqrt

/*
    Created by Antoine RICHE on 26/03/2019.
    Converted in Kotlin by Damien on 16/07/2019.
    credits: https://github.com/hdodenhof/CircleImageView
 */
class MUAvatar : AppCompatImageView, MUViewHelper {


    // Drawing tools variables
    /**
     * RectF representing the image area
     */
    private val mDrawableRect = RectF()
    /**
     * RectF representing the border area
     */
    private val mBorderRect = RectF()
    private val mShaderMatrix = Matrix()
    /**
     * Paint used to draw the image
     */
    private val mBitmapPaint = Paint()
    /**
     * Paint used to draw the image's border
     */
    private val mBorderPaint = Paint()
    /**
     * Paint used to draw the circular background
     */
    private val mBkgPaint = Paint()

    /**
     * The Bitmap representation of image
     */
    private var mBitmap: Bitmap? = null
    /**
     * The Bitmap shader attached to Bitmap
     */
    private var mBitmapShader: BitmapShader? = null
    /**
     * The Bitmap width
     */
    private var mBitmapWidth: Int = 0
    /**
     * The Bitmap height
     */
    private var mBitmapHeight: Int = 0
    /**
     * Indicates if the view is ready to be drawn
     */
    private var mReady: Boolean = false
    /**
     * Indicates if the view is being rendered
     */
    private var mSetupPending: Boolean = false


    // Custom attributes
    /**
     * The border color
     */
    private var mBorderColor = Color.BLACK
    /**
     * The background color if the image is not large enough
     */
    private var mBkgColor = Color.TRANSPARENT
    /**
     * The border width
     */
    private var mBorderWidth: Float = 0.toFloat()
    /**
     * The drawable representation of the image
     */
    private var mImage: Drawable? = null
    /**
     * The placeholder image as drawable
     */
    /**
     * Get the placeholder drawable image
     * @return the placeholder drawable image
     */
    /**
     * Set the placeholder drawable image
     * @property placeholderImage the placeholder drawable image
     */
    var placeholderImage: Drawable? = null
    /**
     * Interface handling user interaction
     */
    /**
     * Get the current listener
     * @return the current interface listener
     */
    /**
     * Set the interface listener to handle click events
     * @property clickListener the interface listener
     */
    var clickListener: ((MUAvatar) -> Unit)? = null
    /**
     * The current type of border
     */
    private var mBorderType = SQUARE_BORDER
    /**
     * The current corner radius (=1000) in case of circle border
     */
    private var mCornerRadius: Float = 0.toFloat()

    /**
     * Get the image attached to the view
     * @return the image as Drawable
     */
    /**
     * Attach the image to the view
     * @property image the image as Drawable
     */
    var image: Drawable?
        get() = drawable
        set(image) {
            mImage = image ?: placeholderImage
            setImageDrawable(mImage)
        }

    /**
     * Get the background color
     * @return the background color as RGBA integer
     */
    /**
     * Set the background color
     * @property bkgColor the background color as RGBA integer
     */
    var bkgColor: Int
        get() = mBkgColor
        set(@ColorInt bkgColor) {
            if (bkgColor == mBkgColor) {
                return
            }

            mBkgColor = bkgColor
            mBkgPaint.color = bkgColor
            invalidate()
        }

    /**
     * Get the border color
     * @return the border color as RGBA integer
     */
    /**
     * Set and apply borderColor
     * @property borderColor the color as RGBA integer
     */
    var borderColor: Int
        get() = mBorderColor
        set(@ColorInt borderColor) {
            if (borderColor == mBorderColor) {
                return
            }

            mBorderColor = borderColor
            mBorderPaint.color = mBorderColor
            invalidate()
        }

    /**
     * Get the border width
     * @return the border width in dp
     */
    /**
     * Set the border width
     * @property borderWidth the border width in pixels
     */
    var borderWidth: Float
        get() = mBorderWidth
        set(borderWidth) {
            mBorderWidth = normalizeFloatValue(borderWidth, 0f, borderWidth)
            setup()
        }

    /**
     * Get the border type
     * @return the border type as integer
     */
    var borderType: Int
        get() = mBorderType
        set(borderType) {
            mBorderType = if (borderType == ROUND_BORDER) ROUND_BORDER else SQUARE_BORDER
            mCornerRadius = (if (mBorderType == SQUARE_BORDER)
                0
            else
                1000).toFloat()
            invalidate()
        }

    /**
     * Get the corner radius
     * @return the corner radius as float
     */
    /**
     * Set the corner radius
     */
    var cornerRadius: Float
        get() = mCornerRadius
        set(cornerRadius) {
            mCornerRadius = if (mBorderType == SQUARE_BORDER) normalizeFloatValue(cornerRadius, 0f, 100f) else 1000f
            invalidate()
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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUAvatar)
        mBorderWidth = attributes.getDimension(R.styleable.MUAvatar_border_width, mBorderWidth)
        mBorderColor = attributes.getColor(R.styleable.MUAvatar_border_color, mBorderColor)
        mBkgColor = attributes.getColor(R.styleable.MUAvatar_bkg_color, mBkgColor)
        mCornerRadius = attributes.getDimension(R.styleable.MUAvatar_corner_radius, mCornerRadius)
        mBorderType = attributes.getInt(R.styleable.MUAvatar_border_type, mBorderType)
        mImage = attributes.getDrawable(R.styleable.MUAvatar_android_src)

        init()
        attributes.recycle()
    }

    private fun init() {

        setImageDrawable(mImage)
        scaleType = ScaleType.CENTER_CROP
        borderColor = mBorderColor
        cornerRadius = mCornerRadius
        borderType = mBorderType
        borderWidth = if (mBorderWidth != 0f) mBorderWidth else DEFAULT_BORDER_WIDTH_IN_DP

        mReady = true

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }

        val root = this
        setOnTouchListener { _, event ->
            if (clickListener != null
                && event.action == MotionEvent.ACTION_DOWN
                && isInCircle(event.x, event.y)) {
                clickListener?.invoke(root)
                this.performClick()
            }
            false
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (mBitmap == null) {
            return
        }

        // Deal with the background color
        if (mBkgColor != Color.TRANSPARENT) {
            canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius, mBkgPaint)
        }

        canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius, mBitmapPaint)

        // Deal with the border color
        if (mBorderWidth > 0) {
            canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius, mBorderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    /**
     * Retrieve the drawable source image as Bitmap
     * @param drawable the source image
     * @return the image as a Bitmap
     */
    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap: Bitmap
        try {
            bitmap = Bitmap.createBitmap(
                (drawable.intrinsicWidth - 2 * mBorderWidth).toInt(),
                (drawable.intrinsicHeight - 2 * mBorderWidth).toInt(),
                Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

        return bitmap
    }

    private fun initializeBitmap() {
        mImage = drawable
        mImage = if (mImage != null) mImage else placeholderImage
        mBitmap = getBitmapFromDrawable(mImage)
        setup()
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }

        if (width == 0 && height == 0) {
            return
        }

        if (mBitmap == null) {
            invalidate()
            return
        }

        mBitmap?.let { mBitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP) }

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth

        mBkgPaint.style = Paint.Style.FILL
        mBkgPaint.isAntiAlias = true
        mBkgPaint.color = mBkgColor


        mBitmapHeight = mBitmap?.height ?: 0
        mBitmapWidth = mBitmap?.width ?: 0

        mBorderRect.set(calculateBounds())
        mDrawableRect.set(mBorderRect)

        updateShaderMatrix()
        invalidate()
    }

    /**
     * Return the coordinates of the border area according to current view as RectF
     */
    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom

        val sideLength = min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate((dx + 0.5f).toInt() + mDrawableRect.left, (dy + 0.5f).toInt() + mDrawableRect.top)

        mBitmapShader?.setLocalMatrix(mShaderMatrix)
    }

    /**
     * Determine if the touch event is in the circle zone
     * @param x the x coordinate of touch
     * @param y the y coordinate of touch
     * @return true if the touch occurred in the circle, false otherwise
     */
    private fun isInCircle(x: Float, y: Float): Boolean {
        val distance = sqrt(
            pow((mDrawableRect.centerX() - x).toDouble(), 2.0) + pow((mDrawableRect.centerY() - y).toDouble(), 2.0)
        )
        return distance <= mDrawableRect.width() / 2
    }

    companion object {

        /**
         * Static fields to determinate the border type
         */
        const val SQUARE_BORDER: Int = 0
        const val ROUND_BORDER: Int = 1
        const val DEFAULT_BORDER_WIDTH_IN_DP = 3f
    }
}
