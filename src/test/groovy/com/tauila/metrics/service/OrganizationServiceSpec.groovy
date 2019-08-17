package com.tauila.metrics.service

import com.taulia.metrics.service.OrganizationService
import spock.lang.Specification

class OrganizationServiceSpec extends Specification {

  def "loadOrganization"() {
    when:
    def organization = OrganizationService.loadOrganization()

    then:
    organization
    !organization.teams.empty
    organization.teams.every { !it.users.empty }
  }

}
