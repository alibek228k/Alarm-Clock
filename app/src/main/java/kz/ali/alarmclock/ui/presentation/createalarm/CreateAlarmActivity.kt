package kz.ali.alarmclock.ui.presentation.createalarm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.alarmsound.ringtonemanager.RingtoneManager
import kz.ali.alarmclock.ui.presentation.createalarm.itemdecoration.NumbersPickerItemDecorator
import kz.ali.alarmclock.ui.presentation.createalarm.recyclerview.NumbersPickerAdapter
import kz.ali.alarmclock.ui.presentation.createalarm.recyclerview.vm.CreateAlarmViewModel
import kz.ali.alarmclock.ui.presentation.snooze.SnoozeActivity
import kz.ali.alarmclock.ui.presentation.vibration.VibrationActivity
import kz.ali.alarmclock.utils.removeLastCharacter
import kotlin.math.abs
import kotlin.math.sign


class CreateAlarmActivity : AppCompatActivity() {

    //UI components
    private var cancelButton: MaterialButton? = null
    private var saveButton: MaterialButton? = null
    private var hours: RecyclerView? = null
    private var minutes: RecyclerView? = null
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
    private var alarmSoundSwitch: SwitchMaterial? = null
    private var vibrationSwitch: SwitchMaterial? = null
    private var snoozeSwitch: SwitchMaterial? = null
    private var alarmName: TextInputEditText? = null
    private var soundName: MaterialTextView? = null
    private var vibrationName: MaterialTextView? = null
    private var snoozeVolume: MaterialTextView? = null

    //RecyclerView adapters
    private var adapterForHours: NumbersPickerAdapter? = NumbersPickerAdapter(24, this)
    private var adapterForMinutes: NumbersPickerAdapter? = NumbersPickerAdapter(60, this)

    //RingtoneManager
    private val ringtoneManager = RingtoneManager(this)

    //Alarm model
    private var alarm: Alarm? = null
    private var selectedDays = mutableListOf<Alarm.Days>()

    //ViewModel
    private var viewModel: CreateAlarmViewModel? = null

    //ActivityResultAPI
    private var getRingtoneContract = registerForActivityResult(AlarmSoundContract()) {
        if (it != null) {
            alarm?.ringtone = it
            soundName?.text = ringtoneManager.getTitleOfRingtone(it.ringtoneUri)

        }
    }

    companion object {
        const val MAX_VELOCITY_Y = 4500
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


        //viewModel
        viewModel = ViewModelProvider(this)[CreateAlarmViewModel::class.java]

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
        alarmSoundSwitch = findViewById(R.id.alarmSoundSwitch)
        vibrationSwitch = findViewById(R.id.vibrationSwitch)
        snoozeSwitch = findViewById(R.id.snoozeSwitch)
        alarmName = findViewById(R.id.alarmName)
        soundName = findViewById(R.id.soundName)
        vibrationName = findViewById(R.id.vibrationName)
        snoozeVolume = findViewById(R.id.snoozeVolume)

        alarm = intent.getParcelableExtra(KEY)

        setupCancelButton()
        setupRecyclerViews()
        setupAlarmDaysButtons()
        setupViews()
        setupSwitches()
        observeDaysOfTheWeek()
    }

    private fun setupRecyclerViews() {
        hours?.apply {
            addItemDecoration(NumbersPickerItemDecorator(this@CreateAlarmActivity))
            layoutManager =
                LinearLayoutManager(this@CreateAlarmActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterForHours
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            scrollToPosition(Int.MAX_VALUE / 2 - 16)
            onFlingListener = object : RecyclerView.OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    var velocity = velocityY
                    if (abs(velocity) > MAX_VELOCITY_Y) {
                        velocity = MAX_VELOCITY_Y * sign(velocityY.toDouble()).toInt()
                        snapHelper.onFling(velocityX, velocity)
                        return true
                    }

                    return false
                }
            }
        }
        minutes?.apply {
            addItemDecoration(NumbersPickerItemDecorator(this@CreateAlarmActivity))
            layoutManager =
                LinearLayoutManager(this@CreateAlarmActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterForMinutes
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            scrollToPosition(Int.MAX_VALUE / 2 - 4)
            onFlingListener = object : RecyclerView.OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    var velocity = velocityY
                    if (abs(velocity) > MAX_VELOCITY_Y) {
                        velocity = MAX_VELOCITY_Y * sign(velocityY.toDouble()).toInt()
                        snapHelper.onFling(velocityX, velocity)
                        return true
                    }
                    return false
                }
            }
        }
    }

    private fun setupCancelButton() {
        cancelButton?.setOnClickListener {
            onBackPressed()
        }
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

        dayStateChanged(mondayCheckbox, mondayTextView, dayOfWeek = Alarm.Days.MONDAY)
        dayStateChanged(tuesdayCheckbox, tuesdayTextView, dayOfWeek = Alarm.Days.TUESDAY)
        dayStateChanged(wednesdayCheckbox, wednesdayTextView, dayOfWeek = Alarm.Days.WEDNESDAY)
        dayStateChanged(thursdayCheckbox, thursdayTextView, dayOfWeek = Alarm.Days.THURSDAY)
        dayStateChanged(fridayCheckbox, fridayTextView, dayOfWeek = Alarm.Days.FRIDAY)
        dayStateChanged(saturdayCheckbox, saturdayTextView, dayOfWeek = Alarm.Days.SATURDAY)
        dayStateChanged(sundayCheckbox, sundayTextView, true, dayOfWeek = Alarm.Days.SUNDAY)
    }

    private fun setupViews() {
        alarmSoundButton?.setOnClickListener {
            if (alarm?.ringtone == null) {
                getRingtoneContract.launch(alarm?.ringtone)
            } else {
                getRingtoneContract.launch(alarm?.ringtone)
            }
        }
        snoozeButton?.setOnClickListener {
            startActivity(SnoozeActivity.newInstance(this))
        }
        vibrationVibration?.setOnClickListener {
            startActivity(VibrationActivity.newInstance(this))
        }
        if (alarm?.name != null) {
            alarmName?.setText(alarm?.name)
        }
        if (alarm?.ringtone?.isTurnedOn != null) {
            alarmSoundSwitch?.isChecked = alarm?.ringtone?.isTurnedOn!!
            if (alarm?.ringtone?.isTurnedOn!!) {
                soundName?.text = ringtoneManager.getTitleOfRingtone(alarm?.ringtone?.ringtoneUri)
            } else {
                snoozeVolume?.text = getString(R.string.off)
            }
        }
        if (alarm?.snooze?.isTurnedOn != null) {
            snoozeSwitch?.isChecked = alarm?.snooze?.isTurnedOn!!
            if (alarm?.snooze?.isTurnedOn!!) {
                snoozeVolume?.text = when (alarm?.snooze?.interval) {
                    Alarm.Snooze.Interval.FIVE -> "5 times"
                    Alarm.Snooze.Interval.TEN -> "10 times"
                    else -> "off"
                }
            } else {
                snoozeVolume?.text = getString(R.string.off)
            }
        } else {
            snoozeVolume?.text = getString(R.string.snooze_volume)
        }
        if (alarm?.vibration?.isTurnedOn != null) {
            vibrationSwitch?.isChecked = alarm?.vibration?.isTurnedOn!!
            if (alarm?.vibration?.isTurnedOn!!) {
                vibrationName?.text = alarm?.vibration?.vibrationUri
            } else {
                vibrationName?.text = getString(R.string.off)
            }
        }
    }

    private fun setupSwitches() {
        alarmSoundSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                soundName?.text = ringtoneManager.getTitleOfRingtone(alarm?.ringtone?.ringtoneUri)
            } else {
                soundName?.text = getString(R.string.off)
            }
        }
        vibrationSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vibrationName?.text = alarm?.vibration?.vibrationUri
            } else {
                vibrationName?.text = getString(R.string.off)
            }
        }
        snoozeSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                snoozeVolume?.text = when (alarm?.snooze?.interval) {
                    Alarm.Snooze.Interval.FIVE -> "5 times"
                    Alarm.Snooze.Interval.TEN -> "10 times"
                    else -> "Off"
                }
            } else {
                snoozeVolume?.text = getString(R.string.off)
            }
        }
    }

    private fun setupSaveButton() {
        saveButton?.setOnClickListener {

        }
    }

    private fun observeDaysOfTheWeek() {
        viewModel?.getDays()?.observe(this) { day ->
            if (day != null) {
                if (selectedDays.contains(day)) selectedDays.remove(day) else selectedDays.add(day)
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

    private fun dayStateChanged(
        checkBox: MaterialCheckBox?,
        textView: MaterialTextView?,
        isSunday: Boolean = false,
        dayOfWeek: Alarm.Days,
    ) {
        checkBox?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textView?.setTextColor(resources.getColor(R.color.purple_500, null))
                viewModel?.notify(dayOfWeek)
            } else {
                if (isSunday) textView?.setTextColor(
                    resources.getColor(
                        R.color.red,
                        null
                    )
                ) else textView?.setTextColor(resources.getColor(R.color.fontColor, null))
                viewModel?.notify(dayOfWeek)
            }
        }
    }
}