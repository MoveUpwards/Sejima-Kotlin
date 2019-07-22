package dev.moveupwards.sejimasample

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import java.util.Locale

import dev.moveupwards.sejima.MUHorizontalPager
import dev.moveupwards.sejima.MUPageControl

internal class FragmentMUHorizontalPager : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_horizontalpager
    }

    override fun title(): String {
        return "MUHorizontalPager"
    }

    override fun initView(view: View) {
        val mMUHorizontalPager: MUHorizontalPager = view.findViewById(R.id.mu_horizontalpager) ?: return // guard that we find it.

        // Navigation
        view.findViewById<View>(R.id.control_mu_horizontalpager_next).setOnClickListener { mMUHorizontalPager.currentIndex = mMUHorizontalPager.currentIndex + 1 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_previous).setOnClickListener { mMUHorizontalPager.currentIndex = mMUHorizontalPager.currentIndex - 1 }

        // Horizontal margins
        view.findViewById<View>(R.id.control_mu_horizontalpager_margin_less).setOnClickListener { mMUHorizontalPager.horizontalMargins = mMUHorizontalPager.horizontalMargins - 1 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_margin_more).setOnClickListener { mMUHorizontalPager.horizontalMargins = mMUHorizontalPager.horizontalMargins + 1 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_margin_more).setOnClickListener { mMUHorizontalPager.horizontalMargins = mMUHorizontalPager.horizontalMargins + 1 }

        // Control radius
        view.findViewById<View>(R.id.control_mu_horizontalpager_corner_more).setOnClickListener { mMUHorizontalPager.muPageControl?.activeElementRadius = mMUHorizontalPager.muPageControl?.activeElementRadius ?: 0 + 5 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_corner_less).setOnClickListener { mMUHorizontalPager.muPageControl?.activeElementRadius = mMUHorizontalPager.muPageControl?.activeElementRadius ?: 0 - 5 }

        // Control padding
        view.findViewById<View>(R.id.control_mu_horizontalpager_control_padding_less).setOnClickListener { mMUHorizontalPager.muPageControl?.elementPadding = mMUHorizontalPager.muPageControl?.elementPadding ?: 0 - 5 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_control_padding_more).setOnClickListener { mMUHorizontalPager.muPageControl?.elementPadding = mMUHorizontalPager.muPageControl?.elementPadding ?: 0 + 5 }

        // Control border color
        (view.findViewById<View>(R.id.control_mu_horizontalpager_border_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUHorizontalPager.muPageControl?.borderColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.BLACK
        }

        // Control border width
        view.findViewById<View>(R.id.control_mu_horizontalpager_control_border_less).setOnClickListener { mMUHorizontalPager.muPageControl?.borderWidth = mMUHorizontalPager.muPageControl?.borderWidth ?: 0 - 1 }

        view.findViewById<View>(R.id.control_mu_horizontalpager_control_border_more).setOnClickListener { mMUHorizontalPager.muPageControl?.borderWidth = mMUHorizontalPager.muPageControl?.borderWidth ?: 0 + 1 }

        // Listener
        (view.findViewById<View>(R.id.control_mu_horizontalpager_listener) as Switch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mMUHorizontalPager.slideListener = { _, toIndex ->
                    Toast.makeText(context, String.format(Locale.FRANCE, "Scroll to page %d", toIndex), Toast.LENGTH_SHORT).show()
                }
            } else {
                mMUHorizontalPager.slideListener = null
            }
        }

        addPages(mMUHorizontalPager, ContextCompat.getColor(view.context, R.color.colorPrimaryDark))

        mMUHorizontalPager.muPageControl = view.findViewById<MUPageControl>(R.id.mu_pagecontrol)

        val leftMargins = mMUHorizontalPager.horizontalMargins
        val borderWidth = mMUHorizontalPager.muPageControl?.borderWidth
        val elementPadding = mMUHorizontalPager.muPageControl?.elementPadding
        val elementRadius = mMUHorizontalPager.muPageControl?.activeElementRadius

        view.findViewById<View>(R.id.control_mu_horizontalpager_raz).setOnClickListener {
            mMUHorizontalPager.horizontalMargins = leftMargins
            mMUHorizontalPager.muPageControl?.borderWidth = borderWidth!! // TODO: Do not understand why we put the value back?
            mMUHorizontalPager.muPageControl?.elementPadding = elementPadding!! // TODO: Do not understand why we put the value back?
            mMUHorizontalPager.muPageControl?.activeElementRadius = elementRadius!! // TODO: Do not understand why we put the value back?
            (view.findViewById<View>(R.id.control_mu_horizontalpager_listener) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_horizontalpager_border_color) as Switch).isChecked = false
        }
    }

    private fun addPages(pager: MUHorizontalPager, bkgColor: Int) {
        var tv = TextView(context)
        tv.text = getString(R.string.app_name)
        pager.addViews(Array(1) { tv }, 12f)

        var ll = LinearLayout(context)
        ll.setBackgroundColor(bkgColor)
        pager.addViews(Array(1) { ll }, 0f)

        tv = TextView(context)
        tv.text = getString(R.string.app_name)
        pager.addViews(Array(1) { tv }, 12f)

        ll = LinearLayout(context)
        ll.setBackgroundColor(bkgColor)
        pager.addViews(Array(1) { ll }, 0f)

        tv = TextView(context)
        tv.text = getString(R.string.app_name)
        pager.addViews(Array(1) { tv }, 12f)
    }
}
