package kz.ali.alarmclock.presentation.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.presentation.alarmsound.ringtonemanager.RingtoneManager
import kz.ali.alarmclock.presentation.ui.createalarm.CreateAlarmActivity
import kz.ali.alarmclock.presentation.ui.home.itemdecoration.AlarmsAdapterItemDecorator
import kz.ali.alarmclock.presentation.ui.home.vm.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AlarmsAdapter.Callback {

    private var toolbar: MaterialToolbar? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: AlarmsAdapter? = null
    private var appbarLayout: AppBarLayout? = null
    private val viewModel by viewModels<MainViewModel>()

    //Ringtone manager
    private var ringtoneManager = RingtoneManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.mainToolbar)
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout)
        recyclerView = findViewById(R.id.recyclerView)
        appbarLayout = findViewById(R.id.appbarLayout)

        setupToolbar()
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addAlarm -> addAlarm()
            R.id.edit -> clear()
            R.id.sort -> startActivity(CreateAlarmActivity.newInstance(this))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        collapsingToolbarLayout?.title = "Alarm"
    }

    private fun setupRecyclerView() {
        adapter = AlarmsAdapter(this, this)
        adapter?.setAlarms(viewModel.getAlarms() ?: emptyList())

        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(AlarmsAdapterItemDecorator(this))

    }

    @SuppressLint("SimpleDateFormat")
    private fun addAlarm() {
        val calendar = Calendar.getInstance()
        val timeInMilliseconds = Date()
        val formatter = SimpleDateFormat("HH:mm")
        calendar.timeInMillis = timeInMilliseconds.time
        adapter?.addAlarm(
            Alarm(
                0,
                calendar,
                "Wake up",
                Alarm.Ringtone(true, ringtoneManager.getDefaultRingtoneUri()),
                formatter.format(calendar.time),
                listOf(
//                    Alarm.Days.THURSDAY,
//                    Alarm.Days.FRIDAY,
                    Alarm.Days.SATURDAY,
                    Alarm.Days.SUNDAY,
                    Alarm.Days.MONDAY,
                    Alarm.Days.TUESDAY,
                    Alarm.Days.WEDNESDAY
                ),
                isActive = true
            )
        )
        recyclerView?.scrollToPosition(0)

        viewModel.saveAlarms(adapter?.getAlarms())
    }

    private fun clear() {
        adapter?.clear()
        viewModel.saveAlarms(adapter?.getAlarms())
    }

    override fun onItemClicked(alarm: Alarm) {
        startActivity(CreateAlarmActivity.newInstance(this, alarm))
    }

    override fun onCheckedStateChanged() {
        viewModel.saveAlarms(adapter?.getAlarms())
    }
}