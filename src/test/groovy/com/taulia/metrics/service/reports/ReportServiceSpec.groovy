package com.taulia.metrics.service.reports

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
    reports.size() == 3
    reports.every { it.reportingContext }
  }

}
