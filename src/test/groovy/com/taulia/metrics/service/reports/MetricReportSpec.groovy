package com.taulia.metrics.service.reports

import com.taulia.metrics.service.SearchParameters
import spock.lang.Specification

class MetricReportSpec extends Specification {

  MetricReport report
  ReportingContext reportingContext

  def setup() {
    reportingContext = new ReportingContext(
        searchParameters: new SearchParameters(
            fromDate: '2019-01-01',
            toDate: '2019-01-31'
        )
    )
    report = new MetricReport(reportingContext) {
      @Override
      File buildCsvFile() {
        return null
      }
    }
  }

  def "NoDash"() {
    expect:
    report.noDash('fromDate') == '20190101'
  }

  def "StrippedDateRange"() {
    expect:
    report.strippedDateRange() == '20190101-20190131'
  }

  def "CreateReportFile"() {
    when:
    def reportFile = report.createReportFile('report')

    then:
    reportFile
    reportFile.name == 'report-20190101-20190131.csv'
  }
}