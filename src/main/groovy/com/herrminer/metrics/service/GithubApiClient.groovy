package com.herrminer.metrics.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop
import com.google.common.base.Charsets
import com.google.common.hash.HashFunction
import com.google.common.hash.Hashing
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GithubApiClient {

  private static final Logger logger = LoggerFactory.getLogger(GithubApiClient)

  private static final String CACHE_DIRECTORY = "${System.getenv('HOME')}/metric-cache"

  HashFunction hashFunction = Hashing.md5()
  String apiBaseUrl = 'https://api.github.com'
  String encodedCredentials

  DefaultHttpClient httpClient = new DefaultHttpClient()
  ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  GithubApiClient(Properties properties) {
    encodeCredentials(properties)
    ensureCacheDirectoryExists()
  }

  def encodeCredentials(Properties props) {
    def username = props.getProperty('github.username')

    if (!username) {
      logger.error "github.username is not defined in metrics.properties"
      System.exit(1)
    }

    def accessToken = props.getProperty('github.access.token')

    if (!accessToken) {
      logger.error "github.access.token is not defined in metrics.properties"
      System.exit(1)
    }

    this.encodedCredentials = "${username}:${accessToken}".bytes.encodeBase64().toString()
  }

  def <T> T getApiResponse(String pathAndQueryString, Class<T> responseClass) {
    def url = apiBaseUrl + pathAndQueryString

    String responseText = getCachedResponseText(url)

    if (responseText) {
      logger.debug "cached ${url}"
    }

    if (!responseText) {
      responseText = getHttpResponseText(url)
      cacheResponseText(url, responseText)
    }

    objectMapper.readValue(responseText, responseClass)
  }

  private String getHttpResponseText(String url) {
    HttpGet get = new HttpGet(url)

    get.addHeader('Authorization', "Basic ${encodedCredentials}")

    def response = httpClient.execute(get)
    HttpEntity entity = response.getEntity()

    logger.info "API request: ${url}"

    if (logger.isDebugEnabled()) {
      logger.debug "RESPONSE HEADERS"
      response.getHeaders().each { header ->
        logger.debug "${header.name}: ${header.value}"
      }
    }

    def responseText = entity.getContent().text

    logger.trace "RESPONSE BODY:\n${responseText}"

    responseText
  }

  boolean ensureCacheDirectoryExists() {
    def cacheDirectory = new File(CACHE_DIRECTORY)
    if (!cacheDirectory.exists()) {
      cacheDirectory.mkdir()
    }
    cacheDirectory.exists()
  }

  String getCachedResponseText(String url) {
    File cachedResponse = new File(getCacheFilename(url))
    cachedResponse.exists() ? cachedResponse.text : null
  }

  boolean cacheResponseText(String url, String responseText) {
    File cachedResponse = new File(getCacheFilename(url))
    if (!cachedResponse.exists())
      cachedResponse << responseText
    cachedResponse.exists()
  }

  String getCacheFilename(String url) {
    "${CACHE_DIRECTORY}/${hashFunction.hashString(url, Charsets.UTF_8).toString()}"
  }

}
