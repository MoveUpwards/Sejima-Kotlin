package dev.moveupwards.sejimasample

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat

import dev.moveupwards.sejima.MUButton

internal class FragmentMUButton : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_button
    }

    override fun title(): String {
        return "MUButton"
    }

    override fun initView(view: View) {
        val mMUButton: MUButton = view.findViewById(R.id.mu_button) ?: return // guard that we find it.

        val titleSize = mMUButton.labelFontSize
        val alignment = mMUButton.labelAlignment
        val cornerRadius = mMUButton.cornerRadius
        val borderWidth = mMUButton.borderWidth
        val hPad = mMUButton.horizontalPadding
        val vPad = mMUButton.verticalPadding

        mMUButton.listener = View.OnClickListener { Toast.makeText(context, "Click on button", Toast.LENGTH_SHORT).show() }

        // Label
        val etTitle = view.findViewById<EditText>(R.id.control_mu_button_et_label)
        view.findViewById<View>(R.id.control_mu_button_label).setOnClickListener { mMUButton.label = if (TextUtils.isEmpty(etTitle.text)) "" else etTitle.text.toString() }

        // Label color
        (view.findViewById<View>(R.id.control_mu_button_title_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.labelColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Label weight
        (view.findViewById<View>(R.id.control_mu_button_title_font) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.labelFontWeight = if (isChecked) Typeface.BOLD else Typeface.NORMAL
        }

        // Label size
        view.findViewById<View>(R.id.control_mu_button_title_less).setOnClickListener { mMUButton.labelFontSize = mMUButton.labelFontSize - 1 }
        view.findViewById<View>(R.id.control_mu_button_title_more).setOnClickListener { mMUButton.labelFontSize = mMUButton.labelFontSize + 1 }

        // Alignment
        view.findViewById<View>(R.id.control_mu_button_left).setOnClickListener { mMUButton.labelAlignment = Gravity.START }
        view.findViewById<View>(R.id.control_mu_button_right).setOnClickListener { mMUButton.labelAlignment = Gravity.END }
        view.findViewById<View>(R.id.control_mu_button_center).setOnClickListener { mMUButton.labelAlignment = Gravity.CENTER }

        // Alpha
        (view.findViewById<View>(R.id.control_mu_button_alpha) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.alpha = if (isChecked) 0.5f else 1.0f
            mMUButton.borderAlpha = if (isChecked) 0.5f else 1.0f
        }
        (view.findViewById<View>(R.id.control_mu_button_disabled_alpha) as Switch).setOnCheckedChangeListener { _, isChecked -> mMUButton.alpha = if (isChecked) 0.3f else 0.7f }

        // Pressed color
        (view.findViewById<View>(R.id.control_mu_button_pressed_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.labelHighLightedColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }
        // Progressing color
        (view.findViewById<View>(R.id.control_mu_button_progressing_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.progressingColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Loading
        (view.findViewById<View>(R.id.control_mu_button_loading) as Switch).setOnCheckedChangeListener { _, isChecked -> mMUButton.isLoading = isChecked }

        // Background color
        (view.findViewById<View>(R.id.control_mu_button_background) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.bkgColor = if (isChecked) {
                Color.LTGRAY
            } else {
                ContextCompat.getColor(view.context, R.color.colorPrimary)
            }
        }

        // Border color
        (view.findViewById<View>(R.id.control_mu_button_border_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUButton.borderColor = if (isChecked) {
                ContextCompat.getColor(view.context, R.color.colorAccent)
            } else {
                ContextCompat.getColor(view.context, R.color.colorPrimaryDark)
            }
        }

        // Border width
        view.findViewById<View>(R.id.control_mu_button_border_less).setOnClickListener { mMUButton.borderWidth = mMUButton.borderWidth - 1 }
        view.findViewById<View>(R.id.control_mu_button_border_more).setOnClickListener { mMUButton.borderWidth = mMUButton.borderWidth + 1 }

        // Corner radius
        view.findViewById<View>(R.id.control_mu_button_radius_less).setOnClickListener { mMUButton.cornerRadius = mMUButton.cornerRadius - 1 }
        view.findViewById<View>(R.id.control_mu_button_radius_more).setOnClickListener { mMUButton.cornerRadius = mMUButton.cornerRadius + 1 }

        // Horizontal padding
        view.findViewById<View>(R.id.control_mu_button_horizontal_less).setOnClickListener { mMUButton.horizontalPadding = mMUButton.horizontalPadding - 1 }
        view.findViewById<View>(R.id.control_mu_button_horizontal_more).setOnClickListener { mMUButton.horizontalPadding = mMUButton.horizontalPadding + 1 }

        // Vertical padding
        view.findViewById<View>(R.id.control_mu_button_vertical_less).setOnClickListener { mMUButton.verticalPadding = mMUButton.verticalPadding - 1 }
        view.findViewById<View>(R.id.control_mu_button_vertical_more).setOnClickListener { mMUButton.verticalPadding = mMUButton.verticalPadding + 1 }

        // RAZ
        view.findViewById<View>(R.id.control_mu_button_raz).setOnClickListener {
            (view.findViewById<View>(R.id.control_mu_button_title_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_title_font) as Switch).isChecked = false
            mMUButton.labelFontSize = titleSize
            mMUButton.labelAlignment = alignment
            (view.findViewById<View>(R.id.control_mu_button_alpha) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_disabled_alpha) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_pressed_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_progressing_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_loading) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_background) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_button_border_color) as Switch).isChecked = false
            mMUButton.borderWidth = borderWidth
            mMUButton.cornerRadius = cornerRadius
            mMUButton.horizontalPadding = hPad
            mMUButton.verticalPadding = vPad
        }
    }
}
