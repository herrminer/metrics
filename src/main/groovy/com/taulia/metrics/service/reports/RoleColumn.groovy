package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

class RoleColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Role'
  }

  @Override
  String getColumnValue(User user) {
    user.role
  }

}
