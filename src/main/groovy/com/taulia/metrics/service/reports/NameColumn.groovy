package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

class NameColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    return 'Name'
  }

  @Override
  String getColumnValue(User user) {
    "${user.firstName} ${user.lastName}"
  }

}
