package kz.ali.alarmclock.ui.presentation.snooze

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.ali.alarmclock.R
import kz.ali.alarmclock.ui.presentation.vibration.VibrationActivity

class SnoozeActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent {
            return Intent(context, SnoozeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooze)
    }
}