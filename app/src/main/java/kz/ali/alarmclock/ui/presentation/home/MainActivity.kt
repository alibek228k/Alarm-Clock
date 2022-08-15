package kz.ali.alarmclock.ui.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kz.ali.alarmclock.R

class MainActivity : AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.mainToolbar)
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        collapsingToolbarLayout?.title = "Alarm"
    }
}