package com.taulia.metrics.service.reports

class ReportService {

  static Class<? extends MetricReport>[] reportClasses = [
      PullRequestStatisticsReport,
      RepositoryContributionReport,
      UserPullRequestsByMonthReport
  ]

  static Collection<MetricReport> getReports(ReportingContext reportingContext) {
    reportClasses.collect {
      it.newInstance(reportingContext)
    }
  }

}
