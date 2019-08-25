package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

class PullRequestsColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Pull Requests'
  }

  @Override
  String getColumnValue(User user) {
    user.numberOfPullRequests
  }

}
