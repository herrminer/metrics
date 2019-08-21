package com.taulia.metrics.model.github

import com.fasterxml.jackson.annotation.JsonProperty

class PullRequest {
  /*      "url": "https://api.github.com/repos/taulia/app-buyer/issues/288",
      "repository_url": "https://api.github.com/repos/taulia/app-buyer",
      "labels_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/labels{/name}",
      "comments_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/comments",
      "events_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/events",
      "html_url": "https://github.com/taulia/app-buyer/pull/288",
      "id": 475932652,
      "node_id": "MDExOlB1bGxSZXF1ZXN0MzAzNTkwNjA5",
      "number": 288,
      "title": "Support additional subdomain depth for global session cookie",
*/
  String id
  String url
  String title
  int number
  GithubUser user
  List<PullRequestFile> files

  /**
   * Example: https://api.github.com/repos/taulia/app-buyer
   */
  @JsonProperty('repository_url')
  String repositoryUrl

  String getRepositoryName() {
    if (!repositoryUrl) { return null }
    repositoryUrl.substring(repositoryUrl.lastIndexOf('/') + 1, repositoryUrl.length())
  }
}
