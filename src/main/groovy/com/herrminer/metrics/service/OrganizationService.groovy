package com.herrminer.metrics.service

import com.herrminer.metrics.model.Organization

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OrganizationService {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationService)

  TeamService teamService

  OrganizationService(TeamService teamService) {
    this.teamService = teamService
  }

  Organization loadOrganization(List<String> githubOrganizations) {
    Organization organization = new Organization(
      githubOrganizations: githubOrganizations
    )

    teamService.getTeams(githubOrganizations).each {
      organization.addTeam(it)
    }

    organization
  }

}
