package com.herrminer.metrics.model

class Organization {
  List<Team> teams = []

  Map<String, User> users = [:]

  void addTeam(Team team) {
    team.organization = this
    teams.add(team)
    users.putAll(team.users.collectEntries {user -> [user.userName, user] })
  }

  def findUser(String userName) {
    users.get(userName)
  }

}
