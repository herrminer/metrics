package com.herrminer.metrics.service

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.model.Role
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import com.herrminer.metrics.model.github.PullRequest
import com.herrminer.metrics.model.github.PullRequestFile
import spock.lang.Specification

class TeamTestBase extends Specification {

  Team team
  User user
  User user2
  User user3

  Organization organization

  def setup() {
    team = new Team(name: "team 1")

    user = new User(userName: 'engineer 1', role: Role.Engineer)
    user.numberOfPullRequests = 5
    user.pullRequests = [
      new PullRequest(files: [prf(1,2,3), prf(4,5,6)]),
      new PullRequest(files: [prf(7,8,9), prf(10,11,12)]),
      new PullRequest(files: [prf(13,14,15), prf(16, 17, 18), prf(19,20,21)]),
    ]
    team.addUser(user)

    user2 = new User(userName: 'manager 1', role: Role.Manager)
    user2.numberOfPullRequests = 8
    team.addUser(user2)

    user3 = new User(userName: 'qe 1', role: Role.Quality)
    user3.numberOfPullRequests = 3
    team.addUser(user3)

    Team team2 = new Team(
      name: 'team 2'
    )
    team2.addUser(new User(userName: 'engineer 2', role: Role.Engineer, numberOfPullRequests: 2))

    organization = new Organization()
    organization.addTeam(team)
    organization.addTeam(team2)
  }

  PullRequestFile prf(int additions, int changes, int deletions) {
    new PullRequestFile(
      additions: additions,
      changes: changes,
      deletions: deletions
    )
  }

}
