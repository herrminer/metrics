package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

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
