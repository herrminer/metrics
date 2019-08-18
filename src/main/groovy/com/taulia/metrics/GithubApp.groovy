package com.taulia.metrics

import com.taulia.metrics.model.Organization
import com.taulia.metrics.service.CsvExporter
import com.taulia.metrics.service.OrganizationService
import com.taulia.metrics.service.PullRequestSearchService
import com.taulia.metrics.service.SearchParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GithubApp {

  private static Logger logger = LoggerFactory.getLogger(GithubApp)

  static void main(String[] args) {

    def credentials = System.getProperty('credentials')

    if (!credentials) {
      println "sorry, no 'credentials' system property"
      System.exit(1)
    }

    PullRequestSearchService searchService = new PullRequestSearchService(credentials)

    SearchParameters searchParameters = new SearchParameters(
      fromDate: "2019-02-01",
      toDate: "2019-07-31",
      chunkSize: 45,
    )

    Organization organization = OrganizationService.organization

    while (searchParameters.advanceChunkDates()) {

      boolean moreResultsAvailable = true

      while (moreResultsAvailable) {
        logger.info "searching from ${searchParameters.fromDate} to ${searchParameters.toDate}, page ${searchParameters.page}"
        def searchResponse = searchService.searchPullRequests(searchParameters)

        if (searchResponse.message) {
          logger.error "ERROR MESSAGE: ${searchResponse.message}"
          System.exit(1)
        }

        searchResponse.items.each { pullRequest ->
          def user = organization.findUser(pullRequest.user.login)
          if (user) {
            logger.debug "adding to total for username ${pullRequest.user.login}, user ${user}"
            user.numberOfPullRequests++
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

    new CsvExporter().buildCsvFile(organization, "${System.getenv('HOME')}/Downloads/metrics")

  }
}
