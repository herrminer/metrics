package com.herrminer.metrics.model

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import spock.lang.Specification

class OrganizationSpec extends Specification {

  Organization organization

  def setup() {
    organization = new Organization()

    Team team1 = new Team(name: 'team1')
    User user = new User(userName: 'team1user1', team: team1)
    User user2 = new User(userName: 'team1user2', team: team1)
    team1.users = [user, user2]
    organization.addTeam(team1)

    Team team2 = new Team(name: 'team2')
    User user3 = new User(userName: 'team2user1', team: team1)
    User user4 = new User(userName: 'team2user2', team: team1)
    team2.users = [user3, user4]
    organization.addTeam(team2)
  }

  def "findUser"() {
    expect:
    organization.findUser('team1user1')
    organization.findUser('team2user1')
  }

}
