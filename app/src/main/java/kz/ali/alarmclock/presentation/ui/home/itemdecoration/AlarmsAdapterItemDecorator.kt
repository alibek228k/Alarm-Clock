package kz.ali.alarmclock.presentation.ui.home.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kz.ali.alarmclock.R

class AlarmsAdapterItemDecorator(
    context: Context,
    private val alarmsVerticalSpacing: Int = context.resources.getDimensionPixelOffset(R.dimen.alarms_vertical_spacing),
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.top = alarmsVerticalSpacing
        outRect.bottom = alarmsVerticalSpacing
    }
}