package com.herrminer.metrics.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.herrminer.metrics.model.PullRequest

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
