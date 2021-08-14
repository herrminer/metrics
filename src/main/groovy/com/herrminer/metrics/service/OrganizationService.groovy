package com.herrminer.metrics.service

import com.herrminer.metrics.model.Organization

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

}
