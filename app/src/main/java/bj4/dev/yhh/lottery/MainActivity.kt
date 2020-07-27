package bj4.dev.yhh.lottery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bj4.dev.yhh.lottery.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, SettingsActivity::class.java))
    }
}