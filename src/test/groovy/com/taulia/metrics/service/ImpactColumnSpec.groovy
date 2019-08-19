package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase

class ImpactColumnSpec extends TeamTestBase {

  def "calculateFullTimeAverage"() {
    expect:
    new ImpactColumn().calculateFullTimeAverage(user) == BigDecimal.valueOf(3.5)
  }

  def "get value"() {
    expect:
    new ImpactColumn().getColumnValue(user) == 1.43.toString()
  }
}
