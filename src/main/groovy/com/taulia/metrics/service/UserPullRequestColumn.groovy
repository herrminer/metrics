package com.taulia.metrics.service

import com.taulia.metrics.model.User

class UserPullRequestColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'PRs'
  }

  @Override
  String getColumnValue(User user) {
    user.numberOfPullRequests
  }

}
