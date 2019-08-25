package com.taulia.metrics.service.reports

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.math.RoundingMode

class AverageCalculator {

  private static Logger logger = LoggerFactory.getLogger(AverageCalculator)

  private static BigDecimal fullTimeAverage

  static BigDecimal getFullTimeAverage(Organization organization) {
    if (!fullTimeAverage) {
      fullTimeAverage = calculateFullTimeAverage(organization)
    }
    fullTimeAverage
  }

  static BigDecimal calculateFullTimeAverage(Organization organization) {
    List<User> fullTimeDevelopers = organization.teams*.users.flatten().findAll { u-> u.fullTimeEngineer }
    int totalPullRequests = fullTimeDevelopers*.numberOfPullRequests.sum()
    def average = BigDecimal.valueOf(totalPullRequests / fullTimeDevelopers.size()).setScale(2, RoundingMode.HALF_UP)
    logger.info "calculated ${average} as average PRs for ${fullTimeDevelopers.size()} full-time developers"
    average
  }

}
