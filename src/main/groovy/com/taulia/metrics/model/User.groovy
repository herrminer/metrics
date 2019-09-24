package com.taulia.metrics.model

import com.taulia.metrics.model.github.PullRequest

class User {
  String firstName
  String lastName
  String userName
  Team team
  Role role
  int numberOfPullRequests
  Collection<PullRequest> pullRequests = []

  boolean isSoftwareEngineer() {
    role.isSoftwareEngineer()
  }

  boolean isFullTimeEngineer() {
    role.isFullTimeEngineer()
  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    User user = (User) o

    if (userName != user.userName) return false

    return true
  }

  int hashCode() {
    return userName.hashCode()
  }


  @Override
  String toString() {
    return "User{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", userName='" + userName + '\'' +
      '}';
  }
}
