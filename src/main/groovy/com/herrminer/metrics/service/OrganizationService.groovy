package com.herrminer.metrics.service

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.model.Role
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OrganizationService {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationService)

  Organization organization

  TeamService teamService

  OrganizationService(TeamService teamService) {
    this.teamService = teamService
    loadOrganization()
  }

  Organization loadOrganization() {
    organization = new Organization()

    teamService.getTeams().each {
      organization.addTeam(it)
    }

    organization
  }

  static Role getRole(String roleName) {
    try {
      return Role.valueOf(roleName)
    } catch (e) {
      logger.error "Invalid role name: ${roleName}, must be one of ${Role.values()}"
      System.exit(1) // hard stop to prevent downstream errors due to non-existent role
    }
  }

}
