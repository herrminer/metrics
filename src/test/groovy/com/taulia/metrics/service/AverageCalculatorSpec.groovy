package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase

class AverageCalculatorSpec extends TeamTestBase {

  def "calculateFullTimeAverage"() {
    expect:
    AverageCalculator.calculateFullTimeAverage(user.team.organization) == 3.5
  }

}
