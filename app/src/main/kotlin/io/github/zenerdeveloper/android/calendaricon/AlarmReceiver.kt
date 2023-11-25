package io.github.zenerdeveloper.android.calendaricon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Time is ${Calendar.HOUR_OF_DAY}:${Calendar.MINUTE}")

        //  Check if the context is null
        if (context == null) {
            Log.e("Couldn't fire alarm","Context is null")
            return
        }

        //  If all is well set the icon and then set a mew alarm
        setIcon(context)
        setAlarm(context)
    }

}