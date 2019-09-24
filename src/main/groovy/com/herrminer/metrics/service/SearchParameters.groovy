package com.herrminer.metrics.service

import groovy.time.TimeCategory

class SearchParameters {

  public static final String DATE_FORMAT = 'yyyy-MM-dd'

  boolean initialized = false

  String org

  String fromDate
  String toDate

  private Date originalFromDate
  private Date originalToDate

  private Date currentFromDate
  private Date currentToDate

  int page = 0
  int pageSize = 100

  // used to work around the 1000-item search limit on Github
  int chunkSize = 30 // days

  SearchParameters() {
  }

  SearchParameters(Properties properties) {
    fromDate = properties.getProperty('fromDate')
    toDate = properties.getProperty('toDate')

    def chunkSize = properties.getProperty('chunkSize')
    if (chunkSize) {
      this.chunkSize = Integer.parseInt(chunkSize)
    }

    org = properties.getProperty("org")
    if (!org) {
      throw new RuntimeException("no 'org' property in metrics.properties")
    }
  }

  void initialize() {
    if (!initialized) {
      originalFromDate = Date.parse(DATE_FORMAT, fromDate)
      originalToDate = Date.parse(DATE_FORMAT, toDate)
      initialized = true
    }
  }

  boolean advanceChunkDates() {
    initialize()

    if (page && currentToDate == originalToDate) {
      return false // already at end
    }

    use (TimeCategory) {
      if (currentFromDate == null) {
        // first time through
        currentFromDate = originalFromDate
      } else {
        currentFromDate = currentToDate + 1.day
      }

      Date nextToDate = currentFromDate + (chunkSize - 1).days
      if (nextToDate.after(originalToDate)) {
        currentToDate = originalToDate
      } else {
        currentToDate = nextToDate
      }

    }

    fromDate = currentFromDate.format(DATE_FORMAT)
    toDate = currentToDate.format(DATE_FORMAT)
    page = 1

    return true
  }

  String buildParameters() {
    "per_page=${pageSize}&page=${page}&q=org:${org}+is:pr+is:merged+created:${fromDate}..${toDate}"
  }

  void incrementPage() {
    page++
  }

  @Override
  public String toString() {
    return "SearchParameters{" +
      "fromDate='" + fromDate + '\'' +
      ", toDate='" + toDate + '\'' +
      ", page=" + page +
      ", pageSize=" + pageSize +
      ", chunkSize=" + chunkSize +
      '}'
  }
}
