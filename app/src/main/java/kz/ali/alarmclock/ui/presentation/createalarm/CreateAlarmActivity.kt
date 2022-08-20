package kz.ali.alarmclock.ui.presentation.createalarm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.createalarm.itemdecoration.NumbersPickerItemDecorator

class CreateAlarmActivity : AppCompatActivity() {

    //UI components
    private var cancelButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var hours: ViewPager2? = null
    private var minutes: ViewPager2? = null
    private var adapterForHours: NumbersPickerAdapter? = NumbersPickerAdapter( 24)
    private var adapterForMinutes: NumbersPickerAdapter? = NumbersPickerAdapter( 60)

    companion object {
        fun newInstance(context: Context, alarm: Alarm? = null): Intent {
            return Intent(context, CreateAlarmActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        hours = findViewById(R.id.hours)
        minutes = findViewById(R.id.minutes)

        setupCancelButton()
        setupViewPagers()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViewPagers(){
        hours?.adapter = adapterForHours
        minutes?.adapter = adapterForMinutes
        hours?.addItemDecoration(NumbersPickerItemDecorator(this))
        minutes?.addItemDecoration(NumbersPickerItemDecorator(this))
        hours?.currentItem = 1
        minutes?.currentItem = 1
        onNumberPickerChangeCallback(26, hours)
        onNumberPickerChangeCallback(62, minutes)

        hours?.setOnTouchListener{_, event ->
            handleOnTouchEvent(event)
        }
    }

    private fun setupCancelButton(){
        cancelButton?.setOnClickListener{
            onBackPressed()
        }
    }

    private fun onNumberPickerChangeCallback(listSize: Int, pager: ViewPager2?){
        pager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                println("State changed $listSize")

                if (state == ViewPager2.SCROLL_STATE_IDLE){
                    when (pager.currentItem){
                        listSize -1 -> pager.setCurrentItem(1, false)
                        0 -> pager.setCurrentItem(listSize-2, false)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position != 0 && position != listSize -1){

                }
            }
        })
    }

    private fun handleOnTouchEvent(event: MotionEvent): Boolean {
        var lastValue = 0f
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastValue = event.x
                hours?.beginFakeDrag()
            }

            MotionEvent.ACTION_MOVE -> {
                val value = event.x
                val delta = value - lastValue
                hours?.fakeDragBy(delta)
                lastValue = value
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                hours?.endFakeDrag()
            }
        }
        return true
    }
}