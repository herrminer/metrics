package com.herrminer.metrics.service.reports

import spock.lang.Specification

class ReportServiceSpec extends Specification {

  def "GetReports"() {
    given:
    ReportingContext reportingContext = new ReportingContext(
        exportDirectory: '/tmp'
    )

    when:
    def reports = ReportService.getReports(reportingContext)

    then:
    reports
    reports.size() == 4
    reports.every { it.reportingContext }
  }

}
