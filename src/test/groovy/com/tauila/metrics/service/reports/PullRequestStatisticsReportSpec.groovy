package com.herrminer.metrics.service.reports

import com.herrminer.metrics.service.reports.PullRequestStatisticsReport
import spock.lang.Specification

class PullRequestStatisticsReportSpec extends Specification {

  def "test that all columns have a header"() {
    expect:
    new PullRequestStatisticsReport().columns.each {
      assert it.columnHeader
    }
  }

}
