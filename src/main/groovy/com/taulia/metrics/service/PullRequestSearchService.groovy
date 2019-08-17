package com.taulia.metrics.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.taulia.metrics.model.github.PullRequestSearchResponse
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

class PullRequestSearchService {

  PullRequestSearchService(String credentials) {
    this.encodedCredentials = credentials.bytes.encodeBase64().toString()
  }

  String encodedCredentials
  String lastResponse

  DefaultHttpClient httpClient = new DefaultHttpClient()
  ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  PullRequestSearchResponse searchPullRequests(SearchParameters searchParameters) {
    String url = "https://api.github.com/search/issues?${searchParameters.buildParameters()}"
    HttpGet get = new HttpGet(url)

    get.addHeader('Authorization', "Basic ${encodedCredentials}")

    def response = httpClient.execute(get)
    HttpEntity entity = response.getEntity()
    lastResponse = entity.getContent().text
    objectMapper.readValue(lastResponse, PullRequestSearchResponse)
  }

}
