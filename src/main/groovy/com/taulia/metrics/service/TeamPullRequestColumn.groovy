package com.taulia.metrics.service

import com.taulia.metrics.model.User

class TeamPullRequestColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'Team PRs'
  }

  @Override
  String getColumnValue(User user) {
    user.team.users*.numberOfPullRequests.sum()
  }

}
