package com.tauila.metrics.service

import com.taulia.metrics.service.PullRequestSearchService
import com.taulia.metrics.service.SearchParameters
import spock.lang.Specification

class PullRequestSearchServiceSpec extends Specification {

  def "put and get a cached response"() {
    given:
    PullRequestSearchService service = new PullRequestSearchService('joseph:miner')

    SearchParameters parameters = new SearchParameters(
      fromDate: '2000-01-01',
      toDate: '2000-01-1'
    )

    def response = "{hello:world}"

    when:
    def returnValue = service.cacheResponse(parameters, response)

    then:
    returnValue
    service.getCachedResponse(parameters) == response
  }

}
