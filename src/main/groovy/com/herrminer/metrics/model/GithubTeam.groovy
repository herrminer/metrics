package com.herrminer.metrics.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

class GithubTeam {

    String id
    String name
    String slug
    String description

    GithubTeam parent

    @JsonProperty('organization')
    GithubOrganization githubOrganization

    @JsonIgnore
    Organization organization

    @JsonIgnore
    List<GithubUser> githubUsers = []

    void addUser(GithubUser githubUser) {
        githubUser.githubTeam = this
        githubUsers.add(githubUser)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        GithubTeam that = (GithubTeam) o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return name.hashCode()
    }


    @Override
    public String toString() {
        return "GithubTeam{" +
                "name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }
}
