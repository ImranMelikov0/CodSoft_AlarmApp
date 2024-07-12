package com.imranmelikov.codsoft_alarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var alarmTimePicker: TimePicker
    private var pendingIntent: PendingIntent? = null
    private lateinit var alarmManager: AlarmManager
    private lateinit var toggleButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        toggleButton=findViewById(R.id.toggleButton)

    }

    @SuppressLint("ScheduleExactAlarm")
    fun onToggleClicked(view: View) {
        if ((view as ToggleButton).isChecked) {
            toggleButton.setBackgroundColor(Color.GREEN)
            Toast.makeText(this@MainActivity, "ALARM ON", Toast.LENGTH_SHORT).show()
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.hour)
            calendar.set(Calendar.MINUTE, alarmTimePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val intent = Intent(this, AlarmReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val time = calendar.timeInMillis
            if (System.currentTimeMillis() > time) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent!!)
        } else {
            pendingIntent?.let { alarmManager.cancel(it) }
            toggleButton.setBackgroundColor(Color.parseColor("#FF9800"))
            Toast.makeText(this@MainActivity, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
    }
}
