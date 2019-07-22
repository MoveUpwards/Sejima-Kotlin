package dev.moveupwards.sejimasample

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

import dev.moveupwards.sejima.MUCard

internal class FragmentMUCard : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_card
    }

    override fun title(): String {
        return "MUCard"
    }

    override fun initView(view: View) {
        val mMUCard: MUCard = view.findViewById(R.id.mu_card) ?: return // guard that we find it.

        val tv = TextView(context)
        tv.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
        tv.text = "This is just a test"
        mMUCard.addContentView(tv)
    }
}
