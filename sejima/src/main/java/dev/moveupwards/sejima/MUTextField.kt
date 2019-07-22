package dev.moveupwards.sejima

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.TextViewCompat

class MUTextField : RelativeLayout, MUViewHelper {

    /**
     * The scale used to convert px in dp
     */
    private var mScale: Float = 0.toFloat()
    /**
     * The TextView which displays the label of the field
     */
    private var mTVLabel: TextView? = null
    /**
     * The EditText for the input
     */
    private var mETInput: AppCompatEditText? = null
    /**
     * The label's text
     */
    private var mLabel: String? = ""
    /**
     * The title's font size
     */
    private var mLabelFontSize: Float = 0.toFloat()
    /**
     * The title's font weight
     */
    private var mLabelFontWeight = Typeface.NORMAL
    /**
     * The title's text color
     */
    private var mLabelColor = Color.WHITE
    /**
     * The text's horizontal alignment
     */
    private var mAlignment = ALIGN_PARENT_START
    /**
     * The field's text
     */
    private var mField: String? = ""
    /**
     * The field's font size
     */
    private var mFieldFontSize: Float = 0.toFloat()
    /**
     * The field's font weight
     */
    private var mFieldFontWeight = Typeface.NORMAL
    /**
     * The field's text color
     */
    private var mFieldColor: Int = 0
    /**
     * Is text field secure
     */
    private var mIsSecure = false
    /**
     * Is text field editable
     */
    private var mIsEditable = true
    /**
     * Keyboard type
     */
    private var mKeyboardType = InputType.TYPE_NULL
    /**
     * Enable/disable auto-correction
     */
    private var mAutoCorrection = true
    /**
     * Boolean to specify if return key is available
     */
    /**
     * Know if the return key is available on the soft keyboard
     * @return true if it is, false otherwise
     */
    /**
     * Enable / disable the return key on soft keyboard
     * @property isReturnKeyAvailable true if the input allows multi line, false otherwise
     */
    var isReturnKeyAvailable = true
        set(returnKeyAvailable) {
            field = returnKeyAvailable
            mETInput?.setSingleLine(!isReturnKeyAvailable)
        }
    /**
     * Placeholder for text field
     */
    private var mPlaceHolderText: String? = ""
    /**
     * Placeholder color for text field
     */
    private var mPlaceHolderFontColor: Int = 0
    /**
     * The underline's field color
     */
    /**
     * Get the input field's underline color
     * @return the color as ARGB integer
     */
    /**
     * Set the input field's underline color
     * @property underlineColor the color as ARGB integer
     */
    var underlineColor = Color.TRANSPARENT
        set(underlineColor) {
            field = underlineColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // TODO: Make an extension to handle this
                mETInput?.background?.colorFilter = BlendModeColorFilter(underlineColor, BlendMode.SRC_IN)
            } else {
                @Suppress("DEPRECATION")
                mETInput?.background?.setColorFilter(this.underlineColor, PorterDuff.Mode.SRC_IN)
            }
        }
    /**
     * The interface listener for text field
     */
    /**
     * Get the interface attached to the view
     * @return the MUTextField interface attached to the view, null if there is not.
     */
    /**
     * Attach a listener to the view
     * @property tfListener the listener to attach
     */
    var tfListener: MUTextFieldListener? = null

    /**
     * The title's font style
     */
    private var mLabelFontStyle = -1

    /**
     * The field's font style
     */
    private var mFieldFontStyle = -1

    /**
     * Get the label's text
     * @return the label's text as String
     */
    /**
     * Set the label's text
     * @property label the label's text as String
     */
    var label: String?
        get() = mLabel
        set(label) {
            mLabel = label
            mTVLabel?.text = mLabel
        }

    /**
     * Get the label font size
     * @return the label's font size as dp
     */
    /**
     * Set the label's font size
     * @property labelFontSize the size in pixels
     */
    var labelFontSize: Float
        get() = mLabelFontSize
        set(labelFontSize) {
            mLabelFontSize = labelFontSize * mScale
            mTVLabel?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLabelFontSize)
        }

    /**
     * Get the label's font weight
     * @return the label font weight
     */
    /**
     * Set the label's font weight
     * @property labelFontWeight the label's font weight as Integer
     */
    var labelFontWeight: Int
        get() = mLabelFontWeight
        set(labelFontWeight) {
            mLabelFontWeight = labelFontWeight
            mTVLabel?.typeface = Typeface.create(Typeface.DEFAULT, mLabelFontWeight)
        }

    /**
     * Get the label's color
     * @return the label's color as ARGB int
     */
    /**
     * Set the label's color
     * @property labelColor the label color as as ARGB int
     */
    var labelColor: Int
        get() = mLabelColor
        set(labelColor) {
            mLabelColor = labelColor
            mTVLabel?.setTextColor(mLabelColor)
        }

    /**
     * Get the horizontal alignment of the label and the text for the input field
     * @return the horizontal alignment as integer
     */
    /**
     * Seth the text horizontal alignment of the view
     * @property alignment the horizontal alignment for the label and the text field as integer.
     * Must be:
     *
     *  * ALIGN_PARENT_START
     *  * ALIGN_PARENT_END
     *  * CENTER_HORIZONTAL
     *
     */
    var alignment: Int
        get() = mAlignment
        set(alignment) {
            val ll = mTVLabel?.layoutParams as LayoutParams
            ll.removeRule(mAlignment)
            ll.addRule(alignment, TRUE)
            mTVLabel?.layoutParams = ll

            when(alignment) {
                ALIGN_PARENT_START -> mETInput?.gravity = Gravity.START
                ALIGN_PARENT_END -> mETInput?.gravity = Gravity.END
                CENTER_HORIZONTAL -> mETInput?.gravity = Gravity.CENTER
                else -> mETInput?.gravity = Gravity.START
            }
            mAlignment = alignment
        }

    /**
     * Get the input field value
     * @return the input field value as String
     */
    /**
     * Set the input field value
     * @property field the input field value as String
     */
    var field: String?
        get() = if (!TextUtils.isEmpty(mETInput?.text)) {
            mETInput?.text?.toString()
        } else {
            ""
        }
        set(newValue) {
            mField = newValue
            mETInput?.setText(mField)
        }

    /**
     * Get the input field's font size
     * @return the font size in dp
     */
    /**
     * Set the input field's font size
     * @property fieldFontSize the input field's font size in pixels
     */
    var fieldFontSize: Float
        get() = mFieldFontSize
        set(fieldFontSize) {
            mFieldFontSize = fieldFontSize * mScale
            mETInput?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFieldFontSize)
        }

    /**
     * Get the input field's font weight
     * @return the font weight as integer
     */
    /**
     * Set the input field's font weight
     * @property fieldFontWeight the font weight as integer
     */
    var fieldFontWeight: Int
        get() = mFieldFontWeight
        set(fieldFontWeight) {
            mFieldFontWeight = fieldFontWeight
            mETInput?.typeface = Typeface.create(Typeface.DEFAULT, mFieldFontWeight)
        }

    /**
     * Get the input field's color
     * @return the input field's color as ARGB integer
     */
    /**
     * Set the input field's color
     * @property fieldColor the color as ARGB integer
     */
    var fieldColor: Int
        get() = mFieldColor
        set(fieldColor) {
            mFieldColor = fieldColor
            mETInput?.setTextColor(mFieldColor)
        }

    /**
     * Get the boolean value indicating if the input text is hidden
     * @return the boolean value
     */
    /**
     * Enable to switch between hidden and shown input text
     * @property isSecure the boolean value which show/hide the input field's text
     */
    // Disable contextual action like copying/selection
    var isSecure: Boolean
        get() = mIsSecure
        set(secure) {
            mIsSecure = secure

            if (mKeyboardType == InputType.TYPE_CLASS_NUMBER) {
                mETInput?.inputType = if (mIsSecure)
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                else
                    mKeyboardType
            } else {
                mETInput?.inputType = if (mIsSecure)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else
                    mKeyboardType
            }

            mETInput?.isLongClickable = !secure
        }

    /**
     * Get the editable value of the input field
     * @return true if the input field is editable, false otherwise
     */
    /**
     * Enable or disable input field edition
     * @property isEditable the value of the edition-availability
     */
    var isEditable: Boolean
        get() = mIsEditable
        set(editable) {
            mIsEditable = editable
            mETInput?.isEnabled = editable
        }

    /**
     * Get the keyboard type (number, text, mail)
     * @return the type as integer
     */
    /**
     * Switch between different mode of input
     * @property keyboardType the input mode (number, text, mail) as integer
     */
    var keyboardType: Int
        get() = mKeyboardType
        set(keyboardType) {
            mKeyboardType = keyboardType
            mETInput?.inputType = mKeyboardType
        }

    /**
     * Get the state of the auto-correction
     * @return true if the auto-correction is active, false otherwise
     */
    /**
     * Enable / disable the auto-correction
     * @property isAutoCorrection the value of the auto-correction state
     */
    var isAutoCorrection: Boolean
        get() = mAutoCorrection
        set(autoCorrection) {
            mAutoCorrection = autoCorrection
            mETInput?.inputType = if (mAutoCorrection)
                InputType.TYPE_TEXT_FLAG_AUTO_CORRECT or mKeyboardType
            else
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or mKeyboardType
        }

    /**
     * Know if the input field is focused or not
     * @return true if focus is on input field, false otherwise
     */
    /**
     * Request or clear focus from the input field
     * @property isActive true to set the focus on the input field, false to clear focus
     */
    var isActive: Boolean
        get() = mETInput?.hasFocus() ?: false
        set(active) {
            if (active) {
                mETInput?.requestFocusFromTouch()
            } else {
                mETInput?.clearFocus()
            }
        }

    /**
     * Get the placeholder's text
     * @return the placeholder's text as String
     */
    /**
     * Set the placeholder's text
     * @property placeHolderText the placeholder's text as String
     */
    var placeHolderText: String?
        get() = mPlaceHolderText
        set(placeHolderText) {
            mPlaceHolderText = placeHolderText
            mETInput?.hint = mPlaceHolderText
        }

    /**
     * Get the color of the placeholder's text
     * @return the color as ARGB integer
     */
    /**
     * Set the placeholder's text color
     * @property placeHolderFontColor the color as ARGB integer
     */
    var placeHolderFontColor: Int
        get() = mPlaceHolderFontColor
        set(placeHolderFontColor) {
            mPlaceHolderFontColor = placeHolderFontColor
            mETInput?.setHintTextColor(mPlaceHolderFontColor)
        }

    /**
     * Get the current font style for the label
     * @return the resource id of the font style
     */
    /**
     * Set the font style for the label
     * @property labelFontStyle the resource id of the font style
     */
    var labelFontStyle: Int
        get() = mLabelFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mLabelFontStyle = fontStyle
                mTVLabel?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
            }
        }

    /**
     * Get the current font style for the field
     * @return the resource id of the font style
     */
    /**
     * Set the font style for the field
     * @property fieldFontStyle the resource id of the font style
     */
    var fieldFontStyle: Int
        get() = mFieldFontStyle
        set(fontStyle) {
            if (checkResource(resources, fontStyle)) {
                mFieldFontStyle = fontStyle
                mETInput?.let { TextViewCompat.setTextAppearance(it, fontStyle) }
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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MUTextField)

        var s = attributes.getString(R.styleable.MUTextField_title)
        mLabel = if (TextUtils.isEmpty(s)) mLabel else s
        mLabelColor = attributes.getColor(R.styleable.MUTextField_title_color, mLabelColor)
        mLabelFontSize = attributes.getDimensionPixelSize(R.styleable.MUTextField_title_size, 0).toFloat()
        mLabelFontWeight = attributes.getInt(R.styleable.MUTextField_title_weight, mLabelFontWeight)
        mLabelFontStyle = attributes.getResourceId(R.styleable.MUTextField_title_font_style, mLabelFontStyle)

        s = attributes.getString(R.styleable.MUTextField_field)
        mField = if (TextUtils.isEmpty(s)) mField else s
        mFieldColor = attributes.getColor(R.styleable.MUTextField_field_color, mFieldColor)
        mFieldFontSize = attributes.getDimensionPixelSize(R.styleable.MUTextField_field_size, 0).toFloat()
        mFieldFontWeight = attributes.getInt(R.styleable.MUTextField_field_weight, mFieldFontWeight)
        mFieldFontStyle = attributes.getResourceId(R.styleable.MUTextField_field_font_style, mFieldFontStyle)

        mIsSecure = attributes.getBoolean(R.styleable.MUTextField_is_secure, false)
        mIsEditable = attributes.getBoolean(R.styleable.MUTextField_android_editable, true)
        mAutoCorrection = attributes.getBoolean(R.styleable.MUTextField_auto_correct, true)
        mKeyboardType = attributes.getInt(R.styleable.MUTextField_android_inputType, mKeyboardType)

        s = attributes.getString(R.styleable.MUTextField_android_hint)
        mPlaceHolderText = if (TextUtils.isEmpty(s)) mPlaceHolderText else s
        mPlaceHolderFontColor = attributes.getColor(R.styleable.MUTextField_android_textColorHint, mPlaceHolderFontColor)
        init(context)
        attributes.recycle()
    }

    private fun init(context: Context) {
        mScale = context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

        val lpRoot = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams = lpRoot

        // Field's label
        val lpTVLabel = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lpTVLabel.addRule(ALIGN_PARENT_TOP, TRUE)
        lpTVLabel.addRule(mAlignment, TRUE)
        mTVLabel = TextView(context)
        mTVLabel?.layoutParams = lpTVLabel
        mTVLabel?.id = View.generateViewId()
        label = mLabel
        labelColor = mLabelColor
        labelFontWeight = mLabelFontWeight
        mLabelFontSize = if (mLabelFontSize != 0f) mLabelFontSize else mTVLabel?.textSize ?: 0f
        mTVLabel?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLabelFontSize)
        addView(mTVLabel)

        // Input field
        val lpEVInput = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lpEVInput.addRule(mAlignment, TRUE)
        mTVLabel?.let { lpEVInput.addRule(BELOW, it.id) }
        mETInput = object : AppCompatEditText(context) {
            override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
                super.onFocusChanged(focused, direction, previouslyFocusedRect)
                if (null != tfListener && !focused) {
                    tfListener?.focusLost(this)
                }
            }

            override fun onSelectionChanged(selStart: Int, selEnd: Int) {
                super.onSelectionChanged(selStart, selEnd)
                if (null != tfListener && selEnd - selStart > 0) {
                    tfListener?.isSelecting(this)
                }
            }

            override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
                super.onTextChanged(text, start, lengthBefore, lengthAfter)
                if (null != tfListener) {
                    tfListener?.textUpdated(this)
                }
            }
        }

        mETInput?.layoutParams = lpEVInput
        // Field's font
        field = mField
        mFieldColor = if (mFieldColor != 0) mFieldColor else mETInput?.currentTextColor ?: Color.BLACK
        fieldColor = mFieldColor
        fieldFontWeight = mFieldFontWeight
        mFieldFontSize = if (mFieldFontSize != 0f) mFieldFontSize else mETInput?.textSize ?: 0f
        mETInput?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFieldFontSize)
        // Field's placeholder
        placeHolderText = mPlaceHolderText
        mPlaceHolderFontColor = if (mPlaceHolderFontColor != 0) mPlaceHolderFontColor else mETInput?.currentHintTextColor ?: Color.BLACK
        placeHolderFontColor = mPlaceHolderFontColor
        underlineColor = underlineColor // Comment this line to pass tests

        // Field's comportment
        isSecure = mIsSecure
        isEditable = mIsEditable
        isAutoCorrection = mAutoCorrection
        keyboardType = mKeyboardType
        isReturnKeyAvailable = isReturnKeyAvailable
        addView(mETInput)

        labelFontStyle = mLabelFontStyle
        fieldFontStyle = mFieldFontStyle
    }

    /**
     * The interface of the view to listen for events.
     */
    interface MUTextFieldListener {
        fun focusLost(textField: AppCompatEditText)
        fun isSelecting(textField: AppCompatEditText)
        fun textUpdated(textField: AppCompatEditText)
    }
}
