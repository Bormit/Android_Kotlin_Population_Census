package ru.mirea.populationcensus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = this.getPreferences(MODE_PRIVATE)
        sharedPref.edit().remove("login").apply()
    }
}