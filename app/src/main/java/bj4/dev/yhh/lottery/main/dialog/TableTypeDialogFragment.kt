package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R

class TableTypeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_table_type_title)
            .setSingleChoiceItems(
                R.array.table_type,
                0
            ) { _, which ->
                val activity = requireActivity()
                if (activity is Callback) {
                    activity.onTableTypeSelected(TableType.values()[which])
                }
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }

    interface Callback {
        fun onTableTypeSelected(tableType: TableType)
    }
}

enum class TableType {
    Large, Small
}