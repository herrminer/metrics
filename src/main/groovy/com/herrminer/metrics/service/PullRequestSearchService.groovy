package com.herrminer.metrics.service

import com.herrminer.metrics.model.github.PullRequestFile
import com.herrminer.metrics.model.github.PullRequestSearchResponse
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
    logger.debug "querying pull requests: ${pathAndQueryString}"
    def response = githubApiClient.getApiResponse(pathAndQueryString, PullRequestSearchResponse)
    logger.info "got ${response.items.size()} pull requests back"
    response.items.each { pullRequest ->
      pullRequest.files = githubApiClient.getApiResponse(
        "/repos/${searchParameters.org}/${pullRequest.repositoryName}/pulls/${pullRequest.number}/files", PullRequestFile[])
    }
    response
  }

}
