package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase
import com.taulia.metrics.service.reports.AveragePrSizeColumn

class AveragePrSizeColumnSpec extends TeamTestBase {

  def "GetColumnValue"() {
    given:
    AveragePrSizeColumn column = new AveragePrSizeColumn()

    expect:
    column.getColumnValue(user) == 25.67.toString()
  }

}
