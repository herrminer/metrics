package com.herrminer.metrics.service

import com.herrminer.metrics.model.Role
import com.herrminer.metrics.model.Team
import com.herrminer.metrics.model.User
import com.herrminer.metrics.model.github.GithubTeam
import com.herrminer.metrics.model.github.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TeamService {

    private static Logger logger = LoggerFactory.getLogger(TeamService)

    private static List<String> teamNames = ['syscolabs-customer-experience-engineering']

    GithubApiClient githubApiClient
    UserService userService
    Map<String, User> users = [:]

    TeamService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
        this.userService = new UserService(githubApiClient)
    }

    List<Team> getTeams() {
        List<String> localTeamNames = []
        localTeamNames.addAll(teamNames)
        teamNames.each { parentTeamSlug ->
            localTeamNames.addAll(getChildTeams(parentTeamSlug).collect { it.slug })
        }

        List<Team> teams = []
        localTeamNames.each {
            Team team = new Team(name: it)
            loadTeamMembers(team)
            teams.add(team)
        }
        teams
    }

    List<GithubTeam> getChildTeams(String parentTeamSlug) {
        githubApiClient.getApiResponse("/orgs/SyscoCorporation/teams/${parentTeamSlug}/teams", GithubTeam[])
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
                if (users.get(it.login)) {
                    // move to new team (presumably a child team)
                    team.addUser(users.get(it.login))
                } else {
                    GithubUser githubUser = userService.getUser(it.login)
                    if (githubUser) {
                        def user = new User(
                                userName: githubUser.login,
                                name: githubUser.name,
                                role: Role.Engineer)
                        users.put(user.userName, user)
                        team.addUser(user)
                    }
                }
            }

            resultSize = result.size()
            page++
        }
    }
}
