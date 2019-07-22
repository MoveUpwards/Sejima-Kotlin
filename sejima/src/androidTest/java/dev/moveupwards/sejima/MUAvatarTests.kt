package dev.moveupwards.sejima

import android.content.Context
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

@RunWith(JUnit4::class)
class MUAvatarTests {


    private var mMUAvatar: MUAvatar? = null
    private var mContext: Context? = null

    @Before
    fun setUp() {

        mContext = ApplicationProvider.getApplicationContext()
        mMUAvatar = MUAvatar(mContext!!)
        assertNotNull(mMUAvatar)
    }

    @Test
    fun defaultValues() {

        // Bkg color
        assertEquals(mMUAvatar?.bkgColor, Color.TRANSPARENT)
        // Border color
        assertEquals(mMUAvatar?.borderColor, Color.BLACK)
        // Border width
        assertEquals(mMUAvatar?.borderWidth, MUAvatar.DEFAULT_BORDER_WIDTH_IN_DP)
        // Border type
        assertEquals(mMUAvatar?.borderType, MUAvatar.SQUARE_BORDER)
        // Corner radius
        assertEquals(mMUAvatar?.borderType, 0)
        // Image
        assertNull(mMUAvatar?.drawable)
        // Placeholder image
        assertNull(mMUAvatar?.placeholderImage)
        // Listener
        assertNull(mMUAvatar?.clickListener)
    }

    @Test
    fun customValues() {
        // Bkg color
        mMUAvatar?.bkgColor = Color.BLACK
        assertEquals(mMUAvatar?.bkgColor, Color.BLACK)
        // Border color
        mMUAvatar?.borderColor = Color.RED
        assertEquals(mMUAvatar?.borderColor, Color.RED)
        // Border width
        mMUAvatar?.borderWidth = 12f
        assertEquals(mMUAvatar?.borderWidth, 12f)
        // Corner radius
        mMUAvatar?.cornerRadius = 15f
        assertEquals(mMUAvatar?.cornerRadius, 15f)
        // Border type
        mMUAvatar?.borderType = MUAvatar.ROUND_BORDER
        assertEquals(mMUAvatar?.borderType, MUAvatar.ROUND_BORDER)
        // Listener
        mMUAvatar?.clickListener = { _ -> }
        assertNotNull(mMUAvatar?.clickListener)

        assertNotNull(mContext)
        val context = mContext ?: return
/*
        // Image
        var drawable = ResourcesCompat.getDrawable(context.resources, R.drawable.avatar, null)
        mMUAvatar?.image = drawable
        assertEquals(mMUAvatar?.image, drawable)
        // Placeholder image
        drawable = ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null)
        mMUAvatar?.placeholderImage = drawable
        assertEquals(mMUAvatar?.placeholderImage, drawable)
*/
    }
/*
    @Test
    fun nullImageWillBeReplacedByPlaceholder() {
        assertNotNull(mContext)
        val context = mContext ?: return

        val placeholder = ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null)
        mMUAvatar?.placeholderImage = placeholder
        mMUAvatar?.image = null
        assertEquals(mMUAvatar?.image, placeholder)
    }*/
}
