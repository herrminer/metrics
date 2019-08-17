package com.taulia.metrics

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.User
import com.taulia.metrics.service.CsvExporter
import com.taulia.metrics.service.OrganizationService

import com.taulia.metrics.service.PullRequestSearchService
import com.taulia.metrics.service.SearchParameters

class GithubApp {

  static void main(String[] args) {
    if (!System.getProperty('credentials')) {
      println "sorry, no 'credentials' system property"
      System.exit(1)
    }

    PullRequestSearchService searchService = new PullRequestSearchService()

    SearchParameters searchParameters = new SearchParameters(
      fromDate: "2019-02-01",
      toDate: "2019-02-15",
      chunkSize: 45,
    )

    Organization organization = OrganizationService.organization

    while (searchParameters.advanceChunkDates()) {

      boolean moreResultsAvailable = true

      while (moreResultsAvailable) {
        println "searching from ${searchParameters.fromDate} to ${searchParameters.toDate}, page ${searchParameters.page}"
        def searchResponse = searchService.searchPullRequests(searchParameters)

        if (searchResponse.message) {
          println "ERROR MESSAGE: ${searchResponse.message}"
          System.exit(1)
        }

        searchResponse.items.each { pullRequest ->
          def user = organization.findUser(pullRequest.user.login)
          if (user) {
            println "adding to total for username ${pullRequest.user.login}, user ${user}"
            user.numberOfPullRequests++
          }
        }

        moreResultsAvailable = searchResponse.totalCount > (searchParameters.page * searchParameters.pageSize)

        if (moreResultsAvailable) {
          searchParameters.incrementPage()
        }

        sleep(1000)
      }
    }

    new CsvExporter().buildCsvFile(organization, "metrics.csv")

  }
}
