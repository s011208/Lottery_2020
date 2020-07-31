package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.MainViewModel
import bj4.dev.yhh.lotterydata.LotteryType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LotteryTypeDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_lottery_type_title)
            .setSingleChoiceItems(
                R.array.lottery_type,
                mainViewModel.lotteryType.value?.ordinal ?: 0
            ) { _, which ->
                mainViewModel.setLotteryType(LotteryType.values()[which])
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }
}