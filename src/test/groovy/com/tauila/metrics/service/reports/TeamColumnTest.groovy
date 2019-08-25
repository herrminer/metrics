package com.tauila.metrics.service.reports

import com.tauila.metrics.service.TeamTestBase
import com.taulia.metrics.service.reports.TeamColumn

class TeamColumnTest extends TeamTestBase {

  def "test that team name is returned"() {
    expect:
    new TeamColumn().getColumnValue(user) == 'team 1'
  }

}
