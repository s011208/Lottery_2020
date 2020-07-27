package bj4.dev.yhh.lottery.settings.updatelog

import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lotterydata.repository.UpdateLogRepository

class UpdateLogViewModel(repository: UpdateLogRepository): ViewModel() {

    val log = repository.getAll()
}