package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

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
