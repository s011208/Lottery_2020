package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.MainViewModel
import bj4.dev.yhh.lottery.main.type.DisplayType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DisplayTypeDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_display_type_title)
            .setSingleChoiceItems(
                R.array.display_type,
                mainViewModel.displayType.value?.ordinal ?: 0
            ) { _, which ->
                mainViewModel.setDisplayType(DisplayType.values()[which])
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }
}