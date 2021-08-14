package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.Organization

import com.herrminer.metrics.service.SearchParameters

class ReportingContext {
  SearchParameters searchParameters
  Organization organization
  String outputDirectory
}
