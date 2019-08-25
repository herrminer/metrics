package com.taulia.metrics.service

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest
import com.taulia.metrics.service.reports.RepositoryContributionReport
import spock.lang.Specification

import java.text.SimpleDateFormat

class RepositoryContributionReportSpec extends Specification {

  RepositoryContributionReport exporter

  Team team1
  Team team2

  def setup(){
    PullRequestRepository repository = new PullRequestRepository()

    team1 = new Team(name: 'team1')
    team2 = new Team(name: 'team2')

    User user = new User(userName: 'user1', team: team1)
    User user2 = new User(userName: 'user2', team: team2)

    SimpleDateFormat format = new SimpleDateFormat('yyyy-MM-dd')
    Date dateCreated = format.parse('2019-01-01')
    repository.addPullRequest(user, new PullRequest(repositoryUrl: 'taulia/app-buyer', dateCreated: dateCreated))
    repository.addPullRequest(user2, new PullRequest(repositoryUrl: 'taulia/app-buyer', dateCreated: dateCreated))
    repository.addPullRequest(user, new PullRequest(repositoryUrl: 'taulia/app-buyer', dateCreated: dateCreated))

    exporter = new RepositoryContributionReport(repository, '/tmp')
  }

  def "BuildCsvFile"() {
    expect:
    exporter.buildCsvFile()
  }

  def "BuildCsvHeader"() {
    expect:
    exporter.buildCsvHeader() == 'repository,team1,team2\n'
  }

  def "BuildCsvLine"() {
    expect:
    exporter.buildCsvLine('app-buyer') == 'app-buyer,2,1\n'
  }

}
