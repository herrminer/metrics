package com.herrminer.metrics.model

import spock.lang.Specification

class RoleSpec extends Specification {

  def "isSoftwareEngineer"() {
    expect:
    Role.Engineer.isSoftwareEngineer()
    Role.Manager.isSoftwareEngineer()
    !Role.Quality.isSoftwareEngineer()
  }

  def "isFullTimeEngineer"() {
    expect:
    Role.Engineer.isFullTimeEngineer()
    !Role.Manager.isFullTimeEngineer()
    !Role.Quality.isFullTimeEngineer()
  }

}
