package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

class UsernameColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'User Name'
  }

  @Override
  String getColumnValue(User user) {
    user.userName
  }

}
