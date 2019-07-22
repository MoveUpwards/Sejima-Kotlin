package dev.moveupwards.sejimasample

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat

import dev.moveupwards.sejima.MUTopBar

internal class FragmentMUTopBar : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_topbar
    }

    override fun title(): String {
        return "MUTopBar"
    }

    override fun initView(view: View) {
        val mMUTopBar: MUTopBar = view.findViewById(R.id.mu_topbar) ?: return // guard that we find it.

        val titleSize = mMUTopBar.titleFontSize
        val defaultLeading = mMUTopBar.leftButtonLeading
        val btnWidth = mMUTopBar.leftButtonWidth
        val alignment = mMUTopBar.titleAlignment

        mMUTopBar.mClickListener = View.OnClickListener { Toast.makeText(context, "Click on MUTopBar", Toast.LENGTH_SHORT).show() }

        // Title
        val etTitle = view.findViewById<EditText>(R.id.control_mu_topbar_et_title)
        view.findViewById<View>(R.id.control_mu_topbar_title).setOnClickListener { mMUTopBar.title = if (TextUtils.isEmpty(etTitle.text)) "" else etTitle.text.toString() }

        // Title color
        (view.findViewById<View>(R.id.control_mu_topbar_title_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUTopBar.titleColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Title weight
        (view.findViewById<View>(R.id.control_mu_topbar_title_font) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUTopBar.titleFontWeight = if (isChecked) Typeface.BOLD else Typeface.NORMAL
        }

        // Title size
        view.findViewById<View>(R.id.control_mu_topbar_title_less).setOnClickListener { mMUTopBar.titleFontSize = mMUTopBar.titleFontSize - 1 }
        view.findViewById<View>(R.id.control_mu_topbar_title_more).setOnClickListener { mMUTopBar.titleFontSize = mMUTopBar.titleFontSize + 1 }

        // Drawable
        (view.findViewById<View>(R.id.control_mu_topbar_image) as Switch).setOnCheckedChangeListener { _, isChecked -> mMUTopBar.buttonImage = if (isChecked) R.drawable.ic_launcher_background else R.drawable.ic_menu_camera }

        // Left leading
        view.findViewById<View>(R.id.control_mu_topbar_leading_less).setOnClickListener { mMUTopBar.leftButtonLeading = mMUTopBar.leftButtonLeading - 1 }
        view.findViewById<View>(R.id.control_mu_topbar_leading_more).setOnClickListener { mMUTopBar.leftButtonLeading = mMUTopBar.leftButtonLeading + 1 }

        // Btn width
        view.findViewById<View>(R.id.control_mu_topbar_width_less).setOnClickListener { mMUTopBar.leftButtonWidth = mMUTopBar.leftButtonWidth - 1 }
        view.findViewById<View>(R.id.control_mu_topbar_width_more).setOnClickListener { mMUTopBar.leftButtonWidth = mMUTopBar.leftButtonWidth + 1 }

        // Drawable
        (view.findViewById<View>(R.id.control_mu_topbar_image) as Switch).setOnCheckedChangeListener { _, isChecked -> mMUTopBar.buttonImage = if (isChecked) R.drawable.ic_launcher_background else R.drawable.ic_menu_camera }

        // Show button
        (view.findViewById<View>(R.id.control_mu_topbar_button_hidden) as Switch).setOnCheckedChangeListener { _, isChecked -> mMUTopBar.isButtonHidden = isChecked }

        // Alignment
        view.findViewById<View>(R.id.control_mu_topbar_left).setOnClickListener { mMUTopBar.titleAlignment = RelativeLayout.ALIGN_PARENT_START }
        view.findViewById<View>(R.id.control_mu_topbar_right).setOnClickListener { mMUTopBar.titleAlignment = RelativeLayout.ALIGN_PARENT_END }
        view.findViewById<View>(R.id.control_mu_topbar_center).setOnClickListener { mMUTopBar.titleAlignment = Gravity.CENTER } // TODO: Expected CENTER_IN_PARENT

        // RAZ
        view.findViewById<View>(R.id.control_mu_topbar_raz).setOnClickListener {
            (view.findViewById<View>(R.id.control_mu_topbar_title_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_topbar_title_font) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_topbar_image) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_topbar_image) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_topbar_button_hidden) as Switch).isChecked = false
            mMUTopBar.titleFontSize = titleSize
            mMUTopBar.leftButtonLeading = defaultLeading
            mMUTopBar.leftButtonWidth = btnWidth.toInt().toFloat()
            mMUTopBar.titleAlignment = alignment
        }
    }
}
