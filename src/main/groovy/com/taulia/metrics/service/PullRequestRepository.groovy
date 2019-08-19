package com.taulia.metrics.service

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest

class PullRequestRepository {

  Map<String, Collection<PullRequest>> pullRequestsByRepositoryAndTeam = [:]
  TreeSet<String> repositories = new TreeSet<>()
  TreeSet<Team> teams = new TreeSet<>()

  void addPullRequest(User user, PullRequest pullRequest) {
    def repositoryName = pullRequest.repositoryName
    def key = getKeyForRepositoryAndTeam(repositoryName, user.team)
    if (!pullRequestsByRepositoryAndTeam[key]) {
      pullRequestsByRepositoryAndTeam[key] = []
    }
    pullRequestsByRepositoryAndTeam[key].add(pullRequest)
    teams.add(user.team)
    repositories.add(repositoryName)
  }

  Collection<PullRequest> getPullRequests(String repositoryName, Team team) {
    pullRequestsByRepositoryAndTeam[getKeyForRepositoryAndTeam(repositoryName, team)]
  }

  String getKeyForRepositoryAndTeam(String repositoryName, Team team) {
    "${repositoryName}-${team.name}"
  }

}
