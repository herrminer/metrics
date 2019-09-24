package com.herrminer.metrics.service

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.AveragePrSizeColumn

class AveragePrSizeColumnSpec extends TeamTestBase {

  def "GetColumnValue"() {
    given:
    AveragePrSizeColumn column = new AveragePrSizeColumn()

    expect:
    column.getColumnValue(user) == 25.67.toString()
  }

}
