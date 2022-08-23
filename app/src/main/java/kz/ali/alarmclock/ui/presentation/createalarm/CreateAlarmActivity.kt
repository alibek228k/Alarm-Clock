package kz.ali.alarmclock.ui.presentation.createalarm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.createalarm.itemdecoration.NumbersPickerItemDecorator

class CreateAlarmActivity : AppCompatActivity(), Observer {

    //UI components
    private var cancelButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var hours: ViewPager2? = null
    private var minutes: ViewPager2? = null
    private var mondayTextView: MaterialTextView? = null
    private var tuesdayTextView: MaterialTextView? = null
    private var wednesdayTextView: MaterialTextView? = null
    private var thursdayTextView: MaterialTextView? = null
    private var fridayTextView: MaterialTextView? = null
    private var saturdayTextView: MaterialTextView? = null
    private var sundayTextView: MaterialTextView? = null
    private var mondayCheckbox: MaterialCheckBox? = null
    private var tuesdayCheckbox: MaterialCheckBox? = null
    private var wednesdayCheckbox: MaterialCheckBox? = null
    private var thursdayCheckbox: MaterialCheckBox? = null
    private var fridayCheckbox: MaterialCheckBox? = null
    private var saturdayCheckbox: MaterialCheckBox? = null
    private var sundayCheckbox: MaterialCheckBox? = null
    private var daysText: MaterialTextView? = null

    //RecyclerView adapters

    private var adapterForHours: NumbersPickerAdapter? = NumbersPickerAdapter(24)
    private var adapterForMinutes: NumbersPickerAdapter? = NumbersPickerAdapter(60)

    //Alarm model
    private var alarm: Alarm? = null
    private var selectedDays = mutableListOf<Alarm.Days>()

    //Observer pattern
    private val observer: Observer? = null

    companion object {
        const val KEY = "KEY"
        fun newInstance(context: Context, alarm: Alarm? = null): Intent {
            val intent = Intent(context, CreateAlarmActivity::class.java)
            if (alarm != null) intent.putExtra(KEY, alarm)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        //inflate Views
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        hours = findViewById(R.id.hours)
        minutes = findViewById(R.id.minutes)
        mondayTextView = findViewById(R.id.mondayTextView)
        tuesdayTextView = findViewById(R.id.tuesdayTextView)
        wednesdayTextView = findViewById(R.id.wednesdayTextView)
        thursdayTextView = findViewById(R.id.thursdayTextView)
        fridayTextView = findViewById(R.id.fridayTextView)
        saturdayTextView = findViewById(R.id.saturdayTextView)
        sundayTextView = findViewById(R.id.sundayTextView)
        mondayCheckbox = findViewById(R.id.mondayCheckbox)
        tuesdayCheckbox = findViewById(R.id.tuesdayCheckbox)
        wednesdayCheckbox = findViewById(R.id.wednesdayCheckbox)
        thursdayCheckbox = findViewById(R.id.thursdayCheckbox)
        fridayCheckbox = findViewById(R.id.fridayCheckbox)
        saturdayCheckbox = findViewById(R.id.saturdayCheckbox)
        sundayCheckbox = findViewById(R.id.sundayCheckbox)
        daysText = findViewById(R.id.daysText)

        alarm = intent.getParcelableExtra(KEY)


        setupCancelButton()
        setupViewPagers()
        setupAlarmDaysButtons()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViewPagers() {
        hours?.adapter = adapterForHours
        minutes?.adapter = adapterForMinutes
        hours?.addItemDecoration(NumbersPickerItemDecorator(this))
        minutes?.addItemDecoration(NumbersPickerItemDecorator(this))
        hours?.currentItem = 1
        minutes?.currentItem = 1
        onNumberPickerChangeCallback(26, hours)
        onNumberPickerChangeCallback(62, minutes)
    }

    private fun setupCancelButton() {
        cancelButton?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onNumberPickerChangeCallback(listSize: Int, pager: ViewPager2?) {
        pager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    when (pager.currentItem) {
                        listSize - 1 -> pager.setCurrentItem(1, false)
                        0 -> pager.setCurrentItem(listSize - 2, false)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position != 0 && position != listSize - 1) {

                }
            }
        })
    }

    private fun setupAlarmDaysButtons() {
        if (alarm != null) {
            selectedDays = alarm?.days?.toMutableList() ?: mutableListOf()
            val size = alarm?.days?.size ?: 0
            val days = alarm?.days
            for (i in 0 until size) {
                when (days?.get(i)) {
                    Alarm.Days.MONDAY -> {
                        mondayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        mondayCheckbox?.isChecked = true
                    }
                    Alarm.Days.TUESDAY -> {
                        tuesdayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        tuesdayCheckbox?.isChecked = true
                    }
                    Alarm.Days.WEDNESDAY -> {
                        wednesdayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        wednesdayCheckbox?.isChecked = true
                    }
                    Alarm.Days.THURSDAY -> {
                        thursdayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        thursdayCheckbox?.isChecked = true
                    }
                    Alarm.Days.FRIDAY -> {
                        fridayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        fridayCheckbox?.isChecked = true
                    }
                    Alarm.Days.SATURDAY -> {
                        saturdayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        saturdayCheckbox?.isChecked = true
                    }
                    Alarm.Days.SUNDAY -> {
                        sundayTextView?.setTextColor(resources.getColor(R.color.purple_500, null))
                        sundayCheckbox?.isChecked = true
                    }
                    else -> {
                        //ignore
                    }
                }
            }
        }
        setupClickListenersForButtons()

        onCheckedChanged(mondayCheckbox, mondayTextView)
        onCheckedChanged(tuesdayCheckbox, tuesdayTextView)
        onCheckedChanged(wednesdayCheckbox, wednesdayTextView)
        onCheckedChanged(thursdayCheckbox, thursdayTextView)
        onCheckedChanged(fridayCheckbox, fridayTextView)
        onCheckedChanged(saturdayCheckbox, saturdayTextView)
        onCheckedChanged(sundayCheckbox, sundayTextView, true)
    }

    private fun onCheckedChanged(
        checkBox: MaterialCheckBox?,
        textView: MaterialTextView?,
        isSunday: Boolean = false,
    ) {
        checkBox?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textView?.setTextColor(resources.getColor(R.color.purple_500, null))
            } else {
                if (isSunday) textView?.setTextColor(resources.getColor(R.color.red,
                    null)) else textView?.setTextColor(resources.getColor(R.color.fontColor, null))
            }
        }
    }

    private fun setupClickListenersForButtons() {
//        mondayTextView?.setOnClickListener {
//            mondayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.MONDAY)
//        }
//        tuesdayTextView?.setOnClickListener {
//            tuesdayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.TUESDAY)
//        }
//        wednesdayTextView?.setOnClickListener {
//            wednesdayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.WEDNESDAY)
//        }
//        thursdayTextView?.setOnClickListener {
//            thursdayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.THURSDAY)
//        }
//        fridayTextView?.setOnClickListener {
//            fridayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.FRIDAY)
//        }
//        saturdayTextView?.setOnClickListener {
//            saturdayTextView = MaterialButton(ContextThemeWrapper(this, R.style.ButtonWithoutCircle))
//            observer?.notify(Alarm.Days.SATURDAY)
//        }
//        sundayTextView?.setOnClickListener {
//            sundayTextView =
//                MaterialButton(ContextThemeWrapper(this, R.style.SundayButtonWithoutCircle))
//            observer?.notify(Alarm.Days.SUNDAY)
//        }
    }

    override fun notify(day: Alarm.Days) {
        if (!selectedDays.contains(day)) {
            selectedDays.add(day)
        }
        if (selectedDays.size == 7) {
            daysText?.text = getString(R.string.every_day)
        }
    }
}