package com.tauila.metrics.service

import com.taulia.metrics.service.GithubApiClient
import spock.lang.Specification

class GithubApiClientSpec extends Specification {

  def "put and get a cached response"() {
    given:
    GithubApiClient service = new GithubApiClient('joseph:miner')

    def response = "{hello:world}"

    when:
    def returnValue = service.cacheResponseText('http://foobar.com', response)

    then:
    returnValue
    service.getCachedResponseText('http://foobar.com') == response
  }

}
