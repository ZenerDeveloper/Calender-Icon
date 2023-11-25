package io.github.zenerdeveloper.android.calendaricon

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

fun setAlarm(context: Context) {

    var alarmMgr: AlarmManager? = null
    alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent: PendingIntent =
        Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
    alarmMgr.cancel(alarmIntent)

    val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.DAY_OF_YEAR, this.get(Calendar.DAY_OF_YEAR) + 1)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        alarmMgr.setExact(
            AlarmManager.RTC,
            calendar.timeInMillis,
            alarmIntent
        )
    } else {
        alarmMgr.set(
            AlarmManager.RTC,
            calendar.timeInMillis,
            alarmIntent
        )
    }
}