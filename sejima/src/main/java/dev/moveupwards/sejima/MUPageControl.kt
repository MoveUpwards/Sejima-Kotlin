package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import java.util.LinkedHashMap

import kotlin.math.max

/*
    Created by Antoine RICHE on 2019/04/02.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUPageControl : LinearLayout, MUViewHelper {

    private val mHMButtons = LinkedHashMap<Int, Indicator>()

    /**
     * The number of pages
     * @return the number of pages
     */
    /**
     * Set the number of pages and update views
     * @property numberPages the number of pages attached to the controller
     */
    var numberPages = 0
        set(numberPages) {
            mHMButtons.clear()
            removeAllViews()
            field = max(numberPages, 0)
            for (i in 0 until this.numberPages) {
                val btn = Indicator(context, i, this)
                mHMButtons[i] = btn
                addView(btn)
            }
            layoutParams.height = (elementWidth * 1.5).toInt()
            updateVisibility()
        }
    private var mCurrentPosition = -1
    /**
     * Get the PageControlListener attached to the current view
     * @return the page control listener instance, null if not
     */
    /**
     * Register a listener for the current page control
     * @property listener the listener which handles click on elements
     */
    var listener: ((MUPageControl, Int) -> Unit)? = null

    /**
     * The element width
     */
    /**
     * The element size
     * @return size of the elements in dp
     */
    /**
     * Set the element size
     * @property elementWidth the size in pixels
     */
    var elementWidth = pixelsToDensity(resources.displayMetrics, 10f).toInt()
    /**
     * The element height
     */
    private var mElementHeight = pixelsToDensity(resources.displayMetrics, 10f).toInt()
    /**
     * The un-active element color
     */
    /**
     * Get the color of elements
     * @return color as ARGB integer
     */
    /**
     * Set the color of elements
     * @property elementColor color as ARGB integer
     */
    var elementColor = Color.LTGRAY
    /**
     * The active element width
     */
    /**
     * Get the active element width
     * @return the active element width in dp
     */
    /**
     * Set the active element width
     * @property activeElementWidth the width in pixels
     */
    var activeElementWidth = pixelsToDensity(resources.displayMetrics, 10f).toInt()
    /**
     * The active element width
     */
    private var mActiveElementRadius: Int = 0
    /**
     * The un-active element color
     */
    /**
     * Get the active element color
     * @return color as ARGB integer
     */
    /**
     * Set the active element color
     * @property activeElementColor color as ARGB integer
     */
    var activeElementColor = Color.BLACK
    /**
     * The un-active element border color
     */
    private var mBorderColor = Color.TRANSPARENT
    /**
     * The element border width
     */
    private var mBorderWidth = 0
    /**
     * The padding between elements
     */
    private var mElementPadding = pixelsToDensity(resources.displayMetrics, 2f).toInt()
    /**
     * The hideForSingleElement value
     */
    private var mHideForSingleElementValue = false

    /**
     * Get the current position
     * @return the current index
     */
    /**
     * Set the current position of the controller
     * @property currentPosition the index of the current page
     */
    var currentPosition: Int
        get() = mCurrentPosition
        set(currentPosition) {
            updateSelection(normalizeIntValue(currentPosition, 0, numberPages - 1))
        }

    /**
     * Get the element radius
     * @return the element radius as integer
     */
    /**
     * Set the element radius
     * @property activeElementRadius the value of element radius
     */
    var activeElementRadius: Int
        get() = mActiveElementRadius
        set(activeElementRadius) {
            mActiveElementRadius = normalizeIntValue(activeElementRadius, 0, elementWidth / 2)
            updateButtonsAppearance()
        }

    /**
     * Get the element border color
     * @return color as ARGB integer
     */
    /**
     * Set the element border color
     * @property borderColor color as ARGB integer
     */
    var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            mBorderColor = borderColor
            updateButtonsAppearance()
        }

    /**
     * Get the current element width
     * @return the element width in dp
     */
    /**
     * Set the element width
     * @property borderWidth the width in dp
     */
    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            mBorderWidth = normalizeIntValue(borderWidth, 0, elementWidth)
            updateButtonsAppearance()
        }

    /**
     * Get the padding between elements
     * @return the padding in dp
     */
    /**
     * Set the padding between elements
     * @property elementPadding the padding in dp
     */
    var elementPadding: Int
        get() = mElementPadding
        set(elementPadding) {
            mElementPadding = elementPadding
            mElementPadding = normalizeIntValue(elementPadding, 0, availableWidth / numberPages)
            updateButtonsAppearance()
        }

    /**
     * Get available width when adjusting padding between elements
     * @return the available width in dp
     */
    private val availableWidth: Int // TODO: Should make page control bigger to let wrapContent works
        get() = width - elementWidth * (numberPages - 1) - activeElementWidth

    /**
     * Determine if a single element has to be hidden
     * @return the hidden boolean value
     */
    /**
     * Set the visibility of a single element
     * @property isHideForSingleElementValue the boolean hidden value
     */
    var isHideForSingleElementValue: Boolean
        get() = mHideForSingleElementValue
        set(hideForSingleElementValue) {
            mHideForSingleElementValue = hideForSingleElementValue
            updateVisibility()
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
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUPageControl)
        elementWidth = attributes.getDimensionPixelSize(R.styleable.MUPageControl_element_width, elementWidth)
        mElementHeight = attributes.getDimensionPixelSize(R.styleable.MUPageControl_element_height, mElementHeight)
        elementColor = attributes.getColor(R.styleable.MUPageControl_element_color, elementColor)
        activeElementWidth = attributes.getDimensionPixelSize(R.styleable.MUPageControl_active_element_width, activeElementWidth)
        mActiveElementRadius = attributes.getInt(R.styleable.MUPageControl_active_element_radius, mActiveElementRadius)
        activeElementColor = attributes.getColor(R.styleable.MUPageControl_active_element_color, activeElementColor)
        mBorderColor = attributes.getColor(R.styleable.MUPageControl_element_border_color, mBorderColor)
        mBorderWidth = attributes.getDimensionPixelSize(R.styleable.MUPageControl_element_border_width, mBorderWidth)
        mElementPadding = attributes.getDimensionPixelSize(R.styleable.MUPageControl_element_padding, mElementPadding)
        mHideForSingleElementValue = attributes.getBoolean(R.styleable.MUPageControl_hide_for_single_page, mHideForSingleElementValue)

        init()
        attributes.recycle()
    }

    private fun init() {
        setWillNotDraw(false)
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER
        numberPages = numberPages // TODO: Useful?
        if (isInEditMode) {
            numberPages = 2
        }

        activeElementRadius = mActiveElementRadius
        updateVisibility()
        invalidate()
    }

    private fun updateVisibility() {
        visibility = View.VISIBLE
        if (numberPages == 0 || numberPages == 1 && mHideForSingleElementValue) {
            visibility = View.GONE
        }
    }

    private fun updateButtonsAppearance() {
        for (btn in mHMButtons.values) {
            btn.updateLayout()
        }
        invalidate()
    }

    private fun updateSelection(newSelection: Int) {
        if (mCurrentPosition != newSelection) {
            mCurrentPosition = newSelection
            for (key in mHMButtons.keys) {
                mHMButtons[key]?.isSelected = key == newSelection
            }
        }
    }

    private inner class Indicator(context: Context, private val mPosition: Int, muPageControl: MUPageControl) : LinearLayout(context) {

        private val mContentView = LinearLayout(context)
        private val mClickListener = OnClickListener {
            updateSelection(mPosition)
            listener?.invoke(muPageControl, mPosition)
        }

        init {
            setOnClickListener(mClickListener)
            mContentView.setOnClickListener(mClickListener)
            addView(mContentView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            isSelected = false
        }

        override fun setSelected(isSelected: Boolean) {
            val btnLp = if (isSelected) {
                LayoutParams(activeElementWidth, mElementHeight)
            } else {
                LayoutParams(elementWidth, mElementHeight)
            }

            layoutParams = btnLp
            updateLayout()
        }

        fun updateLayout() {
            val isSelected = currentPosition == this.mPosition

            applyRoundCornerToView(mActiveElementRadius.toFloat(), if (isSelected) activeElementColor else elementColor, mContentView)
            applyRoundCornerToView(mActiveElementRadius.toFloat(), mBorderColor, this)

            // Deal with the border width
            val lp = mContentView.layoutParams as LayoutParams
            lp.setMargins(mBorderWidth, mBorderWidth, mBorderWidth, mBorderWidth)
            mContentView.layoutParams = lp

            // Deal with the external padding
            val btnLp = layoutParams as LayoutParams
            btnLp.setMargins(mElementPadding / 2, 0, mElementPadding / 2, 0)
            layoutParams = btnLp
        }
    }
}
