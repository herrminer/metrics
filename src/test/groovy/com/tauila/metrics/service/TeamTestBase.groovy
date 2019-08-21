package com.tauila.metrics.service

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest
import com.taulia.metrics.model.github.PullRequestFile
import spock.lang.Specification

class TeamTestBase extends Specification {

  Team team
  User user
  User user2
  User user3

  Organization organization

  def setup() {
    team = new Team(name: "team 1")

    user = new User(userName: 'engineer 1', role: 'Engineer')
    user.numberOfPullRequests = 5
    user.pullRequests = [
      new PullRequest(files: [new PullRequestFile(), new PullRequestFile()]),
      new PullRequest(files: [new PullRequestFile(), new PullRequestFile()]),
      new PullRequest(files: [new PullRequestFile(), new PullRequestFile(), new PullRequestFile()]),
    ]
    team.addUser(user)

    user2 = new User(userName: 'manager 1', role: 'Manager')
    user2.numberOfPullRequests = 8
    team.addUser(user2)

    user3 = new User(userName: 'qe 1', role: 'Quality')
    user3.numberOfPullRequests = 3
    team.addUser(user3)

    Team team2 = new Team(
      name: 'team 2'
    )
    team2.addUser(new User(userName: 'engineer 2', role: 'Engineer', numberOfPullRequests: 2))

    organization = new Organization()
    organization.addTeam(team)
    organization.addTeam(team2)
  }

}
