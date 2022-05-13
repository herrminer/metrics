package com.herrminer.metrics.model

import com.fasterxml.jackson.annotation.JsonProperty

class GithubRepository {
    String id

    String name

    String fullName

    @JsonProperty("private")
    boolean isPrivate // named this way because the field is named "private"

    String visibility

    @JsonProperty("default_branch")
    String defaultBranch

}
