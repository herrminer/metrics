package com.taulia.metrics.service

import com.taulia.metrics.model.Team
import com.taulia.metrics.model.User
import com.taulia.metrics.model.github.PullRequest

import java.text.SimpleDateFormat

class PullRequestRepository {

  Map<String, Collection<PullRequest>> pullRequestsByRepositoryAndTeam = [:]
  Map<String, Collection<PullRequest>> pullRequestsByUserAndRepository = [:]
  Map<String, List<PullRequest>> pullRequestsByUserAndMonth = [:]

  TreeSet<String> repositories = new TreeSet<>()
  TreeSet<Team> teams = new TreeSet<>()

  TreeSet<String> months = new TreeSet<>()
  private SimpleDateFormat monthFormat = new SimpleDateFormat('yyyy-MM')

  void addPullRequest(User user, PullRequest pullRequest) {
    def repositoryName = pullRequest.repositoryName
    def key = getKeyForRepositoryAndTeam(repositoryName, user.team)
    if (!pullRequestsByRepositoryAndTeam[key]) {
      pullRequestsByRepositoryAndTeam[key] = []
    }
    pullRequestsByRepositoryAndTeam[key].add(pullRequest)

    teams.add(user.team)
    repositories.add(repositoryName)
    months.add(monthFormat.format(pullRequest.dateCreated))

    String userMonthKey = getUserMonthKey(user, pullRequest.dateCreated)
    if (!pullRequestsByUserAndMonth.containsKey(userMonthKey)) {
      pullRequestsByUserAndMonth.put(userMonthKey, [])
    }
    pullRequestsByUserAndMonth.get(userMonthKey).add(pullRequest)

    String userRepositoryKey = getUserRepositoryKey(repositoryName, user)
    if (!pullRequestsByUserAndRepository.containsKey(userRepositoryKey)) {
      pullRequestsByUserAndRepository.put(userRepositoryKey, [])
    }
    pullRequestsByUserAndRepository.get(userRepositoryKey).add(pullRequest)
  }

  Collection<PullRequest> getPullRequests(String repositoryName, Team team) {
    pullRequestsByRepositoryAndTeam[getKeyForRepositoryAndTeam(repositoryName, team)]
  }

  Collection<PullRequest> getPullRequests(User user, Date date) {
    def pullRequests = pullRequestsByUserAndMonth.get(getUserMonthKey(user, date))
    pullRequests ?: []
  }

  Collection<PullRequest> getPullRequests(String repository, User user) {
    def pullRequests = pullRequestsByUserAndRepository.get(getUserRepositoryKey(repository, user))
    pullRequests ?: []
  }

  String getUserMonthKey(User user, Date date) {
    "${user.userName}-${monthFormat.format(date)}"
  }

  static String getUserRepositoryKey(String repository, User user) {
    "${repository}.${user.userName}"
  }

  static String getKeyForRepositoryAndTeam(String repositoryName, Team team) {
    "${repositoryName}-${team.name}"
  }

}
