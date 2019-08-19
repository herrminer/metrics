package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase

class ImpactColumnSpec extends TeamTestBase {

  def "get value"() {
    expect:
    new ImpactColumn().getColumnValue(user) == 1.43.toString()
  }
}
