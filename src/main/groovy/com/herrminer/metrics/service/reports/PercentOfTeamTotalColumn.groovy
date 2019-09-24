package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

class PercentOfTeamTotalColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    '% of Team'
  }

  @Override
  String getColumnValue(User user) {
    getPercentOfTeamTotal(user)
  }

  BigDecimal getPercentOfTeamTotal(User user) {
    int teamPullRequestCount = (int) user.team.users*.numberOfPullRequests.sum()
    if (!teamPullRequestCount) { return BigDecimal.ZERO }
    return BigDecimal.valueOf(user.numberOfPullRequests)
      .divide(BigDecimal.valueOf(teamPullRequestCount), 2, RoundingMode.HALF_UP)
  }

}
