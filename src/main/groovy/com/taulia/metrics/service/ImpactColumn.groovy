package com.taulia.metrics.service

import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.math.RoundingMode

class ImpactColumn implements CsvColumn {

  private static Logger logger = LoggerFactory.getLogger(ImpactColumn)

  BigDecimal fullTimeAverage

  @Override
  String getColumnHeader() {
    'Impact'
  }

  @Override
  String getColumnValue(User user) {
    if (!fullTimeAverage) {
      fullTimeAverage = calculateFullTimeAverage(user)
    }
    BigDecimal.valueOf(user.numberOfPullRequests).divide(fullTimeAverage, 2, RoundingMode.HALF_UP)
  }

  BigDecimal calculateFullTimeAverage(User user) {
    List<User> fullTimeDevelopers = user.team.organization.teams*.users.flatten().findAll { u-> u.fullTimeEngineer }
    int totalPullRequests = fullTimeDevelopers*.numberOfPullRequests.sum()
    def average = BigDecimal.valueOf(totalPullRequests / fullTimeDevelopers.size()).setScale(2, RoundingMode.HALF_UP)
    logger.info "calculated ${average} as average PRs for ${fullTimeDevelopers.size()} full-time developers"
    average
  }

}
