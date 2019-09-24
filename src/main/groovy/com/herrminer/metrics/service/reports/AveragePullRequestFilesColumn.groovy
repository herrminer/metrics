package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

class AveragePullRequestFilesColumn implements ReportColumn {

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
