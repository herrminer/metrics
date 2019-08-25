package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

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
