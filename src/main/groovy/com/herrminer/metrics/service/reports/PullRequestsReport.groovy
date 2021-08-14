package com.herrminer.metrics.service.reports


import com.herrminer.metrics.model.github.GithubUser
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
    outputFile << 'repository,id,title,time dimension,created,closed,files,changes,url,author\n'
    reportingContext.organization.users.each { id, user ->
      outputFile << buildPullRequestLines(user)
    }
    outputFile
  }

  static String buildPullRequestLines(GithubUser user) {
    def returnValue = new StringBuilder()
    user.pullRequests.each { pr ->
      returnValue.append("${pr.repositoryName},")
      returnValue.append("${pr.id},")
      returnValue.append("${pr.title.replace(',','')},")
      returnValue.append("${dateFormatter.format(pr.dateClosed)},")
      returnValue.append("${dateFormatter.format(pr.dateCreated)},")
      returnValue.append("${dateFormatter.format(pr.dateClosed)},")
      returnValue.append("${pr.files.size() ?: 0},")
      returnValue.append("${pr.files.flatten()*.changes.sum() ?: 0},")
      returnValue.append("${pr.htmlUrl},")
      returnValue.append("${user.login},")
      returnValue.append("\n")
    }
    returnValue
  }

}
