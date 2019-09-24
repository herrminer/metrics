package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserPullRequestByRepositoryReport extends MetricReport {

  private static final Logger logger = LoggerFactory.getLogger(UserPullRequestByRepositoryReport)

  UserPullRequestByRepositoryReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    List<User> users = reportingContext.organization.teams*.users.flatten()

    def outputFile = createReportFile('user-prs-by-repo')

    outputFile << buildCsvHeader(users)

    reportingContext.pullRequestRepository.repositories.each { repository ->
      outputFile << buildCsvLine(repository, users)
    }

    outputFile
  }

  String buildCsvLine(String repository, Collection<User> users) {
    Closure<String> transformer = { User user ->
      reportingContext.pullRequestRepository.getPullRequests(repository, user).size().toString()
    }
    ([repository] + users.collect(transformer)).join(',') + '\n'
  }

  static String buildCsvHeader(List<User> users) {
    (['Repository'] + users.collect {
      [it.firstName, it.lastName].join(' ')
    }).join(',') + '\n'
  }
}
