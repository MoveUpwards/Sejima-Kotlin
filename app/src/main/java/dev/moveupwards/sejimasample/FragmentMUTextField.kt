package dev.moveupwards.sejimasample

import android.graphics.Color
import android.view.View

import dev.moveupwards.sejima.MUTextField

internal class FragmentMUTextField : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_textfield
    }

    override fun title(): String {
        return "MUTextField"
    }

    override fun initView(view: View) {
        val mMUTextField: MUTextField = view.findViewById(R.id.mu_textfield) ?: return // guard that we find it.
        mMUTextField.label = "TextField label"
        mMUTextField.labelColor = Color.BLACK
        mMUTextField.placeHolderText = "TextField placeholder"
    }
}
