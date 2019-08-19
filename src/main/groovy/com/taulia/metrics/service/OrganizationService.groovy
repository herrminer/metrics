package com.taulia.metrics.service

import com.taulia.metrics.model.Organization
import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User

class OrganizationService {

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

      if (num > 1) {
        String[] parts = line.split(',')

        def user = new User(
          firstName: parts[0],
          lastName: parts[1],
          userName: parts[2],
          role: parts[4]
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

}
