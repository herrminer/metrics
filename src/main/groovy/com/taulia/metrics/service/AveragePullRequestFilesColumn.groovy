package com.taulia.metrics.service

import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest

import java.math.RoundingMode

class AveragePullRequestFilesColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'Avg Files / PR'
  }

  @Override
  String getColumnValue(User user) {
    if (!user.pullRequests) { return 0 }
    int totalFiles = (user.pullRequests*.files*.size()).sum()
    (totalFiles / user.pullRequests.size()).setScale(2, RoundingMode.HALF_UP)
  }

}
