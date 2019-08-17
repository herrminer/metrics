package com.tauila.metrics.service

import com.taulia.metrics.service.CsvExporter
import spock.lang.Specification

class CsvExporterSpec extends Specification {

  def "test that all columns have a header"() {
    expect:
    new CsvExporter().columns.each {
      assert it.columnHeader
    }
  }

}
