package com.herrminer.metrics.service.reports

class ReportService {

  static Class<? extends MetricReport>[] reportClasses = [
          PullRequestsReport
  ]

  static Collection<MetricReport> getReports(ReportingContext reportingContext) {
    reportClasses.collect {
      it.newInstance(reportingContext)
    }
  }

}
