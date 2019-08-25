package com.tauila.metrics.service.reports

import com.tauila.metrics.service.TeamTestBase
import com.taulia.metrics.service.reports.PercentOfTeamTotalColumn

class PercentOfTeamTotalColumnSpec extends TeamTestBase {

  def "getPercentOfTeamTotal"() {
    given:
    PercentOfTeamTotalColumn column = new PercentOfTeamTotalColumn()

    expect:
    column.getPercentOfTeamTotal(user) == new BigDecimal(".31")
    column.getPercentOfTeamTotal(user2) == new BigDecimal(".50")
    column.getPercentOfTeamTotal(user3) == new BigDecimal(".19")
  }


}
