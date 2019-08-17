package com.tauila.metrics.service

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.service.CsvExporter
import spock.lang.Specification

class CsvExporterSpec extends Specification {

  User user
  User user2
  User user3
  CsvExporter exporter

  def setup() {
    exporter = new CsvExporter()

    Team team = new Team(name: "team 1")

    user = new User(role: 'Engineer')
    user.numberOfPullRequests = 5
    team.addUser(user)

    user2 = new User(role: 'Manager')
    user2.numberOfPullRequests = 8
    team.addUser(user2)

    user3 = new User(role: 'Quality')
    user3.numberOfPullRequests = 3
    team.addUser(user3)
  }

  def "getPercentOfTeamTotal"() {
    expect:
    exporter.getPercentOfTeamTotal(user) == new BigDecimal(".31")
    exporter.getPercentOfTeamTotal(user2) == new BigDecimal(".50")
    exporter.getPercentOfTeamTotal(user3) == new BigDecimal(".19")
  }

  def "getFairSharePercentage"() {
    expect:
    exporter.getFairSharePercentage(user) == new BigDecimal(".77")
    exporter.getFairSharePercentage(user2) == new BigDecimal("1.23")
    exporter.getFairSharePercentage(user3) == BigDecimal.ZERO
  }

}
