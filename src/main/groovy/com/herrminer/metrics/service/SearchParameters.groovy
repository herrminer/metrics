package com.herrminer.metrics.service

import groovy.time.TimeCategory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SearchParameters {

  private static final Logger logger = LoggerFactory.getLogger(SearchParameters)

  public static final String DATE_FORMAT = 'yyyy-MM-dd'

  boolean initialized = false

  String org

  String fromDate
  String toDate

  String fileFromDate
  String fileToDate

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
    if (!fromDate) {
      throw new RuntimeException("no 'fromDate' property in metrics.properties")
    }

    toDate = properties.getProperty('toDate')
    if (!toDate) {
      throw new RuntimeException("no 'toDate' property in metrics.properties")
    }

    fileFromDate = fromDate
    fileToDate = toDate

    def chunkSize = properties.getProperty('chunkSize')
    if (chunkSize) {
      this.chunkSize = Integer.parseInt(chunkSize)
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
    "per_page=${pageSize}&page=${page}&q=org:${org}+is:pr+is:merged+closed:${fromDate}..${toDate}"
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
