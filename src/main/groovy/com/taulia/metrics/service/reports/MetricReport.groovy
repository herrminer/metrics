package com.taulia.metrics.service.reports

abstract class MetricReport {

  ReportingContext reportingContext

  MetricReport(ReportingContext reportingContext) {
    this.reportingContext = reportingContext
  }

  abstract File buildCsvFile()

}