package com.herrminer.metrics.service

import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import com.herrminer.metrics.model.github.PullRequest
import com.herrminer.metrics.service.reports.ReportingContext
import com.herrminer.metrics.service.reports.RepositoryContributionReport
import spock.lang.Specification

import java.text.SimpleDateFormat

class RepositoryContributionReportSpec extends Specification {

  RepositoryContributionReport report

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

    ReportingContext reportingContext = new ReportingContext(
        pullRequestRepository: repository,
        exportDirectory: '/tmp',
        searchParameters: new SearchParameters(fromDate: '2019-01-01', toDate: '2019-01-31')
    )
    report = new RepositoryContributionReport(reportingContext)
  }

  def "BuildCsvFile"() {
    expect:
    report.buildCsvFile()
  }

  def "BuildCsvHeader"() {
    expect:
    report.buildCsvHeader() == 'repository,team1,team2\n'
  }

  def "BuildCsvLine"() {
    expect:
    report.buildCsvLine('app-buyer') == 'app-buyer,2,1\n'
  }

}
