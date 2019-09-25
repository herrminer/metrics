package com.herrminer.metrics.service.reports

abstract class MetricReport {

  ReportingContext reportingContext

  MetricReport(ReportingContext reportingContext) {
    this.reportingContext = reportingContext
  }

  abstract File buildCsvFile()

  protected File createReportFile(String baseReportName, boolean deleteIfExists = true) {
    def fileName = "${baseReportName}-${strippedDateRange()}.csv"
    def reportFile = new File("${reportingContext.outputDirectory}/${fileName}")
    if (reportFile.exists()) {
      reportFile.delete()
    }
    reportFile
  }

  String strippedDateRange() {
    "${noDash('fromDate')}-${noDash('toDate')}"
  }

  String noDash(String fieldName) {
    reportingContext.searchParameters."$fieldName".replace('-','')
  }

}