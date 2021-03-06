package com.herrminer.metrics.service.reports


import com.herrminer.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PullRequestStatisticsReport extends MetricReport {

  public static final Logger logger = LoggerFactory.getLogger(PullRequestStatisticsReport)

  List<ReportColumn> columns = [
    new NameColumn(),
//    new UsernameColumn(),
    new TeamColumn(),
    new RoleColumn(),
    new PullRequestsColumn(),
    new AverageColumn(),
    new TeamPullRequestColumn(),
    new PercentOfTeamTotalColumn(),
    new AveragePullRequestFilesColumn(),
    new AveragePrSizeColumn(),
    new FairShareColumn(),
    new ImpactColumn()
  ]

  PullRequestStatisticsReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    def outputFile = createReportFile('pull-requests')
    outputFile << buildCsvHeader()
    reportingContext.organization.teams*.users.flatten().each { user ->
      outputFile << buildCsvLine(user)
    }
    outputFile
  }

  String buildCsvHeader() {
    columns*.columnHeader.join(',') + '\n'
  }

  String buildCsvLine(User user) {
    columns*.getColumnValue(user).join(',') + '\n'
  }

}
