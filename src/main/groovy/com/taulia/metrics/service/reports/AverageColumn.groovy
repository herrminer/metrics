package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User

class AverageColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'FT Average'
  }

  @Override
  String getColumnValue(User user) {
    AverageCalculator.getFullTimeAverage(user.team.organization).toString()
  }

}
