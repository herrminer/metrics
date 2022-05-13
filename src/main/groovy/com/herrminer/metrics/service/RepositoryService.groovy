package com.herrminer.metrics.service

import com.herrminer.metrics.model.GithubRepository

class RepositoryService {

    GithubApiClient githubApiClient

    RepositoryService(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient
    }

    List<GithubRepository> getAllRepositories(String organization) {
        //githubApiClient.getApiResponse("/orgs/${organization}/repos", GithubRepository[])
        githubApiClient.getAllPages("/orgs/${organization}/repos", GithubRepository[], true, 100)
    }
}
