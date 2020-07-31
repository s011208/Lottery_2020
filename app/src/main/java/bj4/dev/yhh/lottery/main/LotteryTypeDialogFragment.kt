package bj4.dev.yhh.lottery.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lotterydata.LotteryType

class LotteryTypeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setSingleChoiceItems(
                R.array.lottery_type,
                0
            ) { _, which ->
                val activity = requireActivity()
                if (activity is Callback) {
                    activity.onLotteryTypeSelected(LotteryType.values()[which])
                }
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> dismiss() }
            .create()
    }

    interface Callback {
        fun onLotteryTypeSelected(lotteryType: LotteryType)
    }
}