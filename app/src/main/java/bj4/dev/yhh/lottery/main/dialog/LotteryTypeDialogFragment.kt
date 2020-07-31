package bj4.dev.yhh.lottery.main.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lotterydata.LotteryType

class LotteryTypeDialogFragment : DialogFragment() {

    companion object {
        private const val SELECTED_INDEX = "SELECTED_INDEX"

        fun make(selectedIndex: Int): LotteryTypeDialogFragment {
            return LotteryTypeDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(SELECTED_INDEX, selectedIndex)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_lottery_type_title)
            .setSingleChoiceItems(
                R.array.lottery_type,
                requireArguments().getInt(SELECTED_INDEX)
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