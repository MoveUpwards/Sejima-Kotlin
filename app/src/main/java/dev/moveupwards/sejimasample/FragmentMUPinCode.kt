package dev.moveupwards.sejimasample

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView

import dev.moveupwards.sejima.MUPinCode

internal class FragmentMUPinCode : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_pincode
    }

    override fun title(): String {
        return "MUPinCode"
    }

    override fun initView(view: View) {
        val mMUPinCode: MUPinCode = view.findViewById(R.id.mu_pincode) ?: return // guard that we find it.

        // Length
        view.findViewById<View>(R.id.control_mu_pincode_less).setOnClickListener { mMUPinCode.count = mMUPinCode.count - 1 }
        view.findViewById<View>(R.id.control_mu_pincode_more).setOnClickListener { mMUPinCode.count = mMUPinCode.count + 1 }

        // Corner radius
        view.findViewById<View>(R.id.control_mu_pincode_corner_less).setOnClickListener { mMUPinCode.cellCornerRadius = mMUPinCode.cellCornerRadius - 5 }
        view.findViewById<View>(R.id.control_mu_pincode_corner_more).setOnClickListener { mMUPinCode.cellCornerRadius = mMUPinCode.cellCornerRadius + 5 }

        // Cell space
        view.findViewById<View>(R.id.control_mu_pincode_space_less).setOnClickListener { mMUPinCode.cellSpacing = mMUPinCode.cellSpacing - 5 }
        view.findViewById<View>(R.id.control_mu_pincode_space_more).setOnClickListener { mMUPinCode.cellSpacing = mMUPinCode.cellSpacing + 5 }

        val tvCode = view.findViewById<TextView>(R.id.control_mu_pincode_tv_code)
        view.findViewById<View>(R.id.control_mu_pincode_btn_get_code).setOnClickListener { tvCode.text = mMUPinCode.code }

        // Set Code
        val etCode = view.findViewById<EditText>(R.id.control_mu_pincode_et_code)
        view.findViewById<View>(R.id.control_mu_pincode_btn_set_code).setOnClickListener { mMUPinCode.code = if (TextUtils.isEmpty(etCode.text)) "" else etCode.text.toString() }
    }
}
