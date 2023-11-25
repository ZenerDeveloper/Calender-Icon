package io.github.zenerdeveloper.android.calendaricon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_BOOT_COMPLETED) {
            //  Check if the context is null
            if (context == null) {
                Log.e("Boot receiver error","Context is null")
                return
            }

            //  If all is well set the icon and then set a mew alarm
            setIcon(context)
            setAlarm(context)
        } else {
            Log.e("Boot receiver error", "The intent: $intent does not match the expected action BOOT_COMPLETED.")
        }


    }

}