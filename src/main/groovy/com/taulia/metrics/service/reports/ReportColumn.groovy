package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User

interface ReportColumn {

  String getColumnHeader()

  String getColumnValue(User user)

}