package com.herrminer.metrics.service

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.ImpactColumn

class ImpactColumnSpec extends TeamTestBase {

  def "get value"() {
    expect:
    new ImpactColumn().getColumnValue(user) == 1.43.toString()
  }
}
