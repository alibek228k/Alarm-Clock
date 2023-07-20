package kz.ali.alarmclock.presentation.alarmsound

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.presentation.alarmsound.ringtonemanager.RingtoneManager
import kz.ali.alarmclock.presentation.alarmsound.ringtonemanager.volumeToFloat
import kz.ali.alarmclock.presentation.alarmsound.ringtonemanager.volumeToInt
import kz.ali.alarmclock.utils.setupActionBar

class AlarmSoundActivity : AppCompatActivity() {

    companion object {
        const val RINGTONE_EXTRA = "ringtoneExtra"
        fun newInstance(context: Context, ringtone: Alarm.Ringtone?): Intent {
            return if (ringtone != null) {
                Intent(context, AlarmSoundActivity::class.java)
                    .putExtra(RINGTONE_EXTRA, ringtone)
            } else {
                Intent(context, AlarmSoundActivity::class.java)
            }
        }
    }

    //RingtoneManager
    private var ringtoneManager = RingtoneManager(this)
    private var audioManager: AudioManager? = null

    //ViewModel
    private var alarmSoundViewModel: AlarmSoundViewModel? = null

    //UI components
    private var toolbar: MaterialToolbar? = null
    private var alarmSoundSwitch: SwitchMaterial? = null
    private var selectRingtoneView: CardView? = null
    private var soundName: MaterialTextView? = null
    private var seekBar: AppCompatSeekBar? = null
    private var isRingtonesEnabledCardView: CardView? = null
    private var offTextView: MaterialTextView? = null

    // ActivityResultAPI
    private var getContract = registerForActivityResult(RingtoneContract()) {
        ringtone?.ringtoneUri = it.toString()
        setupRingtone(ringtoneManager.getTitleOfRingtone(it.toString()))
    }

    //CurrentAlarmRingtone
    private var ringtone: Alarm.Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_sound)
        //Initialize ringtone
        ringtone = intent.getParcelableExtra(RINGTONE_EXTRA)
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        alarmSoundViewModel = ViewModelProvider(this)[AlarmSoundViewModel::class.java]
        //Setting result of activity result API
        setResult(RESULT_OK, intent)
        toolbar = findViewById(R.id.toolbar)
        alarmSoundSwitch = findViewById(R.id.alarmSoundSwitch)
        selectRingtoneView = findViewById(R.id.selectRingtoneView)
        soundName = findViewById(R.id.soundName)
        seekBar = findViewById(R.id.seekBar)
        isRingtonesEnabledCardView = findViewById(R.id.isRingtonesEnabledCardView)
        setupActionBar()
        setupSelectRingtoneView()
        setupRingtone()
        setupSeekBar()
        observeAlarmAvailability()
    }

    private fun setupActionBar() {
        if (toolbar != null) {
            setupActionBar(toolbar!!, titleResId = R.string.alarm_sound) {
                onBackPressed()
            }
        }
    }

    private fun setupSelectRingtoneView() {
        if (ringtone?.isTurnedOn != null){
            alarmSoundViewModel?.isRingtoneAvailable(ringtone?.isTurnedOn!!)
        }
        selectRingtoneView?.setOnClickListener {
            getContract.launch(0)
        }
    }

    private fun setupSeekBar() {
        val maxVolume = audioManager?.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        if (maxVolume != null) {
            seekBar?.max = maxVolume
        }
        if (ringtone?.volume?.volumeToInt() != null) {
            seekBar?.progress = ringtone?.volume?.volumeToInt()!!
        }
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progressVolume = p1.volumeToFloat()
                ringtoneManager.setVolume(progressVolume)
                if (ringtone != null) {
                    ringtone?.volume = progressVolume

                }
            }

            @RequiresApi(Build.VERSION_CODES.P)
            override fun onStartTrackingTouch(p0: SeekBar?) {
                ringtoneManager.playAvailableRingtone(ringtone)

            }

            @RequiresApi(Build.VERSION_CODES.P)
            override fun onStopTrackingTouch(p0: SeekBar?) {
                ringtoneManager.stopAvailableRingtone()
            }
        })
    }

    private fun setupRingtone(name: String? = null) {
        if (name != null) {
            soundName?.text = name
            return
        }
        if (ringtone?.ringtoneUri != null) {
            soundName?.text = ringtoneManager.getTitleOfRingtone(ringtone?.ringtoneUri)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeAlarmAvailability(){
        alarmSoundViewModel?.getRingtoneAvailability()?.observe(this){
            if(it == true){
                isRingtonesEnabledCardView?.background = resources?.getDrawable(R.color.purple_500, null)
                offTextView?.text = getString(R.string.on)
                alarmSoundSwitch?.isChecked = it
            }else if (it == false){
                offTextView?.text = getString(R.string.off)
                isRingtonesEnabledCardView?.background = resources?.getDrawable(R.color.white, null)
                alarmSoundSwitch?.isChecked = it
            }
        }
    }
}