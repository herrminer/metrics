package com.taulia.metrics.service.reports

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class RepositoryContributionReport extends MetricReport {

  public static final Logger logger = LoggerFactory.getLogger(RepositoryContributionReport)

  RepositoryContributionReport(ReportingContext reportingContext) {
    super(reportingContext)
  }

  File buildCsvFile() {
    def outputFile = createReportFile('repositories')
    outputFile << buildCsvHeader()
    reportingContext.pullRequestRepository.repositories.each { repositoryName ->
      outputFile << buildCsvLine(repositoryName)
    }
    outputFile
  }

  String buildCsvHeader() {
    (['repository'] + reportingContext.pullRequestRepository.teams*.name).join(',') + '\n'
  }

  String buildCsvLine(String repositoryName) {
    def line = [repositoryName]
    reportingContext.pullRequestRepository.teams.each {
      def pullRequests = reportingContext.pullRequestRepository.getPullRequests(repositoryName, it)
      line << (pullRequests ? pullRequests.size().toString() : 0.toString())
    }
    line.join(',') + '\n'
  }

}
