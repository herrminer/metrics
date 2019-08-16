package com.taulia.metrics

class GithubApp {

  static void main(String[] args) {
    if (!System.getProperty('credentials')) {
      println "sorry, no 'credentials' system property"
      System.exit(1)
    }

    PullRequestSearchService searchService = new PullRequestSearchService()

    SearchParameters searchParameters = new SearchParameters(
      fromDate: "2019-02-01",
      toDate: "2019-07-31",
      chunkSize: 45,
    )

    ProcessingContext context = new ProcessingContext(users: getUserMap(), searchParameters: searchParameters)

    while (searchParameters.advanceChunkDates()) {


      boolean moreResultsAvailable = true

      while (moreResultsAvailable) {
        println "searching from ${searchParameters.fromDate} to ${searchParameters.toDate}, page ${searchParameters.page}"
        def searchResponse = searchService.searchPullRequests(searchParameters)

        if (searchResponse.message) {
          println "ERROR MESSAGE: ${searchResponse.message}"
          System.exit(1)
        }

        processSearchResults(searchResponse, context)

        moreResultsAvailable = searchResponse.totalCount > (searchParameters.page * searchParameters.pageSize)

        if (moreResultsAvailable) {
//          println "${searchResponse.totalCount} > ${searchParameters.page * searchParameters.pageSize}, so fetching more"
          searchParameters.incrementPage()
        }

        sleep(1000)
      }
    }

    doOutput(context)

  }

  static void processSearchResults(PullRequestSearchResponse response, ProcessingContext context) {
    response.items.each { pullRequest ->

      def user = context.users.get(pullRequest.user.login)

      if (user) {
        user.numberOfPullRequests++
        if (user.softwareEngineer)
          user.team.numberOfPullRequests++
      }

    }
  }

  static void doOutput(ProcessingContext context) {
    def outputFile = new File("pr-metrics.csv")
    if (outputFile.exists()) outputFile.delete()
    outputFile << User.buildCsvHeader()
    context.users.each { k, user ->
      outputFile << user.buildCsvLine()
    }
  }

  static Map<String, User> getUserMap() {
    UserService.getUsers().collectEntries {
      [it.userName, it]
    }
  }
}
