package com.taulia.metrics.model

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User

class Organization {
  List<Team> teams = []

  private Map<String, User> users

  void addTeam(Team team) {
    team.organization = this
    teams.add(team)
  }

  def findUser(String userName) {
    if (users == null) {
      users = loadUsersMap()
    }
    users.get(userName)
  }

  Map<String, User> loadUsersMap() {
    Map<String, User> map = [:]
    teams.each {
      map.putAll(it.users.collectEntries { user -> [(user.userName):user] })
    }
    map
  }
}
