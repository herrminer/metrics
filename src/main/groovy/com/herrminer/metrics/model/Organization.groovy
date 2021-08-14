package com.herrminer.metrics.model

import com.herrminer.metrics.model.github.GithubTeam
import com.herrminer.metrics.model.github.GithubUser

class Organization {
  List<GithubTeam> teams = []

  Map<String, GithubUser> users = [:]

  void addTeam(GithubTeam team) {
    team.organization = this
    teams.add(team)
    users.putAll(team.githubUsers.collectEntries {user -> [user.login, user] })
  }

  def findUser(String userName) {
    users.get(userName)
  }

}
