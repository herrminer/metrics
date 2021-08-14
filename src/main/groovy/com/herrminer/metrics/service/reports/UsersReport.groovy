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
    outputFile << 'team,login,name\n'
    reportingContext.organization.users.each { id, user ->
      outputFile << "${user.team.name},${user.userName},${user.name ?: user.userName}\n"
    }
    outputFile
  }

}
