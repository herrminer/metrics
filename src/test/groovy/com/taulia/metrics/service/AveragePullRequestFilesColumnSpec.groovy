package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase

class AveragePullRequestFilesColumnSpec extends TeamTestBase {

  def "GetColumnValue"() {
    given:
    AveragePullRequestFilesColumn column = new AveragePullRequestFilesColumn()

    expect:
    column.getColumnValue(user) == 2.33.toString()
  }

}
