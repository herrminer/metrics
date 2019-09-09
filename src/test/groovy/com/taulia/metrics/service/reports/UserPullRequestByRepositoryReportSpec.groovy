package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import spock.lang.Specification

class UserPullRequestByRepositoryReportSpec extends Specification {

  UserPullRequestByRepositoryReport report

  def setup() {
    ReportingContext reportingContext = new ReportingContext()
    report = new UserPullRequestByRepositoryReport(reportingContext)
  }

  def "BuildCsvHeader"() {
    given:
    List<User> users = [
        new User(firstName: 'user', lastName: 'one'),
        new User(firstName: 'user', lastName: 'two')
    ]

    when:
    def result = report.buildCsvHeader(users)

    then:
    result == 'Repository,user one,user two\n'
  }

  def "BuildCsvLine"() {
  }

}
