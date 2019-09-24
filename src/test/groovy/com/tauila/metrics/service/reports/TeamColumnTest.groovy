package com.herrminer.metrics.service.reports

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.TeamColumn

class TeamColumnTest extends TeamTestBase {

  def "test that team name is returned"() {
    expect:
    new TeamColumn().getColumnValue(user) == 'team 1'
  }

}
