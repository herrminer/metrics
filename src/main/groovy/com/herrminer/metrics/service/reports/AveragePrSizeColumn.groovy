package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

class AveragePrSizeColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Avg PR Size'
  }

  @Override
  String getColumnValue(User user) {
    if (!user.pullRequests) {
      return 0
    }
    int totalSize = user.pullRequests*.files.flatten()*.changes.sum()
    (totalSize / user.pullRequests.size()).setScale(2, RoundingMode.HALF_UP)
  }

}
