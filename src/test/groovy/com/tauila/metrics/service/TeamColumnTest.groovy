package com.tauila.metrics.service


import com.taulia.metrics.service.TeamColumn

class TeamColumnTest extends TeamTestBase {

  def "test that team name is returned"() {
    expect:
    new TeamColumn().getColumnValue(user) == 'team 1'
  }

}
