package kz.ali.alarmclock.ui.presentation.createalarm.layoutmanager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class ScaleCenterItemLayoutManager : LinearLayoutManager {

    private val mShrinkAmount = 0.15f
    private val mShrinkDistance = 0.9f

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) :
            super(context, orientation, reverseLayout)

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        lp?.width = width / 3
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: RecyclerView.State?): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1: Float = mShrinkDistance * midpoint
            val s0 = 1f
            val s1: Float = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child: View? = getChildAt(i)
                val childMidpoint = (getDecoratedBottom(child!!) + getDecoratedTop(child)) / 2f
                val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.setScaleX(scale)
                child.setScaleY(scale)
            }
            scrolled
        } else {
            0
        }
    }
}