package com.herrminer.metrics.model.github

import spock.lang.Specification

class PullRequestSpec extends Specification {

  def "GetRepositoryName happy path"() {
    PullRequest pullRequest = new PullRequest(repositoryUrl: 'https://api.github.com/repos/taulia/app-buyer')

    expect:
    pullRequest.repositoryName == 'app-buyer'
  }

  def "GetRepositoryName no value"() {
    expect:
    new PullRequest().repositoryName == null
  }

}
