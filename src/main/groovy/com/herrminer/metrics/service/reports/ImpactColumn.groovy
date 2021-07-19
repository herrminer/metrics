package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

/**
 * Number of pull requests compared to the average
 * for all full-time engineers (non-manager, non-QA)
 * in the organization
 */
class ImpactColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Impact'
  }

  @Override
  String getColumnValue(User user) {
    def fullTimeAverage = AverageCalculator.getFullTimeAverage(user.team.organization)
    BigDecimal.valueOf(user.numberOfPullRequests).divide(fullTimeAverage, 2, RoundingMode.HALF_UP)
  }

}
