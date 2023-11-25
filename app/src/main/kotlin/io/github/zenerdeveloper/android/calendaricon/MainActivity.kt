package io.github.zenerdeveloper.android.calendaricon

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle


open class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setIcon(this)

        val sharedPref = this.getSharedPreferences(this.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        val launchPackage = sharedPref.getString(resources.getString(R.string.preference_package), "")!!
        val launchComponent = sharedPref.getString(resources.getString(R.string.preference_component), "")!!

        setAlarm(this)

        val i = Intent()
        i.component = ComponentName(launchPackage, launchComponent)
        i.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

        try {
            startActivity(i)
        } catch (e: Exception) {
            val changeIntent = Intent(this, ChangeActivity::class.java)
            startActivity(changeIntent)
        }
        finish()
    }

}