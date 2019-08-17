package com.taulia.metrics.service

import com.taulia.metrics.model.User

class NameColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    return 'Name'
  }

  @Override
  String getColumnValue(User user) {
    "${user.firstName} ${user.lastName}"
  }

}
