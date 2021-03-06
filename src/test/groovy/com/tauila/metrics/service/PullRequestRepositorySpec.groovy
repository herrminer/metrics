package com.herrminer.metrics.service

import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import com.herrminer.metrics.model.github.PullRequest
import com.herrminer.metrics.service.PullRequestRepository
import spock.lang.Specification

import java.text.SimpleDateFormat

class PullRequestRepositorySpec extends Specification {

  SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd')

  private PullRequestRepository repository
  private User user
  private Team team

  def setup() {
    repository = new PullRequestRepository()
    team = new Team(name: 'team1')
    user = new User(userName: 'dude', team: team)

    PullRequest pullRequest = new PullRequest(
      repositoryUrl: 'http://foo.com/app-name',
      dateCreated: dateFormat.parse('2019-03-04')
    )
    PullRequest pullRequest2 = new PullRequest(
      repositoryUrl: 'http://foo.com/app-login',
      dateCreated: dateFormat.parse('2019-02-04')
    )
    PullRequest pullRequest3 = new PullRequest(
      repositoryUrl: 'http://foo.com/app-name',
      dateCreated: dateFormat.parse('2019-03-28')
    )

    repository.addPullRequest(user, pullRequest)
    repository.addPullRequest(user, pullRequest2)
    repository.addPullRequest(user, pullRequest3)
  }

  def "get pull requests by user and month"() {
    expect:
    repository.months.size() == 2
    def monthList = repository.months.toList()
    monthList[0] == '2019-02'
    monthList[1] == '2019-03'
    repository.getPullRequests(user, dateFormat.parse('2019-02-01')).size() == 1
    repository.getPullRequests(user, dateFormat.parse('2019-03-01')).size() == 2
  }

  def "get pull requests by repository and team"() {
    expect:
    repository.getPullRequests('app-name', team).size() == 2
    repository.getPullRequests('app-login', team).size() == 1
  }

  def "get pull requests by repository and user"() {
    expect:
    repository.getPullRequests('app-name', user).size() == 2
    repository.getPullRequests('app-login', user).size() == 1
    !repository.getPullRequests('app-foobar', user)
  }

}
