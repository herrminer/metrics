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
    def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
    def fileName = "${reportingContext.exportDirectory}/repositories-${timestamp}.csv"
    def outputFile = new File(fileName)
    outputFile << buildCsvHeader()
    reportingContext.pullRequestRepository.repositories.each { repositoryName ->
      outputFile << buildCsvLine(repositoryName)
    }
    logger.info "exported repositories file ${fileName}"
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
