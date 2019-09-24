package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

class TeamPullRequestColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Team PRs'
  }

  @Override
  String getColumnValue(User user) {
    user.team.users*.numberOfPullRequests.sum()
  }

}
