package com.taulia.metrics.service


import com.taulia.metrics.model.github.PullRequestSearchResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PullRequestSearchService {

  private static Logger logger = LoggerFactory.getLogger(PullRequestSearchService)

  GithubApiClient githubApiClient

  PullRequestSearchService(GithubApiClient githubApiClient) {
    this.githubApiClient = githubApiClient
  }

  PullRequestSearchResponse searchPullRequests(SearchParameters searchParameters) {
    String pathAndQueryString = "/search/issues?${searchParameters.buildParameters()}"
    githubApiClient.getApiResponse(pathAndQueryString, PullRequestSearchResponse)
  }

}
