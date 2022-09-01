package kz.ali.alarmclock.ui.presentation.vibration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.ali.alarmclock.R

class VibrationActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, VibrationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vibration)
    }
}