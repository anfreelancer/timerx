package com.luteapp.timerpx

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.aboutlibraries.LibsBuilder
import com.luteapp.timerpx.timer_list.TimerDefFragment
import com.luteapp.timerpx.util.hasAlarmApps

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (hasAlarmApps(this)) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_content, TimerDefFragment.newInstance())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_content, NoAlarmAppFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            LibsBuilder()
                .withAboutIconShown(true)
                .withAboutVersionShownName(true)
                .withActivityTitle(getString(R.string.app_name))
                .withAboutDescription(getString(R.string.text_about))
                .start(this)
        } else if (item.itemId == R.id.menu_premium) {
            startActivity(Intent(this@MainActivity, ShopActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}