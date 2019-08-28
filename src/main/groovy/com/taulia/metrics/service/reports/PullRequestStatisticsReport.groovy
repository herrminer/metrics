package com.taulia.metrics.service.reports

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class PullRequestStatisticsReport {

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

  void buildCsvFile(Organization organization, String exportDirectory) {
    def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
    def fileName = "${exportDirectory}/metrics-${timestamp}.csv"
    def outputFile = new File(fileName)
    if (outputFile.exists()) outputFile.delete()
    outputFile << buildCsvHeader()
    organization.teams*.users.flatten().each { user ->
      outputFile << buildCsvLine(user)
    }
    logger.info "exported file ${fileName}"
  }

  String buildCsvHeader() {
    columns*.columnHeader.join(',') + '\n'
  }

  String buildCsvLine(User user) {
    columns*.getColumnValue(user).join(',') + '\n'
  }

}
