package com.herrminer.metrics.service

import com.herrminer.metrics.model.github.GithubUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService)

    GithubApiClient githubApiClient

    UserService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
    }

    def getUser(String login) {
        githubApiClient.getApiResponse("/users/${login}", GithubUser, true)
    }
}
