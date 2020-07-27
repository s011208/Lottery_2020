package bj4.dev.yhh.lottery.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.settings.updatelog.UpdateLogActivity

class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        private const val KEY_UPDATE_LOG = "settings_update_log"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            KEY_UPDATE_LOG -> {
                startActivity(Intent(context, UpdateLogActivity::class.java))
                true
            }
            else -> super.onPreferenceTreeClick(preference)
        }
    }
}