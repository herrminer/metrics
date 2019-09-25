package com.herrminer.metrics.service

import com.herrminer.metrics.service.GithubApiClient
import spock.lang.Specification

class GithubApiClientSpec extends Specification {

  def "put and get a cached response"() {
    given:
    Properties properties = new Properties()
    properties.setProperty('github.username', 'joseph.miner')
    properties.setProperty('github.access.token', '12345')

    GithubApiClient service = new GithubApiClient(properties)

    def response = "{hello:world}"

    when:
    def returnValue = service.cacheResponseText('http://foobar.com', response)

    then:
    returnValue
    service.getCachedResponseText('http://foobar.com') == response
  }

}
