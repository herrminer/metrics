package com.taulia.metrics.service

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest
import spock.lang.Specification

class RepositoryCsvExporterSpec extends Specification {

  RepositoryCsvExporter exporter

  Team team1
  Team team2

  def setup(){
    PullRequestRepository repository = new PullRequestRepository()

    team1 = new Team(name: 'team1')
    team2 = new Team(name: 'team2')

    User user = new User(userName: 'user1', team: team1)
    User user2 = new User(userName: 'user2', team: team2)

    repository.addPullRequest(user, new PullRequest(repositoryUrl: 'taulia/app-buyer'))
    repository.addPullRequest(user2, new PullRequest(repositoryUrl: 'taulia/app-buyer'))
    repository.addPullRequest(user, new PullRequest(repositoryUrl: 'taulia/app-buyer'))

    exporter = new RepositoryCsvExporter(repository, '/tmp')
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
