package dev.moveupwards.sejima

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View

import kotlin.math.max
import kotlin.math.min

internal interface MUViewHelper {

    fun pixelsToDensity(dp: DisplayMetrics, density: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, density, dp)
    }

    //fun densityToPixels(dp: DisplayMetrics, pixels: Float): Float {
    //    return pixels * dp.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    //}

    /**
     * Normalize alpha value between 0 and 1
     * @param alpha the alpha value to check
     * @return the normalized value of alpha
     */
    fun normalizeAlphaValue(alpha: Float): Float {
        return normalizeFloatValue(alpha, 0f, 1f)
    }

    /**
     * Normalize multiplier value between 0 and 1
     * @param multiplier the alpha value to check
     * @return the normalized value of multiplier
     */
    fun normalizeMultiplierValue(multiplier: Float): Float {
        return normalizeFloatValue(multiplier, 0f, 1f)
    }

    fun normalizeIntValue(valueToNormalize: Int, minValue: Int, maxValue: Int): Int {
        var value = valueToNormalize
        value = max(minValue, value)
        value = min(value, maxValue)
        return if (value > minValue) value else minValue
    }

    fun normalizeFloatValue(valueToNormalize: Float, minValue: Float, maxValue: Float): Float {
        var value = valueToNormalize
        value = max(minValue, value)
        value = min(value, maxValue)
        return if (value > minValue) value else minValue
    }

    /**
     * Check if the given resource id exists in resources
     * @param resources the resources which contains the given id
     * @param resId the resource id to check
     * @return true if the resource exists, false otherwise
     */
    fun checkResource(resources: Resources?, resId: Int): Boolean {
        var result = false
        try {
            result = (resources?.getResourceName(resId) != null)
        } catch (e: Resources.NotFoundException) {
            //
        }
        return result
    }


    /**
     * Apply given radius to the view
     * @param cornerRadius the corner radius to apply
     * @param backgroundColor the background color of the view
     * @param view the view to be round-cornered
     */
    fun applyRoundCornerToView(cornerRadius: Float, backgroundColor: Int, view: View) {
        val borderDrawable = GradientDrawable()
        borderDrawable.cornerRadius = cornerRadius
        borderDrawable.setColor(backgroundColor)
        view.background = borderDrawable
    }
}
