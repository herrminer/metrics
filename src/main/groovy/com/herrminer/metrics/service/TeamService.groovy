package com.herrminer.metrics.service

import com.herrminer.metrics.AppConfiguration
import com.herrminer.metrics.model.GithubTeam
import com.herrminer.metrics.model.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService)

    private static List<String> teamNames = [
        'syscolabs-customer-experience-engineering',
        'syscolabs-customer-experience-disco',
        'syscolabs-customer-experience-mango',
    ]

    GithubApiClient githubApiClient
    UserService userService
    Map<String, GithubUser> githubUsers = [:]

    TeamService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
        this.userService = new UserService(githubApiClient)
    }

    List<GithubTeam> getTeams() {
        List<GithubTeam> teams = []

        // add parent teams first
        teamNames.each { teamSlug ->
            teams.add(getTeam(teamSlug))
        }

        // then add child teams
        teamNames.each { teamSlug ->
            teams.addAll(getChildTeams(teamSlug))
        }

        // now load the team members
        teams.each { team ->
            loadTeamMembers(team)
        }

        teams
    }

    GithubTeam getTeam(String teamSlug) {
        githubApiClient.getApiResponse("/orgs/SyscoCorporation/teams/${teamSlug}",
                GithubTeam,
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh'))
    }

    List<GithubTeam> getChildTeams(String teamSlug) {
        githubApiClient.getApiResponse("/orgs/SyscoCorporation/teams/${teamSlug}/teams",
                GithubTeam[],
                AppConfiguration.getConfigurationAsBoolean('github.org.refresh'))
    }

    private List<GithubUser> loadTeamMembers(GithubTeam team) {
        def perPage = 100
        def resultSize = 100
        def page = 1

        while (resultSize == perPage) {
            def result = githubApiClient.getApiResponse(
                    "/orgs/SyscoCorporation/teams/${team.name}/members?per_page=100&page=${page}",
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
