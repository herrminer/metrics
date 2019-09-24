package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class UserPullRequestsByMonthReport extends MetricReport {

  private static final Logger logger = LoggerFactory.getLogger(UserPullRequestsByMonthReport)

  SimpleDateFormat readFormat = new SimpleDateFormat('yyyy-MM')
  SimpleDateFormat writeFormat = new SimpleDateFormat('MMMMM')

  UserPullRequestsByMonthReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    def outputFile = createReportFile('user-prs-by-month')
    outputFile << buildCsvHeader()
    reportingContext.organization.teams*.users.flatten().each { user ->
      outputFile << buildCsvLine(user)
    }
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
