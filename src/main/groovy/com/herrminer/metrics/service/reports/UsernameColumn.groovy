package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

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
