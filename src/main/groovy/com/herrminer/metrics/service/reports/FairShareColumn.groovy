package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

/**
 * Number of user's pull requests compared to the average
 * for all engineers (including managers) on their team
 */
class FairShareColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Fair Share %'
  }

  @Override
  String getColumnValue(User user) {
    getFairSharePercentage(user)
  }

  BigDecimal getFairSharePercentage(User user) {
    if (!user.softwareEngineer) { return BigDecimal.ZERO }
    def softwareEngineers = user.team.users.findAll({ it.softwareEngineer })
    int developerPullRequestTotal = (int) softwareEngineers*.numberOfPullRequests.sum()
    if (developerPullRequestTotal == 0) { return BigDecimal.ZERO }
    double fairShareAmount = developerPullRequestTotal / softwareEngineers.size()
    return BigDecimal.valueOf(user.numberOfPullRequests)
      .divide(BigDecimal.valueOf(fairShareAmount), 2, RoundingMode.HALF_UP)
  }

}
