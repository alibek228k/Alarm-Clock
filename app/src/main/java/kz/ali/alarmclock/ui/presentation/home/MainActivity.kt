package kz.ali.alarmclock.ui.presentation.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.alarmsound.ringtonemanager.RingtoneManager
import kz.ali.alarmclock.ui.presentation.createalarm.CreateAlarmActivity
import kz.ali.alarmclock.ui.presentation.home.itemdecoration.AlarmsAdapterItemDecorator
import kz.ali.alarmclock.ui.presentation.home.vm.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AlarmsAdapter.Callback {

    //UI Components
    private var toolbar: MaterialToolbar? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: AlarmsAdapter? = null
    private var appbarLayout: AppBarLayout? = null
    private var viewModel: MainViewModel? = null

    //Ringtone manager
    private var ringtoneManager = RingtoneManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        toolbar = findViewById(R.id.mainToolbar)
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout)
        recyclerView = findViewById(R.id.recyclerView)
        appbarLayout = findViewById(R.id.appbarLayout)

        setupToolbar()
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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
        adapter?.setAlarms(viewModel?.getAlarms() ?: emptyList())

        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(AlarmsAdapterItemDecorator(this))

    }

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

        viewModel?.saveAlarms(adapter?.getAlarms())
    }

    private fun clear() {
        adapter?.clear()
        viewModel?.saveAlarms(adapter?.getAlarms())
    }

    override fun onItemClicked(alarm: Alarm) {
        startActivity(CreateAlarmActivity.newInstance(this, alarm))
    }

    override fun onCheckedStateChanged() {
        viewModel?.saveAlarms(adapter?.getAlarms())
    }
}