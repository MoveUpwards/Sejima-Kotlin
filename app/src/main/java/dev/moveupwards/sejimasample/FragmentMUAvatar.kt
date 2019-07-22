package dev.moveupwards.sejimasample

import android.graphics.Color
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

import dev.moveupwards.sejima.MUAvatar

internal class FragmentMUAvatar : FragmentDefault() {

    override fun layoutId(): Int {
        return R.layout.fragment_mu_avatar
    }

    override fun title(): String {
        return "MUAvatar"
    }

    override fun initView(view: View) {
        val mMUAvatar: MUAvatar = view.findViewById(R.id.mu_avatar) ?: return // guard that we find it.

        val cornerRadius = mMUAvatar.cornerRadius
        val borderWidth = mMUAvatar.borderWidth

        mMUAvatar.clickListener = { Toast.makeText(context, "Click on avatar", Toast.LENGTH_SHORT).show() }

        //Border type
        (view.findViewById<View>(R.id.control_mu_avatar_shape) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUAvatar.borderType = if (isChecked) MUAvatar.ROUND_BORDER else MUAvatar.SQUARE_BORDER
            view.findViewById<View>(R.id.control_mu_avatar_radius_less).isEnabled = !isChecked
            view.findViewById<View>(R.id.control_mu_avatar_radius_more).isEnabled = !isChecked
        }

        // Drawable
        (view.findViewById<View>(R.id.control_mu_avatar_image) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUAvatar.image = ResourcesCompat.getDrawable(resources, if (isChecked) R.drawable.ic_launcher_background else R.drawable.ic_menu_manage, null)
        }

        // Background color
        (view.findViewById<View>(R.id.control_mu_avatar_bkg) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUAvatar.bkgColor = if (isChecked) ContextCompat.getColor(view.context, R.color.colorAccent) else Color.TRANSPARENT
        }

        // Border color
        (view.findViewById<View>(R.id.control_mu_avatar_border_color) as Switch).setOnCheckedChangeListener { _, isChecked ->
            mMUAvatar.borderColor = ContextCompat.getColor(view.context, if (isChecked) R.color.colorAccent else R.color.colorPrimaryDark)
        }

        // Corner radius
        view.findViewById<View>(R.id.control_mu_avatar_radius_less).setOnClickListener { mMUAvatar.cornerRadius = mMUAvatar.cornerRadius - 10 }
        view.findViewById<View>(R.id.control_mu_avatar_radius_more).setOnClickListener { mMUAvatar.cornerRadius = mMUAvatar.cornerRadius + 10 }

        // Border width
        view.findViewById<View>(R.id.control_mu_avatar_border_less).setOnClickListener { mMUAvatar.borderWidth = mMUAvatar.borderWidth - 1 }
        view.findViewById<View>(R.id.control_mu_avatar_border_more).setOnClickListener { mMUAvatar.borderWidth = mMUAvatar.borderWidth + 1 }

        view.findViewById<View>(R.id.control_mu_avatar_raz).setOnClickListener {
            (view.findViewById<View>(R.id.control_mu_avatar_border_color) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_avatar_bkg) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_avatar_image) as Switch).isChecked = false
            (view.findViewById<View>(R.id.control_mu_avatar_shape) as Switch).isChecked = false
            mMUAvatar.borderWidth = borderWidth
            mMUAvatar.cornerRadius = cornerRadius
        }
    }
}
