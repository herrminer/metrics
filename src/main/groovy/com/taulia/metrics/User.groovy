package com.taulia.metrics

import java.math.RoundingMode

class User {
  String firstName
  String lastName
  String userName
  Team team
  String role
  int numberOfPullRequests

  static String buildCsvHeader() {
    "First,Last,Github,Team,Role,PRs,Team PRs, Percent of Team, Fair Share Percentage\n"
  }

  String buildCsvLine() {
    [
      firstName,
      lastName,
      userName,
      team.name,
      role,
      numberOfPullRequests,
      team.numberOfPullRequests,
      percentOfTeamTotal,
      fairSharePercentage
    ].join(',') + '\n'
  }

  String formatAsPercentage(BigDecimal value) {
    return "${(value * 100).setScale(0)}%"
  }

  BigDecimal getPercentOfTeamTotal() {
    if (team.numberOfPullRequests == 0) { return BigDecimal.ZERO }
    return BigDecimal.valueOf(numberOfPullRequests)
      .divide(BigDecimal.valueOf(team.numberOfPullRequests), 2, RoundingMode.HALF_UP)
  }

  BigDecimal getFairSharePercentage() {
    if (team.numberOfPullRequests == 0) { return BigDecimal.ZERO }
    def developers = team.users.findAll({ it.softwareEngineer })
    double fairShareAmount = team.numberOfPullRequests / developers.size()
    return BigDecimal.valueOf(numberOfPullRequests)
      .divide(BigDecimal.valueOf(fairShareAmount), 2, RoundingMode.HALF_UP)
  }

  boolean isSoftwareEngineer() {
    role in ['Manager', 'Engineer']
  }
}
