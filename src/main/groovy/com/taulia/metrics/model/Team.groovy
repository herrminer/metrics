package com.taulia.metrics.model

class Team {
  String name

  Organization organization

  List<User> users = []

  void addUser(User user) {
    user.team = this
    users.add(user)
  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    Team team = (Team) o

    if (name != team.name) return false

    return true
  }

  int hashCode() {
    return name.hashCode()
  }


  @Override
  public String toString() {
    return "Team{" +
      "name='" + name + '\'' +
      '}';
  }
}
