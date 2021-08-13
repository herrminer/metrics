package com.herrminer.metrics.service.reports


import com.herrminer.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class PullRequestsReport extends MetricReport {

  public static final Logger logger = LoggerFactory.getLogger(PullRequestsReport)

  static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  PullRequestsReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  @Override
  File buildCsvFile() {
    def outputFile = createReportFile('pull-requests-data')
    outputFile << 'id,title,user login,user name,repository,time dimension,created,closed,changes,url\n'
    reportingContext.organization.teams*.users.flatten().each { user ->
      outputFile << buildPullRequestLines(user)
    }
    outputFile
  }

  static String buildPullRequestLines(User user) {
    def returnValue = new StringBuilder()
    user.pullRequests.each { pr ->
      returnValue.append("${pr.id},")
      returnValue.append("${pr.title.replace(',','')},")
      returnValue.append("${user.userName},")
      returnValue.append("${user.name ?: user.userName},")
      returnValue.append("${pr.repositoryName},")
      returnValue.append("${dateFormatter.format(pr.dateClosed)},")
      returnValue.append("${dateFormatter.format(pr.dateCreated)},")
      returnValue.append("${dateFormatter.format(pr.dateClosed)},")
      returnValue.append("${pr.files.flatten()*.changes.sum() ?: 0},")
      returnValue.append("${pr.htmlUrl},")
      returnValue.append("\n")
    }
    returnValue
  }

}
