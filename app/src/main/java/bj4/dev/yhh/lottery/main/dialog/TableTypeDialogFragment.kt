package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R

class TableTypeDialogFragment : DialogFragment() {

    companion object {
        private const val SELECTED_INDEX = "SELECTED_INDEX"

        fun make(selectedIndex: Int): TableTypeDialogFragment {
            return TableTypeDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(SELECTED_INDEX, selectedIndex)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_table_type_title)
            .setSingleChoiceItems(
                R.array.table_type,
                requireArguments().getInt(SELECTED_INDEX)
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