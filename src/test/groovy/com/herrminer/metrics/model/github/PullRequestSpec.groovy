package com.herrminer.metrics.model.github

import spock.lang.Specification

class PullRequestSpec extends Specification {

  def "GetRepositoryName happy path"() {
    PullRequest pullRequest = new PullRequest(repositoryUrl: 'https://api.github.com/repos/herrminer/app-name')

    expect:
    pullRequest.repositoryName == 'app-name'
  }

  def "GetRepositoryName no value"() {
    expect:
    new PullRequest().repositoryName == null
  }

}
