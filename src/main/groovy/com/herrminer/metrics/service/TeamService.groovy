package com.herrminer.metrics.service

import com.herrminer.metrics.model.Role
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import com.herrminer.metrics.model.github.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService)

    private static List<String> teamNames = ['syscolabs-customer-experience-engineering']

    GithubApiClient githubApiClient
    UserService userService

    TeamService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
        this.userService = new UserService(githubApiClient)
    }

    List<Team> getTeams() {
        List<Team> teams = []
        teamNames.each {
            Team team = new Team(name: it)
            loadTeamMembers(team)
            teams.add(team)
        }
        teams
    }

    private List<User> loadTeamMembers(Team team) {
        def perPage = 100
        def resultSize = 100
        def page = 1

        while (resultSize == perPage) {
            def result = githubApiClient.getApiResponse(
                    "/orgs/SyscoCorporation/teams/${team.name}/members?per_page=100&page=${page}",
                    GithubUser[])

            result.each {
                GithubUser githubUser = userService.getUser(it.login)
                if (githubUser) {
                    team.addUser(new User(
                            userName: githubUser.login,
                            name: githubUser.name,
                            role: Role.Engineer)
                    )
                }
            }

            resultSize = result.size()
            page++
        }
    }
}
