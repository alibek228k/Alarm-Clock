package kz.ali.alarmclock.ui.presentation.createalarm.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kz.ali.alarmclock.R

class NumbersPickerItemDecorator(
    context: Context,
    private val verticalSpacing: Int = context.resources.getDimensionPixelOffset(R.dimen.time_element_item),
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.top = verticalSpacing
        outRect.bottom = verticalSpacing
    }
}