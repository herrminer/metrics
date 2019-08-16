package com.tauila.metrics

import com.taulia.metrics.SearchParameters
import spock.lang.Specification

class SearchParametersSpec extends Specification {

  def "advanceChunkDates one chunk"() {
    given:
    SearchParameters parameters = new SearchParameters(
      fromDate: '2019-08-01',
      toDate: '2019-08-02',
      chunkSize: 3
    )

    when:
    boolean result = parameters.advanceChunkDates()

    then:
    result
    parameters.fromDate == '2019-08-01'
    parameters.toDate == '2019-08-02'

    when:
    result = parameters.advanceChunkDates()

    then:
    !result
  }

  def "advanceChunkDates many chunks"() {
    given:
    SearchParameters parameters = new SearchParameters(
      fromDate: '2019-08-01',
      toDate: '2019-08-08',
      chunkSize: 3
    )

    when:
    boolean result = parameters.advanceChunkDates()

    then:
    result
    parameters.fromDate == '2019-08-01'
    parameters.toDate == '2019-08-03'

    when:
    result = parameters.advanceChunkDates()

    then:
    result
    parameters.fromDate == '2019-08-04'
    parameters.toDate == '2019-08-06'

    when:
    result = parameters.advanceChunkDates()

    then:
    result
    parameters.fromDate == '2019-08-07'
    parameters.toDate == '2019-08-08'

    when:
    result = parameters.advanceChunkDates()

    then:
    !result // no more!
  }

}
