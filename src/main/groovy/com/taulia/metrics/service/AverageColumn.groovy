package com.taulia.metrics.service

import com.taulia.metrics.model.User

class AverageColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'FT Average'
  }

  @Override
  String getColumnValue(User user) {
    AverageCalculator.getFullTimeAverage(user.team.organization).toString()
  }

}
