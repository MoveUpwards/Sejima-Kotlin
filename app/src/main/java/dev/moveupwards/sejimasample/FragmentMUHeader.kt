package dev.moveupwards.sejimasample

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Switch

import androidx.core.content.ContextCompat

import dev.moveupwards.sejima.MUHeader

internal class FragmentMUHeader : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_header
    }

    override fun title(): String {
        return "MUHeader"
    }

    override fun initView(view: View) {
        val mMUHeader: MUHeader = view.findViewById(R.id.mu_header) ?: return // guard that we find it.

        val titleSize = mMUHeader.titleSize
        val detailSize = mMUHeader.detailSize
        val verticalSpace = mMUHeader.verticalSpacing.toFloat()
        val alignment = mMUHeader.alignment.toFloat()

        // Title
        val etTitle: EditText = view.findViewById(R.id.control_mu_header_et_title)
        etTitle.setOnClickListener { mMUHeader.title = if (TextUtils.isEmpty(etTitle.text)) "" else etTitle.text.toString() }

        // Title color
        (view.findViewById<View>(R.id.control_mu_header_title_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUHeader.titleColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Title weight
        (view.findViewById<View>(R.id.control_mu_header_title_font) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUHeader.titleWeight = if (isChecked) Typeface.BOLD else Typeface.NORMAL
        }

        // Title size
        view.findViewById<View>(R.id.control_mu_header_title_less).setOnClickListener { mMUHeader.titleSize = mMUHeader.titleSize - 1 }
        view.findViewById<View>(R.id.control_mu_header_title_more).setOnClickListener { mMUHeader.titleSize = mMUHeader.titleSize + 1 }


        // Details
        val etDetails = view.findViewById<EditText>(R.id.control_mu_header_et_details)
        view.findViewById<View>(R.id.control_mu_header_details).setOnClickListener { mMUHeader.detail = if (TextUtils.isEmpty(etDetails.text)) "" else etDetails.text.toString() }

        // Details color
        (view.findViewById<View>(R.id.control_mu_header_details_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUHeader.detailColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Details weight
        (view.findViewById<View>(R.id.control_mu_header_details_font) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUHeader.detailWeight = if (isChecked) Typeface.BOLD else Typeface.NORMAL
        }

        // Details size
        view.findViewById<View>(R.id.control_mu_header_details_less).setOnClickListener { mMUHeader.detailSize = mMUHeader.detailSize - 1 }
        view.findViewById<View>(R.id.control_mu_header_details_more).setOnClickListener { mMUHeader.detailSize = mMUHeader.detailSize + 1 }

        // Alignment
        view.findViewById<View>(R.id.control_mu_header_left).setOnClickListener { mMUHeader.alignment = Gravity.START }
        view.findViewById<View>(R.id.control_mu_header_right).setOnClickListener { mMUHeader.alignment = Gravity.END }
        view.findViewById<View>(R.id.control_mu_header_center).setOnClickListener { mMUHeader.alignment = Gravity.CENTER }

        // Vertical
        view.findViewById<View>(R.id.control_mu_header_vertical_less).setOnClickListener { mMUHeader.verticalSpacing = mMUHeader.verticalSpacing - 10 }
        view.findViewById<View>(R.id.control_mu_header_vertical_more).setOnClickListener { mMUHeader.verticalSpacing = mMUHeader.verticalSpacing + 10 }

        //mMUHeader.titleFontStyle = R.style.Header

        // RAZ
        view.findViewById<View>(R.id.control_mu_header_raz).setOnClickListener {
            (view.findViewById<View>(R.id.control_mu_header_details_font) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_header_details_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_header_title_font) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_header_title_color) as Switch).isChecked = false
            mMUHeader.titleSize = titleSize
            mMUHeader.detailSize = detailSize
            mMUHeader.verticalSpacing = verticalSpace.toInt()
            mMUHeader.alignment = alignment.toInt()
        }
    }
}
