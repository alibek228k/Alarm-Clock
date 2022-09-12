package kz.ali.alarmclock.ui.presentation.alarmsound

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import kz.ali.alarmclock.R
import kz.ali.alarmclock.utils.setupActionBar

class AlarmSoundActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, AlarmSoundActivity::class.java)
        }
    }

    //UI components
    private var toolbar: MaterialToolbar? = null
    private var alarmSoundSwitch: SwitchMaterial? = null
    private var selectRingtoneView: CardView? = null
    private var getContract = registerForActivityResult(RingtoneContract()) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_sound)

        toolbar = findViewById(R.id.toolbar)
        alarmSoundSwitch = findViewById(R.id.alarmSoundSwitch)
        selectRingtoneView = findViewById(R.id.selectRingtoneView)
        setupActionBar()
        setupSelectRingtoneView()
    }

    private fun setupActionBar() {
        if (toolbar != null) {
            setupActionBar(toolbar!!, titleResId = R.string.alarm_sound) {
                onBackPressed()
            }
        }
    }

    private fun setupSelectRingtoneView() {
        selectRingtoneView?.setOnClickListener {
            getContract.launch(0)
        }
    }
}