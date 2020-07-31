package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.MainViewModel
import bj4.dev.yhh.lottery.main.type.TableType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableTypeDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_table_type_title)
            .setSingleChoiceItems(
                R.array.table_type,
                mainViewModel.tableType.value?.ordinal ?: 0
            ) { _, which ->
                mainViewModel.setTableType(TableType.values()[which])
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }
}