package io.github.zenerdeveloper.android.calendaricon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import java.util.Date

fun setIcon(context: Context) {
    val date = Date()

    val currentDate = date.date

    val pm = context.packageManager

    for (i in 1..31) {
        //  This must be the full-formed class name of the launcher activity minus the date
        val classType = Class.forName("io.github.zenerdeveloper.android.calendaricon.icons.LaunchActivity${i}")

        //  Set whether the activity is enabled or disabled based on the current date
        pm.setComponentEnabledSetting(
            ComponentName(context, classType),
            if(i == currentDate)
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            else
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}