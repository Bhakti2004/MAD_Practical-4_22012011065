package com.example.mad_practical_4_22012011065

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.zip.DataFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun getCurrentDateTime(): String{
        val cal=Calendar.getInstance()
        val df: DateFormat= SimpleDateFormat("MMM,dd yyyy hh:mm:ss a")
        return df.format(cal.time)
    }
    fun getMillis(hour:Int,min:Int):Long
    {
        val setCalendar=Calendar.getInstance()
        setCalendar[Calendar.HOUR_OF_DAY]=hour
        setCalendar[Calendar.MINUTE]=min
        setCalendar[Calendar.SECOND]=0
        return setCalendar.timeInMillis

    }
    fun showTimerDialog(){
        val cldr: Calendar = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cldr.get(Calendar.MINUTE)
        val picker=TimePickerDialog(
            this,
            { tp, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute)},
            hour,
            minutes,
            false
        )
        picker.show()
    }
    fun setAlarm(millisTime: Long,str:String)
    {
        val intent= Intent(this,AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1",str)
        val pendingIntent=PendingIntent.getBroadcast(applicationContext,234324243,intent,PendingIntent.FLAG_UPDATE_CURRENT )
        val alarmManager= getSystemService(ALARM_SERVICE) as AlarmManager
        if(str=="Start")
        {
            if(alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    millisTime,
                    pendingIntent
                )
            }
        }
        else if (str=="Stop")
        {
            alarmManager.cancel(pendingIntent);
            sendBroadcast(intent)
        }
    }
}









