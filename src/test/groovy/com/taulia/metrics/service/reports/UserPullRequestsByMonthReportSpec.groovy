package com.taulia.metrics.service.reports

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest
import com.taulia.metrics.service.PullRequestRepository
import com.taulia.metrics.service.SearchParameters
import spock.lang.Specification

import java.text.SimpleDateFormat

class UserPullRequestsByMonthReportSpec extends Specification {

  UserPullRequestsByMonthReport report
  Team team = new Team(name: 'team1')
  User user = new User(userName: 'user1', team: team)
  User user2 = new User(userName: 'user2', team: team)

  def setup() {

    SearchParameters parameters = new SearchParameters(fromDate: '2019-01-01', toDate: '2019-03-31')

    PullRequestRepository repository = new PullRequestRepository()

    SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd')

    PullRequest pr = new PullRequest(repositoryUrl: '/app-login')

    pr.dateCreated = dateFormat.parse('2019-01-01')
    repository.addPullRequest(user, pr)

    pr.dateCreated = dateFormat.parse('2019-03-01')
    repository.addPullRequest(user, pr)

    pr.dateCreated = dateFormat.parse('2019-02-01')
    repository.addPullRequest(user, pr)

    pr.dateCreated = dateFormat.parse('2019-03-02')
    repository.addPullRequest(user, pr)

    ReportingContext context = new ReportingContext(
      pullRequestRepository: repository,
      searchParameters: parameters
    )

    report = new UserPullRequestsByMonthReport(context)
  }

  def "BuildCsvHeader"() {
    expect:
    report.buildCsvHeader() == 'User,Team,January,February,March\n'
  }

  def "buildCsvLine"() {
    expect:
    report.buildCsvLine(user) == 'user1,team1,1,1,2\n'
  }

  def "buildCsvLine no pull requests for user"() {
    expect:
    report.buildCsvLine(user2) == 'user2,team1,0,0,0\n'
  }

}
