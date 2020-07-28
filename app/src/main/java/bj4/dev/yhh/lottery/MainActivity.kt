package bj4.dev.yhh.lottery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bj4.dev.yhh.lottery.settings.SettingsActivity
import bj4.dev.yhh.lottery.table.large.LargeTableActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startActivity(Intent(this, SettingsActivity::class.java))
        startActivity(Intent(this, LargeTableActivity::class.java))
    }
}