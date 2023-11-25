package io.github.zenerdeveloper.android.calendaricon

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ChangeActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences(this.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        val pm = this.packageManager

        fun listApps(apps: List<ActivityInfo>, layout: LinearLayout){
            for (app in apps) {
                val entry: View = LayoutInflater.from(this).inflate(R.layout.entry, null)
                val entryIcon: ImageView = entry.findViewById(R.id.imageView)
                val entryLabel: TextView = entry.findViewById(R.id.textView)

                entryIcon.setImageDrawable(app.loadIcon(pm))
                entryLabel.text = app.loadLabel(pm)

                entry.setOnClickListener {

                    sharedPref.edit().putString(getString(R.string.preference_package), app.packageName).commit()
                    sharedPref.edit().putString(getString(R.string.preference_component), app.name).commit()

                    try {
                        val i = Intent()
                        i.component = ComponentName(app.packageName, app.name)
                        startActivity(i)
                        finish()
                    } catch (e: Exception) {
                        val changeIntent = Intent(this, ChangeActivity::class.java)
                        startActivity(changeIntent)
                    }
                }
                layout.addView(entry)
            }
        }

        val appList: LinearLayout = findViewById(R.id.linearLayout)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_CALENDAR)

            val recommendLabel = TextView(this)
            recommendLabel.text = getString(R.string.recommended_label)
            recommendLabel.textSize = 28f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                recommendLabel.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }
            val recApps = mutableListOf<ActivityInfo>()
            pm.queryIntentActivities(intent, 0).forEach {
                recApps.add(it.activityInfo)
            }

            if (recApps.isNotEmpty()) {
                appList.addView(recommendLabel)
                listApps(recApps, appList)
            }
        }

        val allIntent = Intent(Intent.ACTION_MAIN)
        allIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val allApps = mutableListOf<ActivityInfo>()
        pm.queryIntentActivities(allIntent, 0).forEach{
            allApps.add(it.activityInfo)
        }

        val allLabel = TextView(this)

        allLabel.text = getString(R.string.all_label)
        allLabel.textSize = 28f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            allLabel.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }
        appList.addView(allLabel)
        listApps(allApps, appList)

    }


}