package com.taulia.metrics.model

class User {
  String firstName
  String lastName
  String userName
  Team team
  String role
  int numberOfPullRequests

  boolean isSoftwareEngineer() {
    role in ['Manager', 'Engineer']
  }

  boolean isFullTimeEngineer() {
    role in ['Manager', 'Engineer']
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
