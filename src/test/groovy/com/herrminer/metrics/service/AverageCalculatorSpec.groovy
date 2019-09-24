package com.herrminer.metrics.service

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.AverageCalculator

class AverageCalculatorSpec extends TeamTestBase {

  def "calculateFullTimeAverage"() {
    expect:
    AverageCalculator.calculateFullTimeAverage(user.team.organization) == 3.5
  }

}
