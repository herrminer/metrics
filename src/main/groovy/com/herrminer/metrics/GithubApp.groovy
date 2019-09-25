package com.herrminer.metrics

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.service.*
import com.herrminer.metrics.service.reports.ReportService
import com.herrminer.metrics.service.reports.ReportingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GithubApp {

  private static Logger logger = LoggerFactory.getLogger(GithubApp)

  static void main(String[] args) {

    MemoryUtility.printMemoryStatistics('start')

    Properties props = new Properties()
    props.load(this.getResourceAsStream('/metrics.properties') as InputStream)

    GithubApiClient githubApiClient = new GithubApiClient(props)
    PullRequestSearchService searchService = new PullRequestSearchService(githubApiClient)

    SearchParameters searchParameters = new SearchParameters(props)

    Organization organization = OrganizationService.organization
    PullRequestRepository pullRequestRepository = new PullRequestRepository()

    while (searchParameters.advanceChunkDates()) {

      boolean moreResultsAvailable = true

      while (moreResultsAvailable) {
        def searchResponse = searchService.searchPullRequests(searchParameters)

        if (searchResponse.message) {
          logger.error "ERROR MESSAGE: ${searchResponse.message}"
          System.exit(1)
        }

        searchResponse.items.each { pullRequest ->
          def user = organization.findUser(pullRequest.user.login)
          if (user) {
            logger.debug "adding pull request for user ${pullRequest.user.login}, user ${user}"
            user.pullRequests.add(pullRequest)
            user.numberOfPullRequests++
            pullRequestRepository.addPullRequest(user, pullRequest)
          } else {
            logger.debug "UNKNOWN USER: ${pullRequest.user.login}"
          }
        }

        moreResultsAvailable = searchResponse.totalCount > (searchParameters.page * searchParameters.pageSize)

        if (moreResultsAvailable) {
          searchParameters.incrementPage()
        }
      }
    }

    ReportingContext reportingContext = new ReportingContext(
      searchParameters: searchParameters,
      organization: organization,
      pullRequestRepository: pullRequestRepository,
      outputDirectory: getOutputDirectoryLocation(props)
    )

    ReportService.getReports(reportingContext).each {
      def exportedFile = it.buildCsvFile()
      logger.info "exported ${exportedFile.absolutePath}"
    }

    MemoryUtility.printMemoryStatistics('end')
  }

  static String getOutputDirectoryLocation(Properties props) {
    def outputDirectory = props.getProperty('output.directory') ?: "${System.getenv('HOME')}/metrics/reports"
    File directory = new File(outputDirectory)
    if (!directory.exists()) directory.mkdirs()
    outputDirectory
  }
}
