package com.taulia.metrics.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import com.taulia.metrics.model.github.PullRequestSearchResponse
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PullRequestSearchService {

  private static Logger logger = LoggerFactory.getLogger(PullRequestSearchService)

  private static final String CACHE_DIRECTORY = "${System.getenv('HOME')}/metric-cache"

  String encodedCredentials
  String lastResponse

  DefaultHttpClient httpClient = new DefaultHttpClient()
  ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  PullRequestSearchService(String credentials) {
    this.encodedCredentials = credentials.bytes.encodeBase64().toString()
    ensureCacheDirectoryExists()
  }

  boolean ensureCacheDirectoryExists() {
    def cacheDirectory = new File(CACHE_DIRECTORY)
    if (!cacheDirectory.exists()) {
      cacheDirectory.mkdir()
    }
    cacheDirectory.exists()
  }

  PullRequestSearchResponse searchPullRequests(SearchParameters searchParameters) {

    String responseText = getCachedResponse(searchParameters)

    if (!responseText) {
      responseText = searchGithubApi(searchParameters)
      cacheResponse(searchParameters, responseText)
      sleep(1000) // so we don't exceed github's rate limit :(
    }

    objectMapper.readValue(responseText, PullRequestSearchResponse)
  }

  private String searchGithubApi(SearchParameters searchParameters) {
    logger.info "searching github from ${searchParameters.fromDate} to ${searchParameters.toDate}, page ${searchParameters.page}"

    String url = "https://api.github.com/search/issues?${searchParameters.buildParameters()}"

    HttpGet get = new HttpGet(url)
    get.addHeader('Authorization', "Basic ${encodedCredentials}")

    def response = httpClient.execute(get)
    HttpEntity entity = response.getEntity()

    lastResponse = entity.getContent().text

    lastResponse
  }

  String getCachedResponse(SearchParameters parameters) {
    File cachedResponse = new File(getCacheFilename(parameters))
    cachedResponse.exists() ? cachedResponse.text : null
  }

  boolean cacheResponse(SearchParameters parameters, String responseText) {
    File cachedResponse = new File(getCacheFilename(parameters))
    if (!cachedResponse.exists())
      cachedResponse << responseText
    cachedResponse.exists()
  }

  String getCacheFilename(SearchParameters parameters) {
    "${CACHE_DIRECTORY}/${Hashing.md5().hashString(parameters.buildParameters(), Charsets.UTF_8).toString()}"
  }
}
