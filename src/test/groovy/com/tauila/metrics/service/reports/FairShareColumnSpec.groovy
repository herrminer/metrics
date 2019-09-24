package com.herrminer.metrics.service.reports

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.FairShareColumn

class FairShareColumnSpec extends TeamTestBase {

  def "getFairSharePercentage"() {
    given:
    FairShareColumn column = new FairShareColumn()

    expect:
    column.getFairSharePercentage(user) == new BigDecimal(".77")
    column.getFairSharePercentage(user2) == new BigDecimal("1.23")
    column.getFairSharePercentage(user3) == BigDecimal.ZERO
  }

}
