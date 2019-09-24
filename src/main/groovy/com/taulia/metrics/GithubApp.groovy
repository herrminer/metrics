package com.taulia.metrics

import com.taulia.metrics.model.Organization
import com.taulia.metrics.service.*
import com.taulia.metrics.service.reports.ReportService
import com.taulia.metrics.service.reports.ReportingContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GithubApp {

  private static Logger logger = LoggerFactory.getLogger(GithubApp)

  static void main(String[] args) {

    MemoryUtility.printMemoryStatistics('start')

    Properties props = new Properties()
    props.load(this.getResourceAsStream('/metrics.properties') as InputStream)

    def credentials = props.getProperty('credentials')

    if (!credentials) {
      println "sorry, no 'credentials' entry in metrics.properties"
      System.exit(1)
    }

    GithubApiClient githubApiClient = new GithubApiClient(credentials)
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

    def exportDirectory = "${System.getenv('HOME')}/Downloads"

    ReportingContext reportingContext = new ReportingContext(
      searchParameters: searchParameters,
      organization: organization,
      pullRequestRepository: pullRequestRepository,
      exportDirectory: exportDirectory
    )

    ReportService.getReports(reportingContext).each {
      def exportedFile = it.buildCsvFile()
      logger.info "exported ${exportedFile.absolutePath}"
    }

    MemoryUtility.printMemoryStatistics('end')
  }
}
