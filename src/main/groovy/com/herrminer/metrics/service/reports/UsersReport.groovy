package com.herrminer.metrics.service.reports

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UsersReport extends MetricReport {

  public static final Logger logger = LoggerFactory.getLogger(UsersReport)

  UsersReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    def outputFile = createReportFile('users')
    outputFile << 'team,login,name,url\n'
    reportingContext.organization.users.each { id, user ->
      outputFile << "${user.githubTeam.name},${user.login},${user.name ?: user.login},${user.url}\n"
    }
    outputFile
  }

}
