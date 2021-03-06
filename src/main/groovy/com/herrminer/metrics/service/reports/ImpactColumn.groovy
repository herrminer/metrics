package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

import java.math.RoundingMode

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
