package com.taulia.metrics

import com.taulia.metrics.model.Organization
import com.taulia.metrics.service.CsvExporter
import com.taulia.metrics.service.GithubApiClient
import com.taulia.metrics.service.OrganizationService
import com.taulia.metrics.service.PullRequestRepository
import com.taulia.metrics.service.PullRequestSearchService
import com.taulia.metrics.service.RepositoryCsvExporter
import com.taulia.metrics.service.SearchParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GithubApp {

  private static Logger logger = LoggerFactory.getLogger(GithubApp)

  static void main(String[] args) {

    MemoryUtility.printMemoryStatistics('start')

    def credentials = System.getProperty('credentials')

    if (!credentials) {
      println "sorry, no 'credentials' system property"
      System.exit(1)
    }

    GithubApiClient githubApiClient = new GithubApiClient(credentials)
    PullRequestSearchService searchService = new PullRequestSearchService(githubApiClient)

    SearchParameters searchParameters = new SearchParameters(
      fromDate: "2019-02-01",
      toDate: "2019-07-31",
      chunkSize: 45,
    )

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
    new CsvExporter().buildCsvFile(organization, exportDirectory)
    new RepositoryCsvExporter(pullRequestRepository, exportDirectory).buildCsvFile()

    MemoryUtility.printMemoryStatistics('end')
  }
}
