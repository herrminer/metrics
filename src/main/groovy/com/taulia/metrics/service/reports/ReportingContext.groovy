package com.taulia.metrics.service.reports

import com.taulia.metrics.model.Organization
import com.taulia.metrics.service.PullRequestRepository
import com.taulia.metrics.service.SearchParameters

class ReportingContext {
  SearchParameters searchParameters
  Organization organization
  PullRequestRepository pullRequestRepository
  String exportDirectory
}
