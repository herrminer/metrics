package com.taulia.metrics

class Team {
  String name

  List<User> users = new ArrayList<>()

  int numberOfPullRequests = 0

  void addUser(User user) {
    user.team = this
    users.add(user)
  }
}
