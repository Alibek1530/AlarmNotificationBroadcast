package uz.tis.alarmnotification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var selectedTv: TextView
    lateinit var selectedBt: Button
    lateinit var setBt: Button
    lateinit var cenceldBt: Button

    lateinit var picker: MaterialTimePicker
    lateinit var calendar: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedTv = findViewById(R.id.selectedTime)
        selectedBt = findViewById(R.id.selectetTimeBtn)
        setBt = findViewById(R.id.setAlarmBtn)
        cenceldBt = findViewById(R.id.cencelAlarnBtn)

        selectedBt.setOnClickListener {
            shoeTimePacker()
        }
        setBt.setOnClickListener {
            setAlarm()
        }
        cenceldBt.setOnClickListener {
            sencelAlarm()
        }




        createNotifityChannel()
    }

    private fun sencelAlarm() {
        alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(this,AlarmReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "alarm cancaled", Toast.LENGTH_SHORT).show()
    }

    private fun setAlarm() {
alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(this,AlarmReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            1000*60*2,pendingIntent
        )

        Toast.makeText(this, "alarm set sacses", Toast.LENGTH_SHORT).show()
    }

    private fun shoeTimePacker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("select alarm time")
            .build()

        picker.show(supportFragmentManager, "foxandroid")

        picker.addOnPositiveButtonClickListener {
            if (picker.hour > 12) {
                selectedTv.text = String.format("%02d", picker.hour - 12) + ":" + String.format(
                    "%02d",
                    picker.minute
                ) + " PM"
            } else {
                selectedTv.text = String.format("%02d", picker.hour - 12) + ":" + String.format(
                    "%02d",
                    picker.minute
                ) + " AM"
            }
            calendar= Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY]=picker.hour
            calendar[Calendar.MINUTE]=picker.minute
            calendar[Calendar.SECOND]=0
            calendar[Calendar.MILLISECOND]=0
        }
    }

    fun createNotifityChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "dada"
            val description = "Chaenl for alarm manager"

            val importance = NotificationManager.IMPORTANCE_HIGH
            val chanel = NotificationChannel("foxandroid", name, importance)
            chanel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(chanel)
        }
    }
}