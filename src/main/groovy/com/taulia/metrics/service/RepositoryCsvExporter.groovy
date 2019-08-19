package com.taulia.metrics.service


import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

class RepositoryCsvExporter {

  public static final Logger logger = LoggerFactory.getLogger(RepositoryCsvExporter)

  PullRequestRepository pullRequestRepository
  String exportDirectory

  RepositoryCsvExporter(PullRequestRepository pullRequestRepository, String exportDirectory) {
    this.pullRequestRepository = pullRequestRepository
    this.exportDirectory = exportDirectory
  }

  void buildCsvFile() {
    def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
    def fileName = "${exportDirectory}/repositories-${timestamp}.csv"
    def outputFile = new File(fileName)
    outputFile << buildCsvHeader()
    pullRequestRepository.repositories.each { repositoryName ->
      outputFile << buildCsvLine(repositoryName)
    }
    logger.info "exported repositories file ${fileName}"
  }

  String buildCsvHeader() {
    (['repository'] + pullRequestRepository.teams*.name).join(',') + '\n'
  }

  String buildCsvLine(String repositoryName) {
    def line = [repositoryName]
    pullRequestRepository.teams.each {
      def pullRequests = pullRequestRepository.getPullRequests(repositoryName, it)
      line << (pullRequests ? pullRequests.size().toString() : 0.toString())
    }
    line.join(',') + '\n'
  }

}
