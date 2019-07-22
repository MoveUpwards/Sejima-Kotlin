package dev.moveupwards.sejima

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import java.util.LinkedList
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlin.math.max

private const val UPDATE_THRESHOLD = 0.55f

/*
    Created by Antoine RICHE on 2019/04/01.
    Converted in Kotlin by Damien on 16/07/2019.
 */
class MUHorizontalPager : ViewPager {

    /**
     * The current index of the view pager
     */
    private var mCurrentIndex = 0
    /**
     * The horizontal margins used for subviews
     */
    /**
     * Get the current horizontal margins
     * @return the horizontal margins in dp
     */
    /**
     * Update the current horizontal margins
     * @property horizontalMargins the new horizontal margins in dp
     */
    var horizontalMargins = 0f
        set(horizontalMargins) {
            field = max(horizontalMargins, 0f)
            adapter?.updateMargins(horizontalMargins)
        }
    /**
     * The listener for scroll events
     */
    /**
     * Get the listener attached to the view pager
     * @return the current listener attached, null if not
     */
    /**
     * Register a listener for the scrolling events
     * @property slideListener the listener to handle user scrolling
     */
    var slideListener: ((MUHorizontalPager, Int) -> Unit)? = null
    /**
     * The page adapter attached to the view pager
     */
    private var mMyPagerAdapter: MyPagerAdapter? = null
    /**
     * The page control attached to the view pager
     */
    var muPageControl: MUPageControl? = null
        set(MUPageControl) {
            field = MUPageControl
            if (muPageControl != null) {
                muPageControl?.numberPages = pageCount
                muPageControl?.listener = { _, index ->
                    setCurrentIndex(index, false)
                }
                muPageControl?.currentPosition = mCurrentIndex
            }
        }

    /**
     * Get the number of views attached to the ViewPager
     * @return the number of views
     */
    val pageCount: Int
        get() = mMyPagerAdapter?.count ?: 0

    /**
     * Get the index of the current page
     * @return the position of the current page
     */
    /**
     * Go to the given position without auto-scrolling
     * @property currentIndex the index of the page to display
     */
    var currentIndex: Int
        get() = mCurrentIndex
        set(currentIndex) = setCurrentIndex(currentIndex, false)

    private inner class MyOnPageChangeListener(private val horizontalPager: MUHorizontalPager) : OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            mCurrentIndex = position
            muPageControl?.currentPosition = position
            slideListener?.invoke(horizontalPager, position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }


    /**
     * Default constructor
     * @param context the view context
     */
    constructor(context: Context) : super(context) {
        init(context)
    }

    /**
     * Constructor with attributes
     * @param context the view context
     * @param attrs the XML attributes for the view
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        mMyPagerAdapter = MyPagerAdapter()
        setAdapter(mMyPagerAdapter)

        if (isInEditMode) {
            val tv = TextView(context)
            tv.text = context.getString(R.string.app_name)

            val b = Button(context)
            b.text = context.getString(R.string.app_name)

            val muHeader = MUHeader(context)
//            muHeader.title = context.getString(R.string.title) // TODO: Should be ""
//            muHeader.detail = context.getString(R.string.detail) // TODO: Should be ""
            addViews(arrayOf(tv, b, muHeader), 10f)
            currentIndex = 0
        }

        addOnPageChangeListener(MyOnPageChangeListener(this))
    }

    fun addViews(views: Array<View>, margins: Float) {
        for (v in views) {
            addSubView(v, margins)
        }
    }

    private fun addSubView(view: View, margins: Float) {
        view.setPadding(margins.toInt(), margins.toInt(), margins.toInt(), margins.toInt())
        mMyPagerAdapter?.addView(view)
        muPageControl?.numberPages = mMyPagerAdapter?.count ?: 0
    }

    /**
     * Get the adapter attached to the ViewPager
     * @return the custom [MyPagerAdapter] adapter
     */
    override fun getAdapter(): MyPagerAdapter? {
        return mMyPagerAdapter
    }

    fun setAdapter(adapter: MyPagerAdapter?) {
        super.setAdapter(adapter)
        mMyPagerAdapter = adapter
    }

    /**
     * Go to the given position
     * @param currentIndex the index of the page to display
     * @param autoScroll a boolean value enabling/disabling animation
     */
    fun setCurrentIndex(currentIndex: Int, autoScroll: Boolean) {
        if (currentIndex in 0 until pageCount && currentIndex != mCurrentIndex) {
            mCurrentIndex = currentIndex
            setCurrentItem(mCurrentIndex, autoScroll)

            muPageControl?.currentPosition = mCurrentIndex
        }
    }

    /**
     * A custom adapter extending PagerAdapter
     */
    class MyPagerAdapter
    /**
     * Constructor of the custom adapter
     */
    internal constructor() : PagerAdapter() {

        /**
         * The list containing the current pages attached to the adapter
         */
        private val mViews: LinkedList<View> = LinkedList()

        init {
        }

        internal fun addView(view: View) {
            mViews.add(view)
            notifyDataSetChanged()
        }

        internal fun updateMargins(newMargins: Float) {
            for (v in mViews) {
                v.setPadding(newMargins.toInt(), newMargins.toInt(), newMargins.toInt(), newMargins.toInt())
            }
            notifyDataSetChanged()
        }

        /**
         * Get the number of pages
         * @return the page count
         */
        override fun getCount(): Int {
            return mViews.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            collection.addView(mViews[position])
            return mViews[position]
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }
    }
}
