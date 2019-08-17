package com.taulia.metrics.service

import com.taulia.metrics.model.User

interface CsvColumn {

  String getColumnHeader()

  String getColumnValue(User user)

}