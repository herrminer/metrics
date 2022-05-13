package com.herrminer.metrics.model

class Organization {

  List<String> githubOrganizations

  List<GithubTeam> teams = []

  GithubTeam unknownTeam = new GithubTeam(name: "unknown")

  Map<String, GithubUser> users = [:]

  void addTeam(GithubTeam team) {
    team.organization = this
    teams.add(team)
    users.putAll(team.githubUsers.collectEntries {user -> [user.login, user] })
  }

  def findUser(String userName) {
    users.get(userName)
  }

  def findOrCreateUser(String userName) {
    GithubUser user = findUser(userName)
    if (!user) {
      user = new GithubUser(login: userName, githubTeam: unknownTeam)
      users.put(userName, user)
    }
    user
  }

}
