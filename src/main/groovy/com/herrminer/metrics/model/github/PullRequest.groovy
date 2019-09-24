package com.herrminer.metrics.model.github

import com.fasterxml.jackson.annotation.JsonProperty

class PullRequest {
  String id
  String url
  String title
  int number
  GithubUser user
  PullRequestFile[] files

  @JsonProperty('created_at')
  Date dateCreated

  @JsonProperty('closed_at')
  Date dateClosed

  /**
   * Example: https://api.github.com/repos/herrminer/app-name
   */
  @JsonProperty('repository_url')
  String repositoryUrl

  String getRepositoryName() {
    if (!repositoryUrl) { return null }
    repositoryUrl.substring(repositoryUrl.lastIndexOf('/') + 1, repositoryUrl.length())
  }
}
