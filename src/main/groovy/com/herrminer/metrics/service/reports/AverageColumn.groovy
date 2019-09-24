package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

class AverageColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Full-time Average'
  }

  @Override
  String getColumnValue(User user) {
    AverageCalculator.getFullTimeAverage(user.team.organization).toString()
  }

}
