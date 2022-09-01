package kz.ali.alarmclock.ui.presentation.alarmsound

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.ali.alarmclock.R
import kz.ali.alarmclock.ui.presentation.vibration.VibrationActivity

class AlarmSoundActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent {
            return Intent(context, AlarmSoundActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_sound)
    }
}