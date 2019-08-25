package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class UserPullRequestsByMonthReport {

  private static final Logger logger = LoggerFactory.getLogger(UserPullRequestsByMonthReport)

  ReportingContext reportingContext
  SimpleDateFormat readFormat = new SimpleDateFormat('yyyy-MM')
  SimpleDateFormat writeFormat = new SimpleDateFormat('MMMMM')

  UserPullRequestsByMonthReport(ReportingContext reportingContext) {
    this.reportingContext = reportingContext
  }

  File buildCsv() {
    def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
    def fileName = "${reportingContext.exportDirectory}/user-pull-requests-by-month-${timestamp}.csv"
    def outputFile = new File(fileName)
    outputFile << buildCsvHeader()
    reportingContext.organization.teams*.users.flatten().each { user ->
      outputFile << buildCsvLine(user)
    }
    logger.info "exported user pull request by month repor ${fileName}"
    outputFile
  }

  String buildCsvLine(User user) {
    Closure<String> transformer = { String month ->
      reportingContext.pullRequestRepository.getPullRequests(user, readFormat.parse(month)).size().toString()
    }
    ([user.userName, user.team.name] + reportingContext.pullRequestRepository.months.collect(transformer)).join(',') + '\n'
  }

  String buildCsvHeader() {
    (['User','Team'] + reportingContext.pullRequestRepository.months.collect {
      writeFormat.format(readFormat.parse(it))
    }).join(',') + '\n'
  }
}
