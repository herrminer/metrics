package com.herrminer.metrics.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

class GithubUser {

  String login
  String name

  @JsonProperty('html_url')
  String url

  @JsonIgnore
  GithubTeam githubTeam

  @JsonIgnore
  Collection<PullRequest> pullRequests = []

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    GithubUser that = (GithubUser) o

    if (login != that.login) return false

    return true
  }

  int hashCode() {
    return login.hashCode()
  }


  @Override
  public String toString() {
    return "GithubUser{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
