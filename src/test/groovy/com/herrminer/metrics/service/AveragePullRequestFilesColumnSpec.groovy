package com.herrminer.metrics.service

import com.herrminer.metrics.service.TeamTestBase
import com.herrminer.metrics.service.reports.AveragePullRequestFilesColumn

class AveragePullRequestFilesColumnSpec extends TeamTestBase {

  def "GetColumnValue"() {
    given:
    AveragePullRequestFilesColumn column = new AveragePullRequestFilesColumn()

    expect:
    column.getColumnValue(user) == 2.33.toString()
  }

}
