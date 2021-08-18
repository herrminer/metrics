package com.herrminer.metrics.service

import com.herrminer.metrics.AppConfiguration
import com.herrminer.metrics.model.GithubOrganization
import com.herrminer.metrics.model.GithubTeam
import com.herrminer.metrics.model.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService)

//    private static List<String> teamNames = [
//        'SyscoCorporation/syscolabs-customer-experience-engineering',
//        'syscolabs-customer-experience-disco',
//        'syscolabs-customer-experience-mango',
//    ]

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
            // first add parent teams for the org
            def parentTeams = getTeamsForOrg(orgName)
            teams.addAll(parentTeams)

            // then add child teams
            parentTeams.each { parentTeam ->
                teams.addAll(getChildTeams(parentTeam))
            }
        }

        // now load the team members
        teams.each { team ->
            loadTeamMembers(team)
        }

        teams
    }

    private List<GithubTeam> getTeamsForOrg(String organization) {
        List<GithubTeam> teams = []
        int page = 1, resultCount = 1
        while (resultCount > 0) {
            def result = githubApiClient.getApiResponse("/orgs/${organization}/teams?page=${page}",
                    GithubTeam[],
                    AppConfiguration.getConfigurationAsBoolean('github.org.refresh')).each {
                it.githubOrganization = new GithubOrganization(login: organization)
            }
            teams.addAll(result)
            resultCount = result.size()
            page++
        }
        teams
    }

    private List<GithubTeam> getChildTeams(GithubTeam githubTeam) {
        githubApiClient.getApiResponse("/orgs/${githubTeam.githubOrganization.login}/teams/${githubTeam.slug}/teams",
                GithubTeam[],
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh')).each {
            it.githubOrganization = githubTeam.githubOrganization
        }
    }

    private List<GithubUser> loadTeamMembers(GithubTeam team) {
        def perPage = 100
        def resultSize = 100
        def page = 1

        while (resultSize == perPage) {
            def result = githubApiClient.getApiResponse(
                    "/orgs/${team.githubOrganization.login}/teams/${team.slug}/members?per_page=100&page=${page}",
                    GithubUser[],
                    AppConfiguration.getConfigurationAsBoolean('github.org.refresh'))

            result.findAll { !(it.login in AppConfiguration.getExcludedUsers()) }.each {
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

            resultSize = result.size()
            page++
        }
    }
}
