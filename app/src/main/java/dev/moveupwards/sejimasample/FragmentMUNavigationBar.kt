package dev.moveupwards.sejimasample

import android.graphics.Color
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

import dev.moveupwards.sejima.MUNavigationBar

internal class FragmentMUNavigationBar : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_navigationbar
    }

    override fun title(): String {
        return "MUNavigationBar"
    }

    override fun initView(view: View) {
        val mMUNavigationBar: MUNavigationBar = view.findViewById(R.id.mu_navbar) ?: return // guard that we find it.

        val sepWidth = mMUNavigationBar.separatorWidth
        val sepMul = mMUNavigationBar.separatorMultiplier

        mMUNavigationBar.listener = object : MUNavigationBar.MUNavigationBarListener {
            override fun clickOnLeftButton(muNavigationBar: MUNavigationBar) {
                Toast.makeText(context, "Click on left button", Toast.LENGTH_SHORT).show()
            }

            override fun clickOnRightButton(muNavigationBar: MUNavigationBar) {
                Toast.makeText(context, "Click on right button", Toast.LENGTH_SHORT).show()
            }
        }

        // Bkg color
        (view.findViewById<View>(R.id.control_mu_navbar_bkg_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUNavigationBar.bkgColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else R.color.colorPrimary
        }

        // Drawable
        (view.findViewById<View>(R.id.control_mu_navbar_image) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUNavigationBar.imgDrawable = ResourcesCompat.getDrawable(resources, if (isChecked) R.drawable.ic_launcher_background else R.drawable.ic_menu_manage, null)
        }

        // Separator color
        (view.findViewById<View>(R.id.control_mu_navbar_separator_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUNavigationBar.separatorColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorPrimaryDark) else Color.BLACK
        }

        // Separator width
        view.findViewById<View>(R.id.control_mu_navbar_separator_less).setOnClickListener { mMUNavigationBar.separatorWidth = mMUNavigationBar.separatorWidth - 1 }
        view.findViewById<View>(R.id.control_mu_navbar_separator_more).setOnClickListener { mMUNavigationBar.separatorWidth = mMUNavigationBar.separatorWidth + 1 }

        // Separator width
        view.findViewById<View>(R.id.control_mu_navbar_separator_less).setOnClickListener { mMUNavigationBar.separatorWidth = mMUNavigationBar.separatorWidth - 1 }
        view.findViewById<View>(R.id.control_mu_navbar_separator_more).setOnClickListener { mMUNavigationBar.separatorWidth = mMUNavigationBar.separatorWidth + 1 }

        // Separator multiplier
        view.findViewById<View>(R.id.control_mu_navbar_separator_multi_less).setOnClickListener { mMUNavigationBar.separatorMultiplier = mMUNavigationBar.separatorMultiplier - 0.1f }
        view.findViewById<View>(R.id.control_mu_navbar_separator_multi_more).setOnClickListener { mMUNavigationBar.separatorMultiplier = mMUNavigationBar.separatorMultiplier + 0.1f }

        // RAZ
        view.findViewById<View>(R.id.control_mu_topbar_raz).setOnClickListener {
            (view.findViewById<View>(R.id.control_mu_navbar_bkg_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_navbar_image) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_navbar_separator_color) as Switch).isChecked = false
            mMUNavigationBar.separatorWidth = sepWidth
            mMUNavigationBar.separatorMultiplier = sepMul
        }
    }
}
