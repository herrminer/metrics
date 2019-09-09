package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class UserPullRequestByRepositoryReport extends MetricReport {

  private static final Logger logger = LoggerFactory.getLogger(UserPullRequestByRepositoryReport)

  UserPullRequestByRepositoryReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    List<User> users = reportingContext.organization.teams*.users.flatten()

    def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
    def fileName = "${reportingContext.exportDirectory}/user-pr-by-repo-${timestamp}.csv"
    def outputFile = new File(fileName)

    outputFile << buildCsvHeader(users)

    reportingContext.pullRequestRepository.repositories.each { repository ->
      outputFile << buildCsvLine(repository, users)
    }

    logger.info "exported user pull request by repository report ${fileName}"

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
