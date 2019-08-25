package com.taulia.metrics.service

import com.tauila.metrics.service.TeamTestBase
import com.taulia.metrics.service.reports.AveragePullRequestFilesColumn

class AveragePullRequestFilesColumnSpec extends TeamTestBase {

  def "GetColumnValue"() {
    given:
    AveragePullRequestFilesColumn column = new AveragePullRequestFilesColumn()

    expect:
    column.getColumnValue(user) == 2.33.toString()
  }

}
