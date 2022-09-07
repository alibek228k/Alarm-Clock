package kz.ali.alarmclock.ui.presentation.createalarm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.alarmsound.AlarmSoundActivity
import kz.ali.alarmclock.ui.presentation.createalarm.itemdecoration.NumbersPickerItemDecorator
import kz.ali.alarmclock.ui.presentation.createalarm.vm.CreateAlarmViewModel
import kz.ali.alarmclock.ui.presentation.snooze.SnoozeActivity
import kz.ali.alarmclock.ui.presentation.vibration.VibrationActivity

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
    private var snoozeButton: RelativeLayout? = null
    private var alarmSoundButton: RelativeLayout? = null
    private var vibrationVibration: RelativeLayout? = null

    //RecyclerView adapters

    private var adapterForHours: NumbersPickerAdapter? = NumbersPickerAdapter(24)
    private var adapterForMinutes: NumbersPickerAdapter? = NumbersPickerAdapter(60)

    //Alarm model
    private var alarm: Alarm? = null
    private var selectedDays = mutableListOf<Alarm.Days>()

    //Observer pattern
    private var observer: ObserverImp? = null

    //ViewModel
    private var viewModel: CreateAlarmViewModel? = null

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
        alarmSoundButton = findViewById(R.id.alarmSoundButton)
        vibrationVibration = findViewById(R.id.vibrationButton)
        snoozeButton = findViewById(R.id.snoozeButton)

        alarm = intent.getParcelableExtra(KEY)
        observer = ObserverImp(this)


        setupCancelButton()
        setupViewPagers()
        setupAlarmDaysButtons()
        setupViews()

    }

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
                        wednesdayTextView?.setTextColor(
                            resources.getColor(
                                R.color.purple_500,
                                null
                            )
                        )
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

        observer?.useNotification()
        onCheckedChanged(mondayCheckbox, mondayTextView, dayOfWeek = Alarm.Days.MONDAY)
        onCheckedChanged(tuesdayCheckbox, tuesdayTextView, dayOfWeek = Alarm.Days.TUESDAY)
        onCheckedChanged(wednesdayCheckbox, wednesdayTextView, dayOfWeek = Alarm.Days.WEDNESDAY)
        onCheckedChanged(thursdayCheckbox, thursdayTextView, dayOfWeek = Alarm.Days.THURSDAY)
        onCheckedChanged(fridayCheckbox, fridayTextView, dayOfWeek = Alarm.Days.FRIDAY)
        onCheckedChanged(saturdayCheckbox, saturdayTextView, dayOfWeek = Alarm.Days.SATURDAY)
        onCheckedChanged(sundayCheckbox, sundayTextView, true, dayOfWeek = Alarm.Days.SUNDAY)
    }

    private fun onCheckedChanged(
        checkBox: MaterialCheckBox?,
        textView: MaterialTextView?,
        isSunday: Boolean = false,
        dayOfWeek: Alarm.Days,
    ) {
        checkBox?.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                textView?.setTextColor(resources.getColor(R.color.purple_500, null))
                observer?.useNotification(dayOfWeek, true)
            } else {
                if (isSunday) textView?.setTextColor(
                    resources.getColor(
                        R.color.red,
                        null
                    )
                ) else textView?.setTextColor(resources.getColor(R.color.fontColor, null))
                observer?.useNotification(dayOfWeek)
            }
        }
    }

    private fun String.removeLastCharacter(): String {
        var str = this
        if (str[str.length - 1] == ',') {
            str = str.dropLast(1)
            return str
        }
        return str
    }

    private fun setupViews() {
        alarmSoundButton?.setOnClickListener {
            startActivity(AlarmSoundActivity.newInstance(this))
        }
        snoozeButton?.setOnClickListener {
            startActivity(SnoozeActivity.newInstance(this))
        }
        vibrationVibration?.setOnClickListener {
            startActivity(VibrationActivity.newInstance(this))
        }
    }

    private fun setupSaveButton() {
        saveButton?.setOnClickListener {

        }
    }

    override fun notify(day: Alarm.Days?, shouldAdd: Boolean) {
        if (day != null) {
            if (shouldAdd) {
                if (!selectedDays.contains(day)) {
                    selectedDays.add(day)
                }
            } else {
                if (selectedDays.contains(day)) {
                    selectedDays.remove(day)
                }
            }
        }
        var every = "Every"
        when (selectedDays.size) {
            7 -> {
                daysText?.text = "$every day"
            }
            0 -> {
                daysText?.text = getString(R.string.alarms_are_off)
            }
            else -> {
                if (selectedDays.contains(Alarm.Days.MONDAY)) {
                    every = "$every Mon,"
                }
                if (selectedDays.contains(Alarm.Days.TUESDAY)) {
                    every = "$every Tue,"
                }
                if (selectedDays.contains(Alarm.Days.WEDNESDAY)) {
                    every = "$every Wed,"
                }
                if (selectedDays.contains(Alarm.Days.THURSDAY)) {
                    every = "$every Thu,"
                }
                if (selectedDays.contains(Alarm.Days.FRIDAY)) {
                    every = "$every Fri,"
                }
                if (selectedDays.contains(Alarm.Days.SATURDAY)) {
                    every = "$every Sat,"
                }
                if (selectedDays.contains(Alarm.Days.SUNDAY)) {
                    every = "$every Sun"
                }
                every = every.removeLastCharacter()
                daysText?.text = every
            }
        }
    }

}