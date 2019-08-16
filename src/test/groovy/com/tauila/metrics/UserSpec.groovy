package com.tauila.metrics

import com.taulia.metrics.Team
import com.taulia.metrics.User
import spock.lang.Specification

class UserSpec extends Specification {

  User user
  User user2
  User user3

  def setup() {
    Team team = new Team(name: "team 1")

    user = new User(role: 'Engineer')
    user.numberOfPullRequests = 5
    team.addUser(user)

    user2 = new User(role: 'Manager')
    user2.numberOfPullRequests = 8
    team.addUser(user2)

    user3 = new User(role: 'Quality')
    user3.numberOfPullRequests = 0
    team.addUser(user3)

    team.numberOfPullRequests = 13
  }

  def "getPercentOfTeamTotal"() {
    expect:
    user.getPercentOfTeamTotal() == new BigDecimal(".38")
    user.formatAsPercentage(user.getPercentOfTeamTotal()) == '38%'

    user2.getPercentOfTeamTotal() == new BigDecimal(".62")
    user2.formatAsPercentage(user2.getPercentOfTeamTotal()) == '62%'
  }

  def "getFairSharePercentage"() {
    expect:
    user.getFairSharePercentage() == new BigDecimal(".77")
    user.formatAsPercentage(user.getFairSharePercentage()) == '77%'

    user2.getFairSharePercentage() == new BigDecimal("1.23")
    user2.formatAsPercentage(user2.getFairSharePercentage()) == '123%'
  }

}
