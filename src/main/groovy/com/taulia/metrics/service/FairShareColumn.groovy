package com.taulia.metrics.service

import com.taulia.metrics.model.User

import java.math.RoundingMode

class FairShareColumn implements CsvColumn {

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
