package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.Organization
import com.herrminer.metrics.service.PullRequestRepository
import com.herrminer.metrics.service.SearchParameters

class ReportingContext {
  SearchParameters searchParameters
  Organization organization
  PullRequestRepository pullRequestRepository
  String outputDirectory
}
