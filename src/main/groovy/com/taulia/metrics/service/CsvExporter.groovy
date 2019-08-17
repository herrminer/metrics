package com.taulia.metrics.service

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.User

import java.math.RoundingMode
import java.text.SimpleDateFormat

class CsvExporter {

  void buildCsvFile(Organization organization, String pathName) {
    def fileName = "${pathName}-${new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())}"
    def outputFile = new File(fileName)
    if (outputFile.exists()) outputFile.delete()
    outputFile << buildCsvHeader()
    organization.teams*.users.flatten().each { user ->
      outputFile << buildCsvLine(user)
    }
  }

  private static String buildCsvHeader() {
    "First,Last,Github,Team,Role,PRs,Team PRs, Percent of Team, Fair Share Percentage\n"
  }

  private String buildCsvLine(User user) {
    [
      user.firstName,
      user.lastName,
      user.userName,
      user.team.name,
      user.role,
      user.numberOfPullRequests,
      user.team.users*.numberOfPullRequests.sum(),
      getPercentOfTeamTotal(user),
      getFairSharePercentage(user)
    ].join(',') + '\n'
  }

  BigDecimal getPercentOfTeamTotal(User user) {
    int teamPullRequestCount = (int) user.team.users*.numberOfPullRequests.sum()
    if (!teamPullRequestCount) { return BigDecimal.ZERO }
    return BigDecimal.valueOf(user.numberOfPullRequests)
      .divide(BigDecimal.valueOf(teamPullRequestCount), 2, RoundingMode.HALF_UP)
  }

  BigDecimal getFairSharePercentage(User user) {
    if (!user.softwareEngineer) { return BigDecimal.ZERO }
    def softwareEngineers = user.team.users.findAll({ it.softwareEngineer })
    int developerPullRequestTotal = (int) softwareEngineers*.numberOfPullRequests.sum()
    if (developerPullRequestTotal == 0) { return BigDecimal.ZERO }
    double fairShareAmount = developerPullRequestTotal / softwareEngineers.size()
    return BigDecimal.valueOf(user.numberOfPullRequests)
      .divide(BigDecimal.valueOf(fairShareAmount), 2, RoundingMode.HALF_UP)
  }


}
