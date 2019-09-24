package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

interface ReportColumn {

  String getColumnHeader()

  String getColumnValue(User user)

}