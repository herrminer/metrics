package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

class NameColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    return 'Name'
  }

  @Override
  String getColumnValue(User user) {
    "${user.name}"
  }

}
