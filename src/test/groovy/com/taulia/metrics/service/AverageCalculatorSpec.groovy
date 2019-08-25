package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase
import com.taulia.metrics.service.reports.AverageCalculator

class AverageCalculatorSpec extends TeamTestBase {

  def "calculateFullTimeAverage"() {
    expect:
    AverageCalculator.calculateFullTimeAverage(user.team.organization) == 3.5
  }

}
