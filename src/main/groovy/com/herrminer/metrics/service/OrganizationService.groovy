package com.herrminer.metrics.service

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.model.Role
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OrganizationService {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationService)

  private static Organization organization

  static Organization getOrganization() {
    if (!organization) {
      organization = loadOrganization()
    }
    organization
  }

  static Organization loadOrganization() {
    Organization organization = new Organization()
    Map<String, Team> teams = new HashMap<>()

    List<User> users = new ArrayList<>()
    new File(getClass().getResource("/organization.csv").toURI()).eachLine { line, num ->

      if (num > 1 && line.length()) {
        String[] parts = line.split(',')

        if (parts.length != 5) {
          logger.error "line has incorrect number of fields: ${line}\n"
          return
        }

        def user = new User(
          firstName: parts[0].trim(),
          lastName: parts[1].trim(),
          userName: parts[2].trim(),
          role: getRole(parts[4].trim())
        )

        def teamName = parts[3]

        if (!teams.containsKey(teamName)) {
          teams.put(teamName, new Team(name: teamName))
        }

        teams.get(teamName).addUser(user)

        users.add(user)
      }
    }

    teams.values().each {
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
