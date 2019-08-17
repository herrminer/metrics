package com.taulia.metrics.model.github

import com.fasterxml.jackson.annotation.JsonProperty
import com.taulia.metrics.model.github.PullRequest

class PullRequestSearchResponse {
  /**
   * Error message
   */
  String message

  @JsonProperty('total_count')
  int totalCount

  @JsonProperty('incomplete_results')
  boolean incompleteResults

  List<PullRequest> items
}
