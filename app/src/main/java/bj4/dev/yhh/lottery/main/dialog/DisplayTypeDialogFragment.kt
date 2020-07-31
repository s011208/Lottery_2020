package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R

class DisplayTypeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_display_type_title)
            .setSingleChoiceItems(
                R.array.display_type,
                0
            ) { _, which ->
                val activity = requireActivity()
                if (activity is Callback) {
                    activity.onDisplayTypeSelected(DisplayType.values()[which])
                }
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }

    interface Callback {
        fun onDisplayTypeSelected(displayType: DisplayType)
    }
}

enum class DisplayType {
    ORDER, ADDING, ENDING
}