package com.taulia.metrics.service

import com.taulia.metrics.model.User

class PullRequestsColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'Pull Requests'
  }

  @Override
  String getColumnValue(User user) {
    user.numberOfPullRequests
  }

}
