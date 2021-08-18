package com.herrminer.metrics.service

import com.herrminer.metrics.AppConfiguration
import com.herrminer.metrics.model.GithubOrganization
import com.herrminer.metrics.model.GithubTeam
import com.herrminer.metrics.model.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService)

    GithubApiClient githubApiClient
    UserService userService
    Map<String, GithubUser> githubUsers = [:]

    TeamService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
        this.userService = new UserService(githubApiClient)
    }

    List<GithubTeam> getTeams(List<String> organizations) {
        List<GithubTeam> teams = []

        organizations.each { orgName ->
            teams.addAll(getTeamsForOrg(orgName))
        }

        teams.removeAll { team -> team.slug in AppConfiguration.getExcludedTeams() }

        teams.each { team ->
            loadTeamMembers(team)
        }

        teams.sort { a, b -> (a.slug <=> b.slug) }

        teams
    }

    private List<GithubTeam> getTeamsForOrg(String organization) {
        githubApiClient.getAllPages("/orgs/${organization}/teams",
                GithubTeam[],
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh'), 100).each {
            it.githubOrganization = new GithubOrganization(login: organization)
        }
    }

    private List<GithubTeam> getChildTeams(GithubTeam githubTeam) {
        githubApiClient.getApiResponse("/orgs/${githubTeam.githubOrganization.login}/teams/${githubTeam.slug}/teams",
                GithubTeam[],
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh')).each {
            it.githubOrganization = githubTeam.githubOrganization
        }
    }

    private List<GithubUser> loadTeamMembers(GithubTeam team) {
        List<GithubUser> teamMembers =  githubApiClient.getAllPages(
                "/orgs/${team.githubOrganization.login}/teams/${team.slug}/members",
                GithubUser[],
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh'), 100)

        teamMembers.findAll { !(it.login in AppConfiguration.getExcludedUsers()) }.each {
            if (githubUsers.get(it.login)) {
                // move to new team (presumably a child team)
                team.addUser(githubUsers.get(it.login))
            } else {
                GithubUser githubUser = userService.getUser(it.login)
                if (githubUser) {
                    githubUsers.put(githubUser.login, githubUser)
                    team.addUser(githubUser)
                }
            }
        }
    }
}
