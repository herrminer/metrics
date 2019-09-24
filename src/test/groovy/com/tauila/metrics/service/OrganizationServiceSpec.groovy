package com.herrminer.metrics.service

import com.herrminer.metrics.service.OrganizationService
import spock.lang.Specification

class OrganizationServiceSpec extends Specification {

  def "loadOrganization"() {
    when:
    def organization = OrganizationService.loadOrganization()

    then:
    organization
    !organization.teams.empty
    organization.teams.every { !it.users.empty && it.organization }
  }

}
