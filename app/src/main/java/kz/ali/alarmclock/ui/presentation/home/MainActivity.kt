package kz.ali.alarmclock.ui.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import kz.ali.alarmclock.R

class MainActivity : AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.mainToolbar)
        supportActionBar?.title = "Alarm"
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }
}