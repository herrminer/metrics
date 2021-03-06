package com.herrminer.metrics.service.reports

abstract class MetricReport {

  ReportingContext reportingContext

  MetricReport(ReportingContext reportingContext) {
    this.reportingContext = reportingContext
  }

  abstract File buildCsvFile()

  protected File createReportFile(String reportName, boolean deleteIfExists = true) {
    def fileName = "${strippedDateRange()}-${reportName}.csv"
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